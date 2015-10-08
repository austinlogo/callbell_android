package com.callbell.callbell.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/7/15.
 */
public class RegisterRequest extends Request {

    private static final String TAG = RegisterRequest.class.getSimpleName();
    private String hospitalId, bedId, register_id;

    public RegisterRequest(String hi, String bi, String ri) {
        hospitalId = hi;
        bedId = bi;
        register_id = ri;
    }

    public String getHospitalId () {
        return hospitalId;
    }

    public String getBedId() {
        return bedId;
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

            jsonObject.put("hospital_id", getHospitalId());
            jsonObject.put("bed_id", getBedId());
            jsonObject.put("register_id", getRegisterId());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }
}
