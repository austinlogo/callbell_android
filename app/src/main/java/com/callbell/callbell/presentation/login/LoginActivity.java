package com.callbell.callbell.presentation.login;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.PrefManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


import javax.inject.Inject;

public class LoginActivity
        extends BaseActivity
        implements LoginFragment.LoginFragmentCallback, TitleBarFragment.TitleBarListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private BroadcastReceiver mBroadcastReceiver;
    private Socket socket;

    private SocketService mService;
    private boolean mBound = false;

    TitleBarFragment mTitleBarFragment;
    LoginFragment mLoginFragment;

    @Inject
    PrefManager prefs;

    @Inject
    MessageRouting mMessage;

//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket(ServerEndpoints.PROD_SERVER_ENDPOINT);
//        } catch (Exception e) {
//            Log.e(TAG, "Error: " + e);
//
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CallBellApplication) getApplication()).inject(this);

        mLoginFragment = LoginFragment.newInstance();
        mTitleBarFragment = TitleBarFragment.newInstance();

        startService(new Intent(getApplicationContext(), SocketService.class));
//        Log.d(TAG, "Socket: " + mSocket.connect());
//        Log.d(TAG, "Connected: " + mSocket.connected());
//        mSocket.emit("add", prefs.location());
//        mSocket.emit("ping", "3");
//
//        mSocket.on("message", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.d(TAG, args[0].toString());
//            }
//        });o

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_login_title_container, mTitleBarFragment)
                .add(R.id.fragment_login_container, mLoginFragment)
                .commit();


        IntentFilter filter = new IntentFilter();
        filter.addAction(PrefManager.EVENT_SU_MODE_CHANGE);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (PrefManager.EVENT_SU_MODE_CHANGE.equals(intent.getAction())) {
                    mTitleBarFragment.setSuperUserSettings(prefs.isSuperUser());
                }
            }
        };



        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void clearValues() {
        // NOOP
    }

    @Override
    public void register() {
        if (SocketService.mService != null) {
            mMessage.register(prefs.getCurrentState(), prefs.getTabletName());

        }
    }
}
