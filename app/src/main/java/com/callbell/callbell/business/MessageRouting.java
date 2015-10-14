package com.callbell.callbell.business;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.models.GetStatesRequest;
import com.callbell.callbell.service.tasks.PostRequestWithCallbackTask;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.tasks.PostRequestTask;

import javax.inject.Inject;

//@Singleton
public class MessageRouting implements PostRequestWithCallbackTask.PostRequestTaskListener {

    private static final String TAG = MessageRouting.class.getSimpleName();
    @Inject
    PrefManager prefs;

    @Inject
    Application context;

    public MessageRouting() {
    }

    public void sendMessage(String to, String cat, String msg) {
        ServerMessage sm = new ServerMessage(prefs.getCurrentState(), to, msg);
        Log.d(TAG, "To: " + to);
        Log.d(TAG, "From: " + prefs.location());
        Log.d(TAG, "Mode: " + prefs.mode());
        new PostRequestTask(context).execute(sm);
    }

    public void getDeviceStates() {

        GetStatesRequest request = new GetStatesRequest(prefs.getCurrentState());

        PostRequestWithCallbackTask task = new PostRequestWithCallbackTask(context, PrefManager.EVENT_STATES_RECEIVED, this);
        task.execute(request);
    }

    @Override
    public void onTaskCompleted(String response) {
        Intent i = new Intent(PrefManager.EVENT_STATES_RECEIVED);
        i.putExtra(PrefManager.STATELIST_RESPONSE, response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }
}
