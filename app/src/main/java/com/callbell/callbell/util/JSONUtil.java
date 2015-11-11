package com.callbell.callbell.util;

import android.util.Log;

import com.callbell.callbell.models.State.State;

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

    public static List<String> getStringListFromJSONArrayString(String arrayString) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(arrayString);

            for (int index = 0; index < array.length(); index++) {
                list.add( (String) array.get(index));
            }

        } catch(JSONException e) {

        }

        return list;
    }

    public static List<State> getStateListFromJSONArray(JSONObject obj) {
        ArrayList<State> result = new ArrayList<>();

        try {
            JSONArray myArray = obj.getJSONArray(PrefManager.STATELIST_KEY);

            for (int index = 0; index < myArray.length(); index++) {
                result.add(new State( (JSONObject) myArray.get(index)));
            }

            return result;

        } catch (JSONException e) {

        }

        return new ArrayList<>();
    }

    public static String getValueStringIfExists(JSONObject obj, String key) {
        try {
            return obj.has(key) ? obj.getString(key) : "";
        } catch (JSONException e) {

        }
        return "";
    }

    public static int getValueIntIfExists(JSONObject obj, String key) {
        try {
            return obj.has(key) ? obj.getInt(key) : 0;
        } catch (JSONException e) {

        }
        return 0;
    }

    public static boolean getValueBooleanFromIntIfExists(JSONObject obj, String key) {
        try {
            int value = obj.has(key) ? obj.getInt(key) : 0;
            return value == 1;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean getValueBooleanValueIfExists(JSONObject obj, String key) {
        try {
            return obj.has(key) && obj.getBoolean(key);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException getting Boolean Value: " + e);
            return false;
        }
    }
}
