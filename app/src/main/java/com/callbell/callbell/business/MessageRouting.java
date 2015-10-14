package com.callbell.callbell.business;

import android.app.Application;
import android.util.Log;

import com.callbell.callbell.models.GetStatesRequest;
import com.callbell.callbell.service.tasks.PostRequestWithResponseTask;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.tasks.PostRequestTask;

import javax.inject.Inject;

//@Singleton
public class MessageRouting {

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

        PostRequestWithResponseTask task = new PostRequestWithResponseTask(context, PrefManager.EVENT_STATES_RECEIVED);
        task.execute(request);
    }
}
