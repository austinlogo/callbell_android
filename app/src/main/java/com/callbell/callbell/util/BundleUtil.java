package com.callbell.callbell.util;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by austin on 11/2/15.
 */
public class BundleUtil {


    private static final String TAG = BundleUtil.class.getSimpleName();

    public static Bundle JSONObjectToBundle(JSONObject object) {

        Bundle bundle = new Bundle();
        Iterator<?> keys = object.keys();

        try {
            while (keys.hasNext()) {
                String key = (String) keys.next();
                bundle.putString(key, object.getString(key));

            }
        } catch (JSONException e) {
            Log.d(TAG, "Exception when getting JSON values: " + e);
            return null;
        }
        return bundle;
    }
}
