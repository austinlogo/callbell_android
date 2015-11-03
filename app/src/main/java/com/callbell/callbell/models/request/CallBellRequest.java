package com.callbell.callbell.models.request;

import android.util.Log;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
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
            category;
    private String message;

    public CallBellRequest(State st, String ti, MessageReason reason, String cat) {
        mState = st;
        to = ti;
        category = cat;
        message = reason.name();
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
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject();

            jsonObject.put(State.STATE_ID, mState.toJson());
            jsonObject.put(TO_KEY, getTo());
            jsonObject.put(CATEGORY_KEY, category);
            jsonObject.put(PAYLOAD_KEY, message);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON: " + e);
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }

}
