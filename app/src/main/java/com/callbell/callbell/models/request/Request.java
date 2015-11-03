package com.callbell.callbell.models.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/7/15.
 */
public class Request {

    public static final String CATEGORY_KEY = "CATEGORY_ID";
    public static final String TO_KEY = "TO_ID";
    public static final String PAYLOAD_KEY = "PAYLOAD_ID";

    public JSONObject toJSON ()  {
        return new JSONObject();
    }

    public String getOperation() {
        return "";
    }
}
