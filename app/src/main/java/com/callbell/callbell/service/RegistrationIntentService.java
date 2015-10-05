package com.callbell.callbell.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.tasks.PostRequestTask;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import javax.inject.Inject;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = RegistrationIntentService.class.getSimpleName();

    public RegistrationIntentService() {
        super(TAG);
    }

    @Inject
    PrefManager mPrefManager;

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.getString(mPrefManager.DEFAULT_SENDER_ID, "").isEmpty()) {
            prefs.edit().putString(mPrefManager.DEFAULT_SENDER_ID, "434312104937");
        }

        Log.d(TAG, "Started Registration Handler");

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("434312104937", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.d(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);
            prefs.edit().putString(mPrefManager.REG_ID, token).apply();
            prefs.edit().putBoolean(mPrefManager.REG_UPLOADED, true).apply();
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            prefs.edit().putBoolean(mPrefManager.REG_UPLOADED, false).apply();
        }

    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        ServerMessage message = new ServerMessage("register", "A1229", null, token);
        new PostRequestTask(getApplication()).execute(message);
    }
}
