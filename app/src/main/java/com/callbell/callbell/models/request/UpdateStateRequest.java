package com.callbell.callbell.models.request;

import android.util.Log;

import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/16/15.
 */
public class UpdateStateRequest extends Request {

    private static final String TAG = UpdateStateRequest.class.getSimpleName();
    private State mState;
    private String to_id;

    public UpdateStateRequest (State currentState, String to) {
        mState = currentState;
        to_id = to;
    }

    @Override
    public String getOperation() {
        return "/updateState";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        try {
            result.put(State.STATE_ID, mState.toJson());
            result.put(Request.CATEGORY_KEY, PrefManager.CATEGORY_TABLET_STATE_UPDATE);
            result.put(TO_KEY, to_id);
        } catch (JSONException e) {
            Log.d(TAG, "Encountered exception creating JSON object: " + e);
        }

        return result;
    }
}
