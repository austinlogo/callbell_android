package com.callbell.callbell.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.presentation.dialogs.CallBellDialog;
import com.callbell.callbell.util.PrefManager;
import com.google.android.gms.gcm.GcmListenerService;

public class CallBellGCMListenerService extends GcmListenerService {

    public static String TAG = CallBellGCMListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + data.toString());

        Intent i = new Intent(PrefManager.EVENT_MESSAGE_RECEIVED);
        i.putExtras(data);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }
}
