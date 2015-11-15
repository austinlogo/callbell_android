package com.callbell.callbell.presentation.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.request.Request;
import com.callbell.callbell.models.response.ConnectionStatusUpdateResponse;
import com.callbell.callbell.models.response.MessageResponse;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import java.util.List;

import javax.inject.Inject;

public class StationActivity
        extends BaseActivity
        implements StationFragment.StationActivityListener, TitleBarFragment.TitleBarListener{
    StationFragment mStationFragment;
    public static final String TAG = StationActivity.class.getSimpleName();

    private BroadcastReceiver mBroadcastReceiver;

    @Inject
    PrefManager prefs;
    private TitleBarFragment mTitleBarFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        Log.d(TAG, "Started Station");

        mTitleBarFragment = TitleBarFragment.newInstance(TitleBarFragment.STATION_MODE_ACTIVITY);
        mStationFragment = StationFragment.newInstance();


        getFragmentManager()
                .beginTransaction()
//                .add(R.id.fragment_login_title_container, mTitleBarFragment)
                .add(R.id.fragment_login_container, mStationFragment)
                .commit();

        IntentFilter filter = new IntentFilter();
        filter.addAction(PrefManager.EVENT_STATES_RECEIVED);
        filter.addAction(PrefManager.EVENT_MESSAGE_RECEIVED);
        filter.addAction(PrefManager.EVENT_STATE_UPDATE);
        filter.addAction(PrefManager.EVENT_SERVER_CONNECTION_CHANGED);
        filter.addAction(PrefManager.EVENT_TABLET_CONNECTIONS_RECEIVED);
        filter.addAction(ConnectionStatusUpdateResponse.INTENT_ACTION);

       mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(PrefManager.EVENT_MESSAGE_RECEIVED)) {
                    MessageResponse response = new MessageResponse(intent.getExtras());

                    Log.d(TAG, "OnReceiveCalled");
                    playContinualNotificationSound();
                    mStationFragment.updateListItemStatus(response);
                    Log.d(TAG, "STATE: " + response.state.toString());
                } else if (intent.getAction().equals(PrefManager.EVENT_STATES_RECEIVED)) {

                    mStationFragment.setListFromJSONString(intent.getStringExtra(PrefManager.STATELIST_RESPONSE));
                } else if (PrefManager.EVENT_STATE_UPDATE.equals(intent.getAction())) {
                    Log.d(TAG, "TABLET STATE UPDATED");
                    State st = new State(JSONUtil.getJSONFromString(intent.getStringExtra(State.STATE_ID)));

                    mStationFragment.updateList(st);
                    Log.d(TAG, st.toString());
                } else if (PrefManager.EVENT_SERVER_CONNECTION_CHANGED.equals(intent.getAction())) {
                    boolean isConnected = intent.getBooleanExtra(PrefManager.SERVER_CONNECTED, false);
                    mTitleBarFragment.toggleServerconnectedView(isConnected);

                    if (isConnected) {
                        mStationFragment.getDeviceStates();
                    }
                } else if (PrefManager.EVENT_TABLET_CONNECTIONS_RECEIVED.equals(intent.getAction())) {
                    String payload = intent.getStringExtra(PrefManager.PAYLOAD);
                    List<String> connectedTablets = JSONUtil.getStringListFromJSONArrayString(payload);
                    mStationFragment.updateConnectionStatuses(connectedTablets);
                } else if (ConnectionStatusUpdateResponse.INTENT_ACTION.equals(intent.getAction())) {
                    String payload = intent.getStringExtra(ConnectionStatusUpdateResponse.INTENT_EXTRA_JSON_STRING);
                    ConnectionStatusUpdateResponse response = new ConnectionStatusUpdateResponse(JSONUtil.getJSONFromString(payload));
                    mStationFragment.updateConnectionStatuses(response);
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        SocketService.getInstance().unregisterDevice(prefs.getCurrentState());
    }

    @Override
    public void stopSound() {
        notificationSound.pause();
    }

    @Override
    public void clearValues() {
        // NOOP
    }

    @Override
    public void toggleSimpleMode() {
        // NOOP
    }
}


