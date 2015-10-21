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


    public static final int RESTROOM_ID = 0;
    public static final int BLANKET_ID = 1;
    public static final int PAIN_ID = 2;
    public static final int FOOD_ID = 3;
    public static final int HELP_ID = 4;


    private static final String TAG = CallBellRequest.class.getSimpleName();
    private State mState;
    private String to;
    private int message;

    public CallBellRequest(State st, String ti, int msg) {
        mState = st;
        to = ti;
        message = msg;
    }

    public String getHospital() {
        return mState.getHospital();
    }

    public int getMessage() {
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
