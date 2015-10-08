package com.callbell.callbell.presentation.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.presentation.CallBellApplication;
import com.callbell.callbell.presentation.bed.BedModeActivity;
import com.callbell.callbell.presentation.station.StationActivity;
import com.callbell.callbell.service.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @InjectView(R.id.login_id)
    EditText login_id;

    @InjectView(R.id.launch_bed_experience)
    Button bedButton;

    @InjectView(R.id.launch_station_experience)
    Button stationButton;

    @Inject
    PrefManager mPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((CallBellApplication) getApplication()).inject(this);
        ButterKnife.inject(this);

        bedButton.setEnabled(false);
        stationButton.setEnabled(false);

        bedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Bed");
                register("bed");
                Intent newActivity = new Intent(getApplicationContext(), BedModeActivity.class);
                startActivity(newActivity);
            }
        });

        stationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register("station");
                Intent newActivity = new Intent(getApplicationContext(), StationActivity.class);
                startActivity(newActivity);
            }
        });

        login_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // NOOP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // NOOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (login_id.getText().length() > 0) {
                    bedButton.setEnabled(true);
                    stationButton.setEnabled(true);
                }
            }
        });


    }

    public void register(String func) {

        if( mPrefManager.getPreferences().getBoolean(mPrefManager.REG_UPLOADED, false) && mPrefManager.getPreferences().getString(mPrefManager.REG_ID, "").length() > 0) {
            Log.d(TAG, "Already Registered");
            Log.d(TAG, mPrefManager.getPreferences().getString(mPrefManager.REG_ID, ""));
        } else if (checkPlayServices()) {
//        if (checkPlayServices()) {
            String hospitalId = "Test_hospital";
            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("hospital_id", hospitalId);
            if (func.equals("station") ) {
                intent.putExtra("location", hospitalId + "_station");
            } else {
                intent.putExtra("location", login_id.getText().toString());
            }

            Log.d(TAG, "starting Service: " + func);
            startService(intent);

        }else{
            Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_SHORT).show();
        }
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
