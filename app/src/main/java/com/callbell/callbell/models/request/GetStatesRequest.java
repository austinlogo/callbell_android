package com.callbell.callbell.models.request;

import android.util.Log;

import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/13/15.
 */
public class GetStatesRequest extends Request {

    private static final String TAG = GetStatesRequest.class.getSimpleName();
    private State mState;

    public GetStatesRequest(State currentState) {
        mState = currentState;
    }

    @Override
    public String getOperation() {
        return "/getDeviceStates";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put(State.STATE_ID, mState.toJson());
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object: " + e);
        }

        return result;
    }
}
