package com.callbell.callbell.data;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

@Singleton
public class PrefManager {

    public static final String REG_ID = "REGISTRATION_ID";
    public static final String REG_UPLOADED = "REGISTRATION_UPLOADED_TO_GCM_SERVER";
    public static final String DEFAULT_SENDER_ID = "DEFAULT_SENDER_ID";

    public static SharedPreferences prefs;

    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public String getToken() {
        return "token";
    }

    public SharedPreferences getPreferences() {
        return prefs;
    }
}
