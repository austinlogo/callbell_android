package com.callbell.callbell.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/4/15.
 */
public class ServerMessage extends Request {

    private static final String TAG = ServerMessage.class.getSimpleName();
    private String from,
            to,
            message,
            hospital,
            operation;

    public ServerMessage(String hos, String op, String fr, String t, String msg) {
        hospital = hos;
        operation = op;
        from = fr;
        to = t;
        message = msg;
    }

    public String getHospital() {
        return hospital;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getOperation() {
        return "/" + operation;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("hospital_id", getHospital());
            jsonObject.put("to_id", getTo());
            jsonObject.put("from_id", getFrom());
            jsonObject.put("payload", getMessage());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }

}
