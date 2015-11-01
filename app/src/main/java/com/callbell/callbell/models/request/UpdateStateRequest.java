package com.callbell.callbell.models.request;

import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/16/15.
 */
public class UpdateStateRequest extends Request {

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
    public JSONObject toJSON() throws JSONException {
        JSONObject result = new JSONObject();

        result.put(State.STATE_ID, mState.toJson());
        result.put(Request.CATEGORY_KEY, PrefManager.CATEGORY_TABLET_STATE_UPDATE);
        result.put(TO_KEY, to_id);
        return result;
    }
}
