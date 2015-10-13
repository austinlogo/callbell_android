package com.callbell.callbell.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.callbell.callbell.models.State;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.models.RegisterRequest;
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
    PrefManager prefs;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Started Registration Handler");

        ((CallBellApplication) getApplication()).inject(this);

        if (prefs.getPreferences().getString(prefs.DEFAULT_SENDER_KEY, "").isEmpty()) {
            prefs.getPreferences().edit().putString(prefs.DEFAULT_SENDER_KEY, prefs.senderId());
        }

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(prefs.senderId(), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.d(TAG, "GCM Registration Token: " + token);


            sendRegistrationToServer(token);
            prefs.getPreferences().edit().putString(prefs.REG_ID, token).apply();

        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            prefs.getPreferences().edit().putBoolean(prefs.REG_UPLOADED_KEY, false).apply();
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
        RegisterRequest msg = new RegisterRequest(prefs.getCurrentState(), token);
        new PostRequestTask(getApplication()).execute(msg);
    }
}
