package com.callbell.callbell.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.dagger.AndroidModule;
import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.CallBellGCMListenerService;
import com.callbell.callbell.service.RegistrationIntentService;

import com.callbell.callbell.service.tasks.PostRequestTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import javax.inject.Inject;

import java.io.IOException;

import dagger.ObjectGraph;

public class DashboardActivity extends AppCompatActivity {

    private String SENDER_ID = "434312104937";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String TAG = DashboardActivity.class.getSimpleName();

    @Inject
    PrefManager mPrefManager;

    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    Context context;
    String regid;
    EditText txt;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: verify that this works, please
        Intent i = new Intent(this, CallBellGCMListenerService.class);
        startService(i);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        ((CallBellApplication) getApplication()).inject(this);

        txt = (EditText) findViewById(R.id.edit_text_reg_id);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        prefs.edit().clear().apply();
        context = getApplicationContext();
        mButton = (Button) findViewById(R.id.nurse_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
                getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

                ServerMessage sm = new ServerMessage("receive", "A1228", "A1228", "Hello");
                new PostRequestTask(getApplication()).execute(sm);
            }
        });

        String reg = "";
        if( prefs.getBoolean(mPrefManager.REG_UPLOADED, false) && prefs.getString(mPrefManager.REG_ID, "").length() > 0) {
            txt.setText("Reg Id set: " + prefs.getString(mPrefManager.REG_ID, ""));
            Log.d("REG", reg);
        }else if(checkPlayServices()){
            txt.setText("REG ID being set");
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);

        }else{
            Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
