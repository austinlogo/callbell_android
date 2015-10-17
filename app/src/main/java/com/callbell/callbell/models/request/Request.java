package com.callbell.callbell.models.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/7/15.
 */
public class Request {

    public static final String CATEGORY_KEY = "category_id";
    public static final String TO_KEY = "to_id";
    public static final String PAYLOAD_KEY = "payload_id";

    public JSONObject toJSON () throws JSONException {
        return null;
    }

    public String getOperation() {
        return "";
    }
}
