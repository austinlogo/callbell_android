package com.callbell.callbell.service;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class CallBellGCMListenerService extends GcmListenerService {

    public static String TAG = CallBellGCMListenerService.class.getSimpleName();

    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + data.toString());
    }
}
