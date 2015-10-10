package com.callbell.callbell.models;

import android.util.Log;

import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/4/15.
 */
public class ServerMessage extends Request {

    private static final String TAG = ServerMessage.class.getSimpleName();
    private State mState;
    private String to,
            message;

    public ServerMessage(State st, String ti, String msg) {
        mState = st;
        to = ti;
        message = msg;

    }

    public String getHospital() {
        return mState.getHospital();
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return mState.getLocation();
    }

    public String getOperation() {
        return "/receive";
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(PrefManager.STATE_KEY, mState.toJson());
            jsonObject.put("to_id", getTo());
            jsonObject.put("payload", getMessage());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }

}
