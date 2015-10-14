package com.callbell.callbell.presentation.bed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.CallBellApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BedModeActivity extends BaseActivity
        implements CallBellsFragment.OnFragmentInteractionListener, PlanOfCareFragment.OnFragmentInteractionListener {

    private static final String TAG = BedModeActivity.class.getSimpleName();

    @Inject
    MessageRouting messageRouting;

    @Inject
    PrefManager prefs;

    StaffFragment mStaffFragment;
    CallBellsFragment mCallBellsFragment;
    PlanOfCareFragment mPlanOfCareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "BedModeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_mode);
        ButterKnife.inject(this);
        ((CallBellApplication) getApplication()).inject(this);

        mStaffFragment = StaffFragment.newInstance();
        mCallBellsFragment = CallBellsFragment.newInstance();
        mPlanOfCareFragment = PlanOfCareFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.bed_mode_staff_fragment_container, mStaffFragment, "Staff Information")
                .add(R.id.bed_mode_plan_of_care_fragment_container, mPlanOfCareFragment, "Plan of Care")
                .add(R.id.bed_mode_CallBellsFragment_container, mCallBellsFragment, "Call Bells")
                .commit();

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Admin Mode Altered: " + prefs.isSuperUser());
                mStaffFragment.enableSuperUserAccess(prefs.isSuperUser());
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));
    }

    @Override
    public void onCallBellPressed(String msg) {
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        //Send GCM Message
        messageRouting.sendMessage(prefs.getStationTabletName(), prefs.CATEGORY_CALL_BELL, msg);

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
