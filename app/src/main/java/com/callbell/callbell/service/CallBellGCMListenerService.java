package com.callbell.callbell.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.models.request.Request;
import com.callbell.callbell.presentation.dialogs.CallBellDialog;
import com.callbell.callbell.util.PrefManager;
import com.google.android.gms.gcm.GcmListenerService;

public class CallBellGCMListenerService extends GcmListenerService {

    public static String TAG = CallBellGCMListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.d(TAG, data.toString());
        String category = data.getString(Request.CATEGORY_KEY);


        if (PrefManager.CATEGORY_CALL_BELL.equals(category)
                || PrefManager.CATEGORY_CALL_BELL_RESPONSE.equals(category)) {
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + data.toString());

            Intent i = new Intent(PrefManager.EVENT_MESSAGE_RECEIVED);
            i.putExtras(data);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
//        } else if(PrefManager.CATEGORY_CALL_BELL_RESPONSE.equals(category)) {

        } else if (PrefManager.CATEGORY_TABLET_STATE_UPDATE.equals(category)) {
            Log.d(TAG, "Category called");
            Intent i = new Intent(PrefManager.EVENT_STATE_UPDATE);
            i.putExtras(data);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        }
    }
}
