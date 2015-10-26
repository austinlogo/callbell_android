package com.callbell.callbell.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.dialogs.SetPainRatingDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                adminSettingsAction(item);
                break;
            case R.id.timer_setting:
                painSettingsDisplayAction();
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
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void adminSettingsAction(MenuItem adminButton) {

        // Moving into SuperUser Mode
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUserStatus(false);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
            adminButton.setTitle(R.string.admin_mode);
            prefs.setState(prefs.getCurrentState());
        }
    }

    public void painSettingsDisplayAction() {
        if (prefs.isSuperUser()) {
            SetPainRatingDialog dialog = new SetPainRatingDialog();
            dialog.show(getFragmentManager(), "PAIN SET");
        } else {
            PainRatingDialog prDialog = new PainRatingDialog();
            prDialog.show(getFragmentManager(), "PAIN RATING DIALOG");
        }
    }

    public void playSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
