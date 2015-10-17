package com.callbell.callbell.util;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManager {

    // KEYS
    public static final String REG_ID = "REGISTRATION_ID";
    public static final String KEY_PASSWORD = "PASSWORD_ID";
    public static final String REG_UPLOADED_KEY = "REGISTRATION_UPLOADED_TO_GCM_SERVER";
    public static final String DEFAULT_SENDER_KEY = "DEFAULT_SENDER";
    public static final String SU_STATUS = "SUPER_USER_STATUS";

    //STATE
    public static final String HOSPITAL_KEY = "hospital_id";
    public static final String LOCATION_KEY = "location_id";
    public static final String GROUP_KEY = "group_id";
    public static final String MODE_KEY = "mode_id";
    public static final String REGISTRATION_KEY = "registration_id";
    public static final String PHYSICIAN_KEY = "physician_id";
    public static final String NURSE_KEY = "nurse_id";
    public static final String RESIDENT_KEY = "resident_id";
    public static final String STATE_KEY = "state_id";
    public static final String CHIEF_COMPLAINT_KEY = "chief_complaint_key";
    public static final String SHOWN_ACTION_KEY = "shown_actions_id";
    public static final String STATELIST_KEY = "stateList";
    public static final String ALL_ACTION_ITEMS_KEY = "all_action_items";

    //GLOBAL VALUES
    public static final String BED_MODE = "bed_mode";
    public static final String STATION_MODE = "station_mode";
    public static final String STATION_SUFFIX = "_STATION";
    public static final String GROUP_SUFFIX = "_GROUP";

    //CATEGORIES
    public static final String CATEGORY_CALL_BELL = "call_bell";
    public static final String CATEGORY_TABLET_STATE_UPDATE = "tablet_state";

    //EVENTS
    public static final String EVENT_MESSAGE_RECEIVED = "MESSAGE RECEIVED";
    public static final String EVENT_STATES_RECEIVED = "STATES RECEIVED";
    public static final String EVENT_STATE_UPDATE = "TABLET STATE UPDATED";
    public static final String EVENT_SU_MODE_CHANGE = "SU MODE CHANGE";

    //MISC. VALUES
    public static final String DEFAULT_SU_PASSWORD = "2468";
    private static final String TAG = PrefManager.class.getSimpleName();
    public static final String STATELIST_RESPONSE = "STATELIST RESPONSE";
    public static final String CALL_BELL_MESSAGE_BODY = "message_body";
    public static final String CALL_BELL_MESSAGE_FROM = "message_from";



    public static SharedPreferences prefs;
    public static State currentState;
    public static boolean isSuperUser;

    private static List<Integer> shownActions;
    private static List<String> allActionItems;

    @Inject
    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        currentState = new State(this);
        isSuperUser = false;
        shownActions = pullShownActionsFromPrefs();

    }

    public String getToken() {
        return prefs.getString(REG_ID, "");
    }

    public SharedPreferences getPreferences() {
        return prefs;
    }

    public String hospital() {
        return prefs.getString(HOSPITAL_KEY, "");
    }

    public String location() {
        return prefs.getString(LOCATION_KEY, "");
    }

    public String mode() {
        return prefs.getString(MODE_KEY, "");
    }

    public String group() {
        return prefs.getString(GROUP_KEY, "");
    }

    public String physician() {
        return prefs.getString(PHYSICIAN_KEY, "");
    }

    public String resident() {
        return prefs.getString(RESIDENT_KEY, "");
    }

    public String nurse() {
        return prefs.getString(NURSE_KEY, "");
    }

    public String chiefComplaint() {
        return prefs.getString(CHIEF_COMPLAINT_KEY, POCValues.DEFAULT_CHOICE);
    }

    public List<String> allActionItems() {
        if (allActionItems == null) {
            Set<String> set = prefs.getStringSet(ALL_ACTION_ITEMS_KEY, new HashSet<String>());
            return new ArrayList<>(set);
        }

        return allActionItems;
    }

    public List<Integer> shownActions() {

        return shownActions == null ? new ArrayList<Integer>() : shownActions;

    }

    private List<Integer> pullShownActionsFromPrefs() {
        try {
            String arrayString = prefs.getString(SHOWN_ACTION_KEY, "");
            Log.d(TAG, "shownActions-checkedValuePostions: " + arrayString);

            if (arrayString.isEmpty()) {
                return new ArrayList<Integer>();
            }

            JSONArray jArray = new JSONArray(arrayString);

            List<Integer> intArray = new ArrayList<>(jArray.length());
            for (int index = 0; index < jArray.length(); index++) {
                intArray.add(jArray.optInt(index));
            }

            return intArray;
        } catch (JSONException e) {
            Log.e(TAG, "unable to parse JSON in ShownActions: " + e);
        }

        return new ArrayList<>();
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setState(State newState) {
        SharedPreferences.Editor sp = prefs.edit();

        Log.d(TAG, "RES: " + resident());

        sp.putString(HOSPITAL_KEY, newState.getHospital());
        sp.putString(LOCATION_KEY, newState.getLocation());
        sp.putString(GROUP_KEY, newState.getGroup());
        sp.putString(MODE_KEY, newState.getMode());
        sp.putString(PHYSICIAN_KEY, newState.getPhysician());
        sp.putString(NURSE_KEY, newState.getNurse());
        sp.putString(RESIDENT_KEY, newState.getResident());
        sp.putString(CHIEF_COMPLAINT_KEY, newState.getChiefComplaint());
        sp.apply();

        Log.d(TAG, "RES2: " + resident());

        currentState = new State(newState);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setStaff(String doc, String res, String nurse) {
        currentState.setStaff(doc, res, nurse);

        SharedPreferences.Editor sp = prefs.edit();
        sp.putString(PHYSICIAN_KEY, doc);
        sp.putString(RESIDENT_KEY, res);
        sp.putString(NURSE_KEY, nurse);
        sp.commit();
    }

    public void setShownActions(List<Integer> sa) {
        Log.d(TAG, "SET SHOWN ACTIONS");
        SharedPreferences.Editor sp = prefs.edit();
        shownActions = sa;

        if (shownActions == null) {
            sp.putString(SHOWN_ACTION_KEY, "").apply();
            return;
        }

        JSONArray array = new JSONArray();
        for (int i : shownActions) {
            array.put(i);
        }

        Log.d(TAG, "setShownActions-Array: " + array.toString());
        sp.putString(SHOWN_ACTION_KEY, array.toString()).apply();
    }

    public void setAllActionItems(List<String> actionList) {
        allActionItems = actionList;

        SharedPreferences.Editor sp = prefs.edit();
        sp.putStringSet(ALL_ACTION_ITEMS_KEY, new HashSet<>(actionList)).commit();
    }

    public void uploadedToken(boolean bool) {
        prefs.edit().putBoolean(REG_UPLOADED_KEY, true).apply();
    }

    public void setSuperUserStatus(boolean bool) {
        isSuperUser = bool;
    }

    public String getStationTabletName() {
        return hospital() + STATION_SUFFIX;
    }

    public String senderId() {
        return "434312104937";
    }

}
