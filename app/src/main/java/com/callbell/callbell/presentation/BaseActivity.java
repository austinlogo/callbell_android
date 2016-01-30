package com.callbell.callbell.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.dialogs.SetPainRatingDialog;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.util.LocaleUtil;
import com.callbell.callbell.util.PrefManager;

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

    public MediaPlayer notificationSound;

    public static int DEFAULT_FLAGS = Intent.FLAG_ACTIVITY_CLEAR_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TOP;


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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    }


    protected void register() {
        if (SocketService.mService != null) {
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

    public void adminSettingsAction(View view) {
        Button button = (Button)view;
        // Moving into SuperUser Mode
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUser(false);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
            button.setText(R.string.admin_mode);
            prefs.setState(prefs.getCurrentState());
        }
    }

    public void painSettingsDisplayAction(View view) {
        if (prefs.isSuperUser()) {
            SetPainRatingDialog dialog = new SetPainRatingDialog();
            dialog.show(getFragmentManager(), "PAIN SET");
        } else {
            PainRatingDialog prDialog = new PainRatingDialog();
            prDialog.show(getFragmentManager(), "PAIN RATING DIALOG");
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
}
