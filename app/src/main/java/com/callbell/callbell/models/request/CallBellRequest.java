package com.callbell.callbell.models.request;

import android.util.Log;

import com.callbell.callbell.models.State;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/4/15.
 */
public class CallBellRequest extends Request {


    private static final String TAG = CallBellRequest.class.getSimpleName();
    private State mState;
    private String to,
            message;

    public CallBellRequest(State st, String ti, String msg) {
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

    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(PrefManager.STATE_KEY, mState.toJson());
        jsonObject.put(TO_KEY, getTo());
        jsonObject.put(CATEGORY_KEY, PrefManager.CATEGORY_CALL_BELL);
        jsonObject.put(PAYLOAD_KEY, getMessage());

        return jsonObject;
    }

}
