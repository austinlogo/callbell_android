package com.callbell.callbell.models;

import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/13/15.
 */
public class GetStatesRequest extends Request {

    private State mState;

    public GetStatesRequest(State currentState) {
        mState = currentState;
    }

    @Override
    public String getOperation() {
        return "/getDeviceStates";
    }

    @Override
    public JSONObject toJSON() throws JSONException {

        JSONObject result = new JSONObject();
        result.put(PrefManager.STATE_KEY, mState.toJson());

        return result;
    }
}