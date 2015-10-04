package com.callbell.callbell.data;

import org.json.JSONObject;

/**
 * Created by austin on 10/4/15.
 */
public interface ServerDAO {

    public void sendMessage(String senderId, JSONObject message);
}
