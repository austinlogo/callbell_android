package com.callbell.callbell.util;

import android.util.Log;

import com.callbell.callbell.models.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 10/13/15.
 */
public class JSONUtil {


    private static final String TAG = JSONUtil.class.getSimpleName();

    public static JSONObject getJSONFromString(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception has occurred: " + e);
        }

        return null;
    }

    public static List<State> getStateListFromJSONArray(JSONObject obj) {
        ArrayList<State> result = new ArrayList<>();

        try {
            JSONArray myArray = obj.getJSONArray(PrefManager.STATELIST_KEY);

            for (int index = 0; index < myArray.length(); index++) {
                result.add(new State( (JSONObject) myArray.get(index)));
                Log.d(TAG, result.get(result.size() - 1).getPhysician());
            }

            return result;

        } catch (JSONException e) {

        }

        return new ArrayList<>();
    }
}
