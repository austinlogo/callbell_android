package com.callbell.callbell.presentation.station;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.presentation.CallBellApplication;
import com.callbell.callbell.presentation.dialogs.CallBellDialog;

public class StationActivity extends AppCompatActivity implements StationFragment.OnFragmentInteractionListener {
    StationFragment mStationFragment;
    public static final String TAG = StationActivity.class.getSimpleName();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        Log.d(TAG, "Started Station");

        mStationFragment = StationFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.base_fragment_container, mStationFragment)
                .commit();

        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));


        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "OnReceiveCalled");
                mStationFragment.setText(intent.getStringExtra("message"));
                CallBellDialog alert = CallBellDialog.newInstance(intent.getExtras());

                getSupportFragmentManager().beginTransaction()
                        .add(alert, "Alert")
                        .commit();

            }
        }, new IntentFilter("Message Received"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
