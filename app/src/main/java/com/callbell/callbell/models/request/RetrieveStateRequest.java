package com.callbell.callbell.models.request;

import android.util.Log;

import com.callbell.callbell.models.State.State;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 12/6/15.
 */
public class RetrieveStateRequest extends Request {

    private static final String TAG = RetrieveStateRequest.class.getSimpleName();
    private State mState;

    public RetrieveStateRequest(State st) {
        mState = st;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = super.toJSON();

        try {
            result.put(State.STATE_ID, mState.toJSON());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
