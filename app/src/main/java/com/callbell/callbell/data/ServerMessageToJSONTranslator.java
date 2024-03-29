package com.callbell.callbell.data;


import android.util.Log;

import com.callbell.callbell.models.request.RegistrationRequest;
import com.callbell.callbell.models.request.CallBellRequest;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Singleton;

@Singleton
public class ServerMessageToJSONTranslator {

    private static final String TAG = ServerMessageToJSONTranslator.class.getSimpleName();

    public JSONObject ServerMessageToJSON(CallBellRequest message) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("hospital_id", message.getHospital());
            jsonObject.put("to_id", message.getTo());
            jsonObject.put("from_id", message.getFrom());
            jsonObject.put("payload", message.getMessage());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }

    public JSONObject RegisterRequestToJSON(RegistrationRequest message) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("hospital_id", message.getHospitalId());
            jsonObject.put("bed_id", message.getLocationId());
            jsonObject.put("register_id", message.getRegisterId());

            return jsonObject;
        } catch (JSONException e) {
            Log.e(TAG, "An error has occurred creating a jsonObject");
        }

        return null;
    }
}
