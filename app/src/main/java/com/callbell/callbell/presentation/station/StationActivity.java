package com.callbell.callbell.presentation.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.dialogs.CallBellDialog;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class StationActivity extends BaseActivity implements StationFragment.OnFragmentInteractionListener {
    StationFragment mStationFragment;
    public static final String TAG = StationActivity.class.getSimpleName();

    private BroadcastReceiver mBroadcastReceiver;

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

        getFragmentManager()
                .beginTransaction()
                .add(R.id.base_fragment_container, mStationFragment)
                .commit();

        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));


        IntentFilter filter = new IntentFilter();
        filter.addAction(PrefManager.EVENT_STATES_RECEIVED);
        filter.addAction(PrefManager.EVENT_MESSAGE_RECEIVED);
        filter.addAction(PrefManager.EVENT_STATE_UPDATE);

       mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(PrefManager.EVENT_MESSAGE_RECEIVED)) {
                    Log.d(TAG, "OnReceiveCalled");
//                mStationFragment.setText(intent.getStringExtra("message"));
                    CallBellDialog alert = CallBellDialog.newInstance(intent.getExtras());

                    alert.show(getFragmentManager(), "Dialog");
                } else if (intent.getAction().equals(PrefManager.EVENT_STATES_RECEIVED)) {
                    mStationFragment.setListFromJSONString(intent.getStringExtra(PrefManager.STATELIST_RESPONSE));
                } else if (PrefManager.EVENT_STATE_UPDATE.equals(intent.getAction())) {
                    Log.d(TAG, "TABLET STATE UPDATED");

                    State st = new State(JSONUtil.getJSONFromString(intent.getStringExtra("message")));
                    mStationFragment.updateList(st);
                    Log.d(TAG, st.toString());
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}


