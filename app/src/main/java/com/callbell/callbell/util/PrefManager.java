package com.callbell.callbell.util;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State;

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

    //GLOBAL VALUES
    public static final String BED_MODE = "bed_mode";
    public static final String STATION_MODE = "station_mode";
    public static final String STATION_SUFFIX = "_STATION";
    public static final String GROUP_SUFFIX = "_GROUP";
    public static final String CATEGORY_CALL_BELL = "call_bell";

    //EVENTS
    public static final String EVENT_MESSAGE_RECEIVED = "MESSAGE RECEIVED";
    public static final String EVENT_SU_MODE_CHANGE = "SU MODE CHANGE";

    //MISC. VALUES
    public static final String DEFAULT_SU_PASSWORD = "2468";
    private static final String TAG = PrefManager.class.getSimpleName();



    public static SharedPreferences prefs;
    public static State currentState;
    public static boolean isSuperUser;

    @Inject
    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        isSuperUser = false;

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
        currentState.setStaff(doc, nurse, res);

        SharedPreferences.Editor sp = prefs.edit();
        sp.putString(PHYSICIAN_KEY, doc);
        sp.putString(RESIDENT_KEY, res);
        sp.putString(NURSE_KEY, nurse);
        sp.commit();
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
