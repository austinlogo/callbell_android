package com.callbell.callbell.presentation;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.dialogs.SetPainRatingDialog;
import com.callbell.callbell.presentation.toast.BeaToast;
import com.callbell.callbell.service.AdminReceiver;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.LocaleUtil;
import com.callbell.callbell.util.PrefManager;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by austin on 10/11/15.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Inject
    PrefManager prefs;

    @Inject
    MessageRouting mMessageRouting;

    protected Menu mOptionsMenu;
    protected LocaleUtil.AvailableLocales currentLocale;
    protected boolean active;

    private PowerManager mPowerManager;
    private DevicePolicyManager mDpm;
    public static boolean isTabletLocked = false;

    @Override
    protected void onResume() {
        super.onResume();

        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public MediaPlayer notificationSound;

    public static int DEFAULT_FLAGS = Intent.FLAG_ACTIVITY_CLEAR_TOP;


    // Determine if we want to unregister the device onDestroy()
    protected boolean persistConnectionOnDestroy = false;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(BaseActivity.DEFAULT_FLAGS);
        super.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationSound = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        notificationSound.setLooping(true);

        currentLocale = LocaleUtil.getLocale(this);
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.base_layout);
        ((CallBellApplication) getApplication()).inject(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "BaseActivity Broadcast Receiver");
                determineSuperUserText(mOptionsMenu);
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));

        setDeviceOwner();
    }

    private void setDeviceOwner() {
        ComponentName deviceAdmin = new ComponentName(this, AdminReceiver.class);
        mDpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!mDpm.isAdminActive(deviceAdmin)) {
            BeaToast.makeText(this, getString(R.string.not_device_admin) + " ME", BeaToast.LENGTH_SHORT).show();
        }

        if (!mDpm.isDeviceOwnerApp(getPackageName())) {
            try {
                Runtime.getRuntime().exec("dpm set-device-owner com.callbell.callbell/.service.AdminReceiver");
            } catch (IOException e) {
                Log.e(TAG, "Setting Device Owner failed");
                e.printStackTrace();
            }
        }

        mDpm.setLockTaskPackages(deviceAdmin, new String[]{getPackageName()});
        enableKioskMode(true);
    }

    protected void register() {
        if (SocketService.mService != null && !SocketService.isRegistered) {

            mMessageRouting.register(prefs.getCurrentState(), prefs.getCurrentState().getTabletName());
            mMessageRouting.retrieveState(prefs.getCurrentState());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bed_mode, menu);

        determineSuperUserText(menu);
        return true;
    }

    protected void determineSuperUserText(Menu menu) {
        if (menu == null) {
            Log.e(TAG, "menu null");
            return;
        }

        MenuItem adminItem = menu.findItem(R.id.admin_setting);
        if (prefs.isSuperUser()) {
            adminItem.setTitle(R.string.user_mode);
        } else adminItem.setTitle(R.string.admin_mode);
    }

    @Override
    public void onBackPressed() {
        if (!prefs.isSuperUser()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.admin_setting:
//                adminSettingsAction();
                break;
            case R.id.timer_setting:
//                painSettingsDisplayAction();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }

    public void setSuperUserPermissions(boolean isSuperUser) {
        if (!isSuperUser) {
            enableKioskMode(true);
        } else {
            enableKioskMode(false);
        }
    }

    public void playContinualNotificationSound() {
        try {
            notificationSound.setLooping(true);
            notificationSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playSoundOnce() {
        try {
            notificationSound.setLooping(false);
            notificationSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isActivityLocaleUpdated() {
        return currentLocale.equals(LocaleUtil.getLocale(this));
    }

    public void refresh() {
        new POCValues(this);
        new MedicationValues(this);
    }

    private void enableKioskMode(boolean enabled) {
        try {
            if (enabled) {
                if (!isTabletLocked && mDpm.isLockTaskPermitted(this.getPackageName())) {
                    isTabletLocked = true;
                    startLockTask();
                } else if (!mDpm.isLockTaskPermitted(this.getPackageName())) {
                    Toast.makeText(this, "Unable to lock tablet", Toast.LENGTH_SHORT).show();
                }
            } else {
                isTabletLocked = false;
                stopLockTask();
//                mIsKioskEnabled = false;
//                mButton.setText(getString(R.string.enter_kiosk_mode));
            }
        } catch (Exception e) {
            // TODO: Log and handle appropriately
        }
    }
}
