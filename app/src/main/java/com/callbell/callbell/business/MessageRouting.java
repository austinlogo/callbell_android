package com.callbell.callbell.business;

import android.app.Application;
import android.content.Context;

import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.service.tasks.PostRequestTask;

import javax.inject.Inject;
import javax.inject.Singleton;

//@Singleton
public class MessageRouting {

    @Inject
    Application context;

    public MessageRouting() {
    }

    public void sendMessage(String hos, String to, String cat, String msg) {
        ServerMessage sm = new ServerMessage(hos, "receive", "A1", to, msg);
        new PostRequestTask(context).execute(sm);
    }
}
