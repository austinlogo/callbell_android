package com.callbell.callbell.presentation.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.util.LocaleUtil;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.PrefManager;
import com.github.nkzawa.socketio.client.Socket;


import javax.inject.Inject;

public class LoginActivity
        extends BaseActivity
        implements LoginFragment.LoginFragmentCallback, TitleBarFragment.TitleBarListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter filter;

    TitleBarFragment mTitleBarFragment;
    LoginFragment mLoginFragment;

    @Inject
    PrefManager prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CallBellApplication) getApplication()).inject(this);

        mLoginFragment = LoginFragment.newInstance();
        mTitleBarFragment = TitleBarFragment.newInstance(TitleBarFragment.LOGIN_MODE_ACTIVITY);

        startService(new Intent(getApplicationContext(), SocketService.class));

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_login_title_container, mTitleBarFragment)
                .add(R.id.fragment_login_container, mLoginFragment)
                .commit();


        filter = new IntentFilter();
        filter.addAction(PrefManager.EVENT_SU_MODE_CHANGE);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (PrefManager.EVENT_SU_MODE_CHANGE.equals(intent.getAction())) {
                    mTitleBarFragment.setSuperUserPermissions(prefs.isSuperUser());
                }
            }
        };

    }

    @Override
    public void clearValues() {
        mLoginFragment.clearValues();
    }

    @Override
    public void toggleSimpleMode() {
        // NOOP
    }

    @Override
    public void refresh() {
        super.refresh();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);

        if (!isActivityLocaleUpdated()) {
            currentLocale = LocaleUtil.getLocale(this);
            refresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void launchActivity(Intent i) {
        startActivity(i);
    }
}
