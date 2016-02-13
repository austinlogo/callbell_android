package com.callbell.callbell.presentation.bed;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.EducationMetricLogger;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.response.MessageResponse;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.dialogs.PlanOfCareInfoDialog;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.presentation.toast.BeaToast;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BedModeActivity
        extends BaseActivity
        implements
                CallBellsFragment.OnFragmentInteractionListener,
                PlanOfCareFragment.PlanOfCareInteraction,
                TitleBarFragment.TitleBarListener,
                StaffFragment.StaffFragmentListener {

    private static final String TAG = BedModeActivity.class.getSimpleName();

    @Inject
    MessageRouting messageRouting;

    @Inject
    PrefManager prefs;

    @InjectView(R.id.bed_mode_CallBellsFragment_container)
    FrameLayout mCallBellContainer;

    private BroadcastReceiver mBroadcastReceiver;
    private StaffFragment mStaffFragment;
    private CallBellsFragment mCallBellsFragment;
    private PlanOfCareFragment mPlanOfCareFragment;
    private TitleBarFragment mTitleBarFragment;
    private boolean mSimpleMode = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.timer_setting);
        item.setVisible(true);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "BedModeActivity");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_mode);

        ButterKnife.inject(this);
        ((CallBellApplication) getApplication()).inject(this);

        mStaffFragment = StaffFragment.newInstance(PrefManager.getCurrentState());
        mCallBellsFragment = CallBellsFragment.newInstance();
        mPlanOfCareFragment = PlanOfCareFragment.newInstance(PrefManager.getCurrentState());
        mTitleBarFragment = TitleBarFragment.newInstance(TitleBarFragment.BED_MODE_ACTIVITY);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.bed_mode_title_bar_fragment_container, mTitleBarFragment, "Title Bar")
                .add(R.id.bed_mode_staff_fragment_container, mStaffFragment, "Staff Information")
                .add(R.id.bed_mode_plan_of_care_fragment_container, mPlanOfCareFragment, "Plan of Care")
                .add(R.id.bed_mode_CallBellsFragment_container, mCallBellsFragment, "Call Bells")
                .commit();

        setSuperUserPermissions(prefs.isSuperUser());

        IntentFilter filter = new IntentFilter();
        filter.addAction(PrefManager.EVENT_SU_MODE_CHANGE);
        filter.addAction(PrefManager.EVENT_MESSAGE_RECEIVED);
        filter.addAction(PrefManager.EVENT_SERVER_CONNECTION_CHANGED);
        filter.addAction(PrefManager.EVENT_STATE_UPDATE);
        filter.addAction(PrefManager.EVENT_RATE_PAIN);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(PrefManager.EVENT_MESSAGE_RECEIVED)) {
                    MessageResponse response = new MessageResponse(intent.getExtras());
                    BeaToast.makeText(getApplicationContext(), response.messageReason, Toast.LENGTH_SHORT).show();

                } else if (intent.getAction().equals(PrefManager.EVENT_SU_MODE_CHANGE)) {
                    Log.d(TAG, "Admin Mode Altered: " + prefs.isSuperUser());

                    if (mPlanOfCareFragment.isAdded()) {
                        mPlanOfCareFragment.setSuperUserPermissions(prefs.isSuperUser());
                    }

                    mStaffFragment.setSuperUserPermissions(prefs.isSuperUser());
                    mTitleBarFragment.setSuperUserPermissions(prefs.isSuperUser());
                    setSuperUserPermissions(prefs.isSuperUser());

                    if (!prefs.isSuperUser()) {
                        messageRouting.updateState(prefs.getCurrentState(), prefs.getStationName());
                    }
                    mStaffFragment.setSuperUserPermissions(prefs.isSuperUser());

                } else if (intent.getAction().equals(PrefManager.EVENT_SERVER_CONNECTION_CHANGED)) {
                    mTitleBarFragment.toggleServerconnectedView(intent.getBooleanExtra(PrefManager.SERVER_CONNECTED, false));
                } else if (PrefManager.EVENT_STATE_UPDATE.equals(intent.getAction())) {
                    Log.d(TAG, "TABLET STATE UPDATED");
                    State st = new State(JSONUtil.getJSONFromString(intent.getStringExtra(State.STATE_ID)));

                    prefs.setState(st);
                    mStaffFragment.updateState(st);

                    //we're not always showing the plan of care fragment (e.g. Simple mode)
                    if (mPlanOfCareFragment.isAdded()) {
                        mPlanOfCareFragment.updateState(st);
                    }
                    playSoundOnce();
                    BeaToast.makeText(context, R.string.new_info, BeaToast.LENGTH_LONG).show();
                } else if (PrefManager.EVENT_RATE_PAIN.equals(intent.getAction())) {
                    playSoundOnce();
                    PainRatingDialog dialog = new PainRatingDialog();
                    dialog.show(getFragmentManager(), "PAIN RATING DIALOG");
                }
            }
        };

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadcastReceiver, filter);
    }

    ////////// ACTIVITY ////////////////////////////////////////////////////////////////////////////

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        lockScreen();

        if (persistConnectionOnDestroy) {
            persistConnectionOnDestroy = false;
        }

        register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "pause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unlockScreen();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        if (!persistConnectionOnDestroy) {
            messageRouting.uploadEducationMetrics(prefs.getCurrentState(), EducationMetricLogger.getInstance().getEducationMetricList());
            SocketService.getInstance().unregisterDevice(prefs.getCurrentState());
        }
    }

    @Override
    public void onCallBellPressed(MessageReason reason) {
        BeaToast.makeText(this, R.string.message_sent, Toast.LENGTH_LONG).show();
        messageRouting.sendMessage(prefs.getStationName(), prefs.CATEGORY_CALL_BELL, reason);
    }

    @Override
    public void saveStaffState(State st) {
        prefs.setStaff(st.getPhysician(), st.getResident(), st.getNurse());
    }

    ////////// POCFragment//////////////////////////////////////////////////////////////////////////

    @Override
    public void showInfoDialog(String tit, String expandedName, String bod) {
        Intent i;

        if (expandedName == null) {
            i = new Intent(this, PlanOfCareInfoDialog.class);
            i.putExtras(PlanOfCareInfoDialog.newInstance(tit, bod));
        } else {
            i = new Intent(this, PlanOfCareInfoDialog.class);
            i.putExtras(PlanOfCareInfoDialog.newInstance(tit, expandedName, bod));
        }


        startActivity(i);
    }

    @Override
    public void savePOCState(State st) {
        prefs.setPOC(st);
        prefs.setAcceptablePain(st.getAcceptablePain());

    }

    ////////// TITLE BAR FRAGMENT //////////////////////////////////////////////////////////////////

    @Override
    public void clearValues() {
        if (mStaffFragment == null || mPlanOfCareFragment == null) {
            Log.e(TAG, "One or more fragments are null");
        }

        mStaffFragment.clearValues();
        mPlanOfCareFragment.clearValues();
    }

    @Override
    public void toggleSimpleMode() {
        mSimpleMode = !mSimpleMode;

        mCallBellContainer.setVisibility(mSimpleMode ? View.GONE : View.VISIBLE);
        FragmentManager fm = getFragmentManager();

        if (mSimpleMode) {
            fm.beginTransaction()
                    .remove(mCallBellsFragment)
                    .commit();

            fm.executePendingTransactions();

            fm.beginTransaction()
                    .replace(R.id.bed_mode_plan_of_care_fragment_container, mCallBellsFragment)
                    .commit();
            fm.executePendingTransactions();
        } else {
            fm.beginTransaction()
                    .replace(R.id.bed_mode_plan_of_care_fragment_container, mPlanOfCareFragment)
                    .commit();

            fm.executePendingTransactions();
            setSuperUserPermissions(true);

            fm.beginTransaction()
                    .add(R.id.bed_mode_CallBellsFragment_container, mCallBellsFragment)
                    .commit();

            fm.executePendingTransactions();
        }

        mCallBellsFragment.toggleMode(mSimpleMode);
    }

    @Override
    public void refresh() {
        super.refresh();
        persistConnectionOnDestroy = true;

        //Save State
        savePOCState(mPlanOfCareFragment.getState());
        saveStaffState(mStaffFragment.getState());

        //Restart Activity
        Intent i = new Intent(this, BedModeActivity.class);
        finish();
        startActivity(i);
    }

    public void setSuperUserPermissions(boolean isSuperUser) {
        mCallBellContainer.setVisibility(isSuperUser || mSimpleMode ? View.GONE : View.VISIBLE);
    }
}
