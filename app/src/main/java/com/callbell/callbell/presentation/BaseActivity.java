package com.callbell.callbell.presentation;

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
import android.view.WindowManager;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

/**
 * Created by austin on 10/11/15.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    @Inject
    PrefManager prefs;

    protected Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.base_layout);

        ((CallBellApplication) getApplication()).inject(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "BaseActivity Broadcast Receiver");
                determineSuperUserText(optionsMenu);
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;

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
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void adminSettingsAction(MenuItem adminButton) {
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getSupportFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUserStatus(false);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
            adminButton.setTitle(R.string.admin_mode);
            prefs.setState(prefs.getCurrentState());
        }
    }
}
