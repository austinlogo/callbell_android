package com.callbell.callbell.models;

import android.content.Intent;

import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by austin on 10/9/15.
 */
public class State {

    @Inject
    PrefManager prefs;

    private String hospital,
        group,
        location,
        mode;

    public State(String hos, String grp, String loc) {
        hospital = hos;
        group = grp;
        location = loc;
        mode = "";
    }

    public State(String hos, String grp, String loc, String mod) {
        hospital = hos;
        group = grp;
        location = loc;
        mode = mod;
    }

    public State(State st) {
        hospital = st.getHospital();
        group = st.getGroup();
        location = st.getLocation();
        mode = st.getMode();
    }

    public String getHospital() {
        return hospital;
    }

    public String getLocation() {
        return location;
    }

    public String getGroup() {
        return group;
    }

    public String getMode() {
        return mode;
    }

    public void setHospital(String hos) {
        hospital = hos;
    }

    public void setLocation(String loc) {
        location = loc;
    }

    public void setGroup(String grp) {
        group = grp;
    }

    public void setMode(String mod) {
        mode = mod;
    }

    public boolean equals(State other) {
        return this.hospital.equals(other.getHospital())
                && this.group.equals(other.getGroup())
                && this.location.equals(other.getLocation())
                && this.mode.equals(other.getMode());
    }

    public static State getStateFromIntent(Intent i) {
        return new State(i.getStringExtra(PrefManager.HOSPITAL_KEY),
                i.getStringExtra(PrefManager.GROUP_KEY),
                i.getStringExtra(PrefManager.LOCATION_KEY));
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();

        object.put(PrefManager.HOSPITAL_KEY, hospital);
        object.put(PrefManager.GROUP_KEY, group);
        object.put(PrefManager.LOCATION_KEY, location);

        return object;
    }
}
