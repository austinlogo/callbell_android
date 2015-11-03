package com.callbell.callbell.business;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.request.GetStatesRequest;
import com.callbell.callbell.models.request.UpdateStateRequest;
import com.callbell.callbell.service.services.SocketService;
import com.callbell.callbell.service.tasks.PostRequestWithCallbackTask;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.models.request.CallBellRequest;
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

    public void sendMessage(String to, String cat, MessageReason reason) {
        CallBellRequest sm = new CallBellRequest(prefs.getCurrentState(), to, reason, cat);
        SocketService.getInstance().sendMessage(sm);
//        new PostRequestTask(context).execute(sm);
    }

    public void updateState() {
        Log.d(TAG, "Updating State");
        UpdateStateRequest usr = new UpdateStateRequest(prefs.getCurrentState(), prefs.getStationTabletName());
        new PostRequestTask(context).execute(usr);
    }

    public void getDeviceStates() {
        Log.d(TAG, "getDeviceStates");
        Log.d(TAG, "CurrentState: " + prefs.getCurrentState().toString());
        GetStatesRequest request = new GetStatesRequest(prefs.getCurrentState());

        PostRequestWithCallbackTask task = new PostRequestWithCallbackTask(context, PrefManager.EVENT_STATES_RECEIVED, this);
        task.execute(request);
    }

    @Override
    public void onTaskCompleted(String intentAction, String response) {

        // Setting up the structure for when there are more intentActions
        if (PrefManager.EVENT_STATES_RECEIVED.equals(intentAction)) {
            Intent i = new Intent(PrefManager.EVENT_STATES_RECEIVED);
            i.putExtra(PrefManager.STATELIST_RESPONSE, response);
            LocalBroadcastManager.getInstance(context).sendBroadcast(i);
        }
    }
}
