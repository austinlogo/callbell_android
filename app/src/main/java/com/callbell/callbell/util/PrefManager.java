package com.callbell.callbell.util;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.callbell.callbell.models.State;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManager {

    // KEYS
    public static final String REG_ID = "REGISTRATION_ID";
    public static final String REG_UPLOADED_KEY = "REGISTRATION_UPLOADED_TO_GCM_SERVER";
    public static final String DEFAULT_SENDER_KEY = "DEFAULT_SENDER";

    //STATE
    public static final String HOSPITAL_KEY = "hospital_id";
    public static final String LOCATION_KEY = "location_id";
    public static final String GROUP_KEY = "group_id";
    public static final String MODE_KEY = "mode_id";
    public static final String REGISTRATION_KEY = "registration_id";
    public static final String STATE_KEY = "state_id";

    //GLOBAL VALUES
    public static final String BED_MODE = "bed_mode";
    public static final String STATION_MODE = "station_mode";
    public static final String STATION_SUFFIX = "_STATION";
    public static final String GROUP_SUFFIX = "_GROUP";
    public static final String CATEGORY_CALL_BELL = "call_bell";



    public static SharedPreferences prefs;
    public static State currentState;
    public static boolean isAdmin;

    @Inject
    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        isAdmin = false;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setState(State newState) {
        SharedPreferences.Editor sp = prefs.edit();

        sp.putString(HOSPITAL_KEY, newState.getHospital());
        sp.putString(LOCATION_KEY, newState.getLocation());
        sp.putString(GROUP_KEY, newState.getGroup());
        sp.putString(MODE_KEY, newState.getMode());
        sp.apply();

        currentState = new State(newState);
    }

    public void uploadedToken(boolean bool) {
        prefs.edit().putBoolean(REG_UPLOADED_KEY, true).apply();
    }

    public void setAdmin(boolean bool) {
        isAdmin = bool;
    }

    public String getStationTabletName() {
        return hospital() + STATION_SUFFIX;
    }

    public String senderId() {
        return "434312104937";
    }


}
