package com.callbell.callbell.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.dagger.AndroidModule;
import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.service.RegistrationIntentService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        ((CallBellApplication) getApplication()).inject(this);

        ObjectGraph objectGraph = ObjectGraph.create(new AndroidModule());

        txt = (EditText) findViewById(R.id.edit_text_reg_id);
        prefs = getSharedPreferences("Chat", 0);
        context = getApplicationContext();

        String reg = "";
        if(!(reg = prefs.getString("REG_FROM","")).isEmpty()){
            txt.setText("Reg Id set: " + reg);
            Log.d("REG", reg);
        }else  if(!(reg = prefs.getString("REG_FROM","")).isEmpty()){
            txt.setText("Reg Id set: " + reg );
            Log.d("REG", reg);
        }else if(checkPlayServices()){
//            Intent intent = new Intent(this, RegistrationIntentService.class);
//            startService(intent);
            txt.setText(mPrefManager.getToken());
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

    private class Register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                    regid = gcm.register(SENDER_ID);
                    Log.d("RegId", regid);

                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("REG_ID", regid);
                    Log.d("REG", regid);
                    edit.commit();

                }

                return  regid;

            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
                return "Fails";

            }
        }

        @Override
        protected void onPostExecute(String json) {
            txt.setText("Success. you're new reg_id is: " + regid);
        }

    }
}
