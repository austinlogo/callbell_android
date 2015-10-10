package com.callbell.callbell.models;

import android.util.Log;

import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by austin on 10/7/15.
 */
public class RegisterRequest extends Request {


    private static final String TAG = RegisterRequest.class.getSimpleName();
    private State mState;

    private String register_id;

    public RegisterRequest(State st, String reg) {
        mState = st;
        register_id = reg;
    }

    public String getHospitalId () {
        return mState.getHospital();
    }

    public String getGroupId() {
        return mState.getGroup();
    }

    public String getLocationId() {
        return mState.getLocation();
    }

    public String getRegisterId() {
        return register_id;
    }

    @Override
    public String getOperation() {
        return "/register";
    }

    @Override
    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(PrefManager.STATE_KEY, mState.toJson());
            jsonObject.put(PrefManager.REGISTRATION_KEY, getRegisterId());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }
}
