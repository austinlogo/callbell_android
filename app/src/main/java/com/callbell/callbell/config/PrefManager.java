package com.callbell.callbell.config;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManager {

    // KEYS
    public static final String REG_ID = "REGISTRATION_ID";
    public static final String REG_UPLOADED_KEY = "REGISTRATION_UPLOADED_TO_GCM_SERVER";
    public static final String DEFAULT_SENDER_KEY = "DEFAULT_SENDER";

    //STATE
    public static final String HOSPITAL_KEY = "HOSPITAL";
    public static final String LOCATION_KEY = "LOCATION";
    public static final String MODE_KEY = "MODE";

    //GLOBAL VALUES
    public static final String BED_MODE = "bed_mode";
    public static final String STATION_MODE = "station_mode";
    public static final String STATION_SUFFIX = "_station";
    public static final String CATEGORY_CALL_BELL = "call_bell";


    public static SharedPreferences prefs;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setState(String hos, String loc, String mod) {
        SharedPreferences.Editor sp = prefs.edit();

        sp.putString(HOSPITAL_KEY, hos);
        sp.putString(LOCATION_KEY, loc);
        sp.putString(MODE_KEY, mod);
        sp.apply();
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
}
