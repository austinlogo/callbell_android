package com.callbell.callbell.business;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.callbell.callbell.config.PrefManager;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.tasks.PostRequestTask;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        ServerMessage sm = new ServerMessage(prefs.hospital(), "receive", "A1", to, msg);
        Log.d(TAG, "To: " + to);
        Log.d(TAG, "From: " + prefs.location());
        Log.d(TAG, "Mode: " + prefs.mode());
        new PostRequestTask(context).execute(sm);
    }
}
