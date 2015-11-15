package com.callbell.callbell.presentation.bed;

import android.app.FragmentManager;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.response.MessageResponse;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.dialogs.PlanOfCareInfoDialog;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.service.tasks.PainRatingAsyncTask;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BedModeActivity
        extends BaseActivity
        implements
                CallBellsFragment.OnFragmentInteractionListener,
                PlanOfCareFragment.PlanOfCareInteraction,
                TitleBarFragment.TitleBarListener {

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
    private PainRatingAsyncTask mPainRatingAsyncTask;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_mode);

        ButterKnife.inject(this);
        ((CallBellApplication) getApplication()).inject(this);

        mStaffFragment = StaffFragment.newInstance();
        mCallBellsFragment = CallBellsFragment.newInstance();
        mPlanOfCareFragment = PlanOfCareFragment.newInstance();
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

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(PrefManager.EVENT_MESSAGE_RECEIVED)) {
                    MessageResponse response = new MessageResponse(intent.getExtras());
                    playSoundOnce();
                    Toast.makeText(getApplicationContext(), response.messageReason.name(), Toast.LENGTH_SHORT).show();

                } else if (intent.getAction().equals(PrefManager.EVENT_SU_MODE_CHANGE)) {
                    Log.d(TAG, "Admin Mode Altered: " + prefs.isSuperUser());

                    mStaffFragment.setSuperUserPermissions(prefs.isSuperUser());
                    mPlanOfCareFragment.setSuperUserPermissions(prefs.isSuperUser());
                    mTitleBarFragment.setSuperUserPermissions(prefs.isSuperUser());
                    setSuperUserPermissions(prefs.isSuperUser());

                    if (!prefs.isSuperUser()) {
                        messageRouting.updateState();
                    }
                    mStaffFragment.setSuperUserPermissions(prefs.isSuperUser());

                } else if (intent.getAction().equals(PrefManager.EVENT_SERVER_CONNECTION_CHANGED)) {
                    mTitleBarFragment.toggleServerconnectedView(intent.getBooleanExtra(PrefManager.SERVER_CONNECTED, false));
                }
            }
        };

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketService.getInstance().unregisterDevice(prefs.getCurrentState());
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);

        if (mPainRatingAsyncTask != null) {
            mPainRatingAsyncTask.interrupt();
        }
    }

    @Override
    public void onCallBellPressed(MessageReason reason) {
        Toast.makeText(this, R.string.message_sent, Toast.LENGTH_SHORT).show();
        messageRouting.sendMessage(prefs.getStationName(), prefs.CATEGORY_CALL_BELL, reason);
    }

    @Override
    public void showInfoDialog(String tit, String expandedName, String bod) {
        if (expandedName == null) {
            PlanOfCareInfoDialog.newInstance(tit, bod).show(getFragmentManager(), "INFO DIALOG");
        } else {
            PlanOfCareInfoDialog.newInstance(tit, expandedName, bod).show(getFragmentManager(), "INFO DIALOG");
        }
    }

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

            mCallBellsFragment.toggleOrientation(LinearLayout.HORIZONTAL);
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

            mCallBellsFragment.toggleOrientation(LinearLayout.VERTICAL);
        }
    }

    public void setSuperUserPermissions(boolean isSuperUser) {
        mCallBellContainer.setVisibility(isSuperUser || mSimpleMode ? View.GONE : View.VISIBLE);
    }
}
