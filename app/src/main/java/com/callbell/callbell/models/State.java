package com.callbell.callbell.models;

import android.content.Intent;
import android.util.Log;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by austin on 10/9/15.
 */
public class State {

    public static final String HOSPITAL_ID = "HOSPITAL_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final String MODE_ID = "MODE_ID";
    public static final String PHYSICIAN = "PHYSICIAN";
    public static final String NURSE = "NURSE";
    public static final String RESIDENT = "RESIDENT";
    public static final String CHIEF_COMPLAINT = "CHIEF_COMPLAINT";

    private static final String TAG = State.class.getSimpleName();
    private String hospital,
        group,
        location,
        mode,
        physician,
        nurse,
        resident,
        chiefComplaint;

    private String[] shownActions;

    public State(PrefManager prefs) {
        hospital = prefs.hospital();
        group = prefs.group();
        location = prefs.location();
        mode = prefs.mode();
        physician = prefs.physician();
        nurse = prefs.nurse();
        resident = prefs.resident();
        chiefComplaint = prefs.chiefComplaint();
    }

    //only used for getting a state from an intent
    public State(String hos, String grp, String loc) {
        hospital = hos;
        group = grp;
        location = loc;
        mode = "";
    }

    public State(String hos, String grp, String loc, String mod, String doc, String nurs, String res, String cc) {
        hospital = hos;
        group = grp;
        location = loc;
        mode = mod;
        physician = doc;
        nurse = nurs;
        resident = res;
        chiefComplaint = cc;
    }

    public State(State st) {
        hospital = st.getHospital();
        group = st.getGroup();
        location = st.getLocation();
        mode = st.getMode();
        physician = st.getPhysician();
        resident = st.getResident();
        nurse = st.getNurse();
        chiefComplaint = st.getChiefComplaint();

    }

    public State(JSONObject object) {
        hospital = JSONUtil.getValueIfExists(object, HOSPITAL_ID);
        group = JSONUtil.getValueIfExists(object, GROUP_ID);
        location = JSONUtil.getValueIfExists(object, LOCATION_ID);
        mode = JSONUtil.getValueIfExists(object, MODE_ID);
        physician = JSONUtil.getValueIfExists(object, PHYSICIAN);
        resident = JSONUtil.getValueIfExists(object, RESIDENT);
        nurse = JSONUtil.getValueIfExists(object, NURSE);
        chiefComplaint = JSONUtil.getValueIfExists(object, CHIEF_COMPLAINT);

//            hospital = object.has("HOSPITAL_ID") ? object.getString() : "";
//            group = object.has("GROUP_ID") ? object.getString("GROUP_ID");
//            location = object.has("LOCATION_ID") ? object.getString("LOCATION_ID") : "";
//            mode = "";
//            physician = object.has("PHYSICIAN") ? object.getString("PHYSICIAN") : "";
//            resident = "";
//            nurse = object.has("NURSE") ? object.getString("NURSE") : "";
//            chiefComplaint = object.has("CHIEF_COMPLAINT") ? object.getString("CHIEF_COMPLAINT") : "";

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

    public String getPhysician() {
        return (physician != null) ? physician : "";
    }

    public String getNurse() {
        return (nurse != null) ? nurse : "";
    }

    public String getResident() {
        return (resident != null) ? resident : "";
    }

    public String getChiefComplaint() {
        return (chiefComplaint != null) ? chiefComplaint : POCValues.DEFAULT_CHOICE;
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

    public void setChiefComplaint(String cc) {
        chiefComplaint = cc;
    }

    public void setStaff(String doc, String res, String nurs) {
        physician = doc;
        resident = res;
        nurse = nurs;
    }

    public boolean equals(State other) {
        return this.hospital.equals(other.getHospital())
                && this.group.equals(other.getGroup())
                && this.location.equals(other.getLocation());
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
        object.put(PrefManager.PHYSICIAN_KEY, physician);
        object.put(PrefManager.NURSE_KEY, nurse);
        object.put(PrefManager.RESIDENT_KEY, resident);
        object.put(PrefManager.CHIEF_COMPLAINT_KEY, chiefComplaint);

        return object;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LOCATION_ID + ": " + location);
        sb.append(CHIEF_COMPLAINT + ": " + chiefComplaint);
        return sb.toString();
    }
}
