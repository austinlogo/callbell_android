package com.callbell.callbell.presentation.bed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.presentation.StaffFragment;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.presentation.CallBellApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BedModeActivity extends AppCompatActivity implements CallBellsFragment.OnFragmentInteractionListener {

    private static final String TAG = BedModeActivity.class.getSimpleName();

    @Inject
    MessageRouting messageRouting;

    @Inject
    PrefManager prefs;

    StaffFragment mStaffFragment;
    CallBellsFragment mCallBellsFragment;

    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "BedModeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_mode);
        ButterKnife.inject(this);
        ((CallBellApplication) getApplication()).inject(this);

        mStaffFragment = StaffFragment.newInstance();
        mCallBellsFragment = CallBellsFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.bed_mode_staff_fragment_container, mStaffFragment, "Staff Information")
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.bed_mode_CallBellsFragment_container, mCallBellsFragment, "Call Bells")
                .commit();


        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean enable = intent.getBooleanExtra(PrefManager.KEY_PASSWORD, false);
                Log.d(TAG, "Admin Mode Altered: " + enable );
                mStaffFragment.enableSuperUserAccess(enable);
                determineSuperUserText(optionsMenu);

            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_ENABLED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bed_mode, menu);

        determineSuperUserText(menu);
        return true;
    }

    private void determineSuperUserText(Menu menu) {
        if (menu == null) {
            return;
        }

        MenuItem adminItem = menu.findItem(R.id.admin_setting);
        if (prefs.isSuperUser()) {
            adminItem.setTitle(R.string.user_mode);
        } else adminItem.setTitle(R.string.admin_mode);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.admin_setting:
                adminSettignsAction(item);
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void adminSettignsAction(MenuItem adminButton) {
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getSupportFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUser(false);
            mStaffFragment.enableSuperUserAccess(false);
            adminButton.setTitle(R.string.admin_mode);
        }
    }

    @Override
    public void onCallBellPressed(String msg) {
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        //Send GCM Message
        messageRouting.sendMessage(prefs.getStationTabletName(), prefs.CATEGORY_CALL_BELL, msg);

    }
}
