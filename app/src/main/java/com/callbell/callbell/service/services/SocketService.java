package com.callbell.callbell.service.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.EmbossMaskFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.request.Request;
import com.callbell.callbell.models.request.RetrieveStateRequest;
import com.callbell.callbell.models.response.ConnectionStatusUpdateResponse;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.util.BundleUtil;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.util.ThreadUtil;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import javax.inject.Inject;

/**
 * Created by austin on 11/1/15.
 */
public class SocketService extends Service {

    @Inject
    PrefManager prefs;

    private static final long DEFAULT_PING_INTERVAL_IN_MS = 30000;
    private static final long LOST_CONNECTION_PING_INTERVAL_IN_MS = 5000;
    public static SocketService mService;

    private static boolean isServerDisconnected = false;


    private static final String TAG = SocketService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    public Socket mSocket;

    Thread mThread, mPingThread;
    SocketListeners mThreadRunnable;
    PingServer mPingThreadRunnable;

    public enum SocketOperation {
        REGISTER,
        UNREGISTER,
        RECEIVE,
        DEVICE_MESSAGE,
        SERVER_DISCONNECT,
        CONNECTION_UPDATE,
        GET_DEVICE_STATES,
        RETRIEVE_STATE,
        UPDATE_STATE_AND_SEND_REQUEST
    }

    public static SocketService getInstance() {
        return mService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mService = this;

        try {
            mSocket = IO.socket(ServerEndpoints.SERVER_ENDPOINT_IN_USE);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Exception with Socket: " + e);
        }

        mThreadRunnable = new SocketListeners();
        mThread = new Thread(mThreadRunnable);
        mThread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class SocketListeners implements Runnable {

        @Override
        public void run() {

            mSocket.connect();

            mSocket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "message: " + args[0].toString());
                }
            });

            mSocket.on(SocketOperation.DEVICE_MESSAGE.name(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "RECEIVED MESSAGE");
                    JSONObject payload = JSONUtil.getJSONFromString(args[0].toString());
                    handleIncomingMessages(payload);
                }
            });

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "CONNECTED TO SOCKET");
                    sendServerConnectionChangedBroadcast(true);
                }
            });

            mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "CLIENT SOCKET DISCONNECT");
                    sendServerConnectionChangedBroadcast(false);
                }
            });

            mSocket.on(SocketOperation.CONNECTION_UPDATE.name(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    handleUpdate((String) args[0]);
                }
            });

            mSocket.on(SocketOperation.SERVER_DISCONNECT.name(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "SERVER SOCKET DISCONNECTED");
                    mSocket.disconnect();
                    sendServerConnectionChangedBroadcast(false);
                }
            });

            mSocket.on(SocketOperation.GET_DEVICE_STATES.name(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "GET_DEVICE_STATES RECEIVED");
                    handleDeviceStatesUpdate(args[0].toString());
                }
            });

            mSocket.on(SocketOperation.RETRIEVE_STATE.name(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, args[0].toString());
                }
            });


            mSocket.on("pong", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String payload = (String) args[0];

                    if (payload != null && payload.length() > 0) {
                        Intent i = new Intent(PrefManager.EVENT_TABLET_CONNECTIONS_RECEIVED);
                        i.putExtra(PrefManager.PAYLOAD, payload);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
                    }
                }
            });

        }
    }

    public class PingServer implements Runnable {

        private volatile boolean running = true;
        String name;

        public PingServer(String tabletName) {
            name = tabletName;
        }

        public void terminate() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {

                long sleepLength = 0;

                if (mSocket.connected() && isServerDisconnected) {
                    isServerDisconnected = false;
                    sendServerConnectionChangedBroadcast(true);
                    sleepLength = DEFAULT_PING_INTERVAL_IN_MS;
                } else if (mSocket.connected()) {
                    sleepLength = DEFAULT_PING_INTERVAL_IN_MS;
                } else {

                    Log.e(TAG, "SERVER UNAVAILABLE");
                    Log.d(TAG, "Connection Status " + mSocket.connected());

                    isServerDisconnected = true;
                    mSocket.connect();

                    sleepLength = LOST_CONNECTION_PING_INTERVAL_IN_MS;
                }

                ThreadUtil.sleep(sleepLength, Thread.currentThread());

                // if we terminate while sleeping we shouldn't call.
                if (running) {
                    mSocket.emit("ping", name);
                }

            }
        }
    }

    public void registerDevice(State state) {
        mPingThreadRunnable = new PingServer(state.getTabletName());
        mPingThread = new Thread(mPingThreadRunnable);
        startSocketEmitter(SocketOperation.REGISTER, state.toJSON().toString());
        mPingThread.start();
    }

    public void unregisterDevice(State state) {
        startSocketEmitter(SocketOperation.UNREGISTER, state.toJSON().toString());
        mPingThreadRunnable.terminate();
        mThread.interrupt();

    }

    public void sendMessage(Request request) {
        startSocketEmitter(SocketOperation.RECEIVE, request.toJSON().toString());
    }

    public void updateStateAndSendMessage(Request request) {
        startSocketEmitter(SocketOperation.UPDATE_STATE_AND_SEND_REQUEST, request.toJSON().toString());
    }

    public void getDeviceState(Request request) {
        startSocketEmitter(SocketOperation.GET_DEVICE_STATES, request.toJSON().toString());
    }

    public void retrieveState(RetrieveStateRequest request) {
        startSocketEmitter(SocketOperation.RETRIEVE_STATE, request.toJSON().toString());
    }

    public void startSocketEmitter(SocketOperation operation, String payload) {
        Log.d(TAG, "PAYLOAD: " + payload);
        new Thread(new SocketEmitter(operation, payload)).start();
    }

    private void handleUpdate(String string) {

        if (string == null) {
            Log.e(TAG, "handle Update parameter was null");
            return;
        }

        Intent i = new Intent(ConnectionStatusUpdateResponse.INTENT_ACTION);
        i.putExtra(ConnectionStatusUpdateResponse.INTENT_EXTRA_JSON_STRING, string);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private void handleDeviceStatesUpdate(String response) {
        Intent i = new Intent(PrefManager.EVENT_STATES_RECEIVED);
        i.putExtra(PrefManager.STATELIST_RESPONSE, response);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    public void handleIncomingMessages(JSONObject object) {

        String data = JSONUtil.getValueStringIfExists(object, "data");
        Bundle bundle = BundleUtil.JSONObjectToBundle(JSONUtil.getJSONFromString(data));

        Log.d(TAG, bundle.toString());
        String category = bundle.getString(Request.CATEGORY_KEY);
        Log.d(TAG, "CATEGORY: " + category);


        if (PrefManager.CATEGORY_CALL_BELL.equals(category)
                || PrefManager.CATEGORY_CALL_BELL_RESPONSE.equals(category)) {
//            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + bundle.toString());

            Intent i = new Intent(PrefManager.EVENT_MESSAGE_RECEIVED);
            i.putExtras(bundle);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

        } else if (PrefManager.CATEGORY_TABLET_STATE_UPDATE.equals(category)) {
            Log.d(TAG, "Category called");
            Intent i = new Intent(PrefManager.EVENT_STATE_UPDATE);
            i.putExtras(bundle);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        } else if (PrefManager.CATEGORY_RATE_PAIN.equals(category)) {
            Intent i = new Intent(PrefManager.EVENT_RATE_PAIN);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        }
    }

    public class SocketEmitter implements Runnable {

        private SocketOperation mOperation;
        private String payload;

        public SocketEmitter(SocketOperation op, String pl) {
            mOperation = op;
            payload = pl;
        }

        @Override
        public void run() {
            Log.d(TAG, "Starting SocketEmitter Runnable with Operation: " + mOperation.name());
            mSocket.emit(mOperation.name(), payload);
        }
    }

    public class LocalBinder extends Binder {
        public SocketService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SocketService.this;
        }
    }

    private void sendServerConnectionChangedBroadcast(boolean isServerConnected) {
        Intent i = new Intent(PrefManager.EVENT_SERVER_CONNECTION_CHANGED);
        i.putExtra(PrefManager.SERVER_CONNECTED, isServerConnected);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }
}
