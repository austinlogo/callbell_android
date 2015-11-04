package com.callbell.callbell.service.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.models.request.Request;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.util.BundleUtil;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by austin on 11/1/15.
 */
public class SocketService extends Service {

    public static SocketService mService;

    private static final String TAG = SocketService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    public Socket mSocket;
    Thread mThread;

    public enum SocketOperation {
        REGISTER,
        UNREGISTER,
        RECEIVE,
        DEVICE_MESSAGE,
        GET_DEVICE_STATES
    }

    public static SocketService getInstance() {
        return mService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mService = this;

        try {
            mSocket = IO.socket(ServerEndpoints.EMULATOR_LOCALHOST_SERVER_ENDPOINT);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Exception with Socket: " + e);
        }

        mThread = new Thread(new SocketListeners());
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
        }
    }

    public void registerDevice(Request request) {
        startSocketEmitter(SocketOperation.REGISTER, request.toJSON().toString());
//        Thread emitter = new Thread(new SocketEmitter(SocketOperation.REGISTER, name));
//        emitter.start();
    }

    public void unregisterDevice(String name) {
        startSocketEmitter(SocketOperation.UNREGISTER, name);
//        Thread emitter = new Thread(new SocketEmitter(SocketOperation.UNREGISTER, name));
//        emitter.start();
    }

    public void sendMessage(Request request) {
        startSocketEmitter(SocketOperation.RECEIVE, request.toJSON().toString());
    }

    public void getDeviceState(Request request) {
        startSocketEmitter(SocketOperation.GET_DEVICE_STATES, request.toJSON().toString());
    }

    public void startSocketEmitter(SocketOperation operation, String payload) {
        Log.d(TAG, "PAYLOAD: " + payload);
        new Thread(new SocketEmitter(operation, payload)).start();
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
        }
    }

    public class LocalBinder extends Binder {
        public SocketService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SocketService.this;
        }
    }
}
