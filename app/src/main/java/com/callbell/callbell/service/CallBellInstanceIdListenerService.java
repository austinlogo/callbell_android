package com.callbell.callbell.service;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by austin on 10/3/15.
 */
public class CallBellInstanceIdListenerService extends InstanceIDListenerService {

    private static final String TAG = CallBellGCMListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).\
        Log.d(TAG, "Token Refresh");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
