package com.callbell.callbell.models.State;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austin on 10/9/15.
 */
public class State {

    public static final String HOSPITAL_ID = "HOSPITAL_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final String MODE_ID = "MODE_ID";
    public static final String PHYSICIAN = "PHYSICIAN_ID";
    public static final String NURSE = "NURSE_ID";
    public static final String RESIDENT = "RESIDENT_ID";
    public static final String CHIEF_COMPLAINT = "CHIEF_COMPLAINT_ID";
    public static final String PAIN_RATING = "PAIN_RATING_ID";
    public static final String REGISTRATION_ID = "REGISTRATION_ID";
    public static final String CONNECTION_INDICATOR_ID = "CONNECTION_INDICATOR_ID";
    public static final String STATE_ID = "STATE_ID";

    private static final String TAG = State.class.getSimpleName();
    private boolean isConnectedValue = true;
    private String hospital,
        group,
        location,
        mode,
        physician,
        nurse,
        resident,
        chiefComplaint;

    private int painRating;

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
        painRating = prefs.painRating();
    }

    public State(String hos, String grp, String loc, String mod, String doc, String nurs, String res, String cc, int pr) {
        hospital = hos;
        group = grp;
        location = loc;
        mode = mod;
        physician = doc;
        nurse = nurs;
        resident = res;
        chiefComplaint = cc;
        painRating = pr;
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
        painRating = st.getPainRating();
        isConnectedValue = st.isConnected();

    }

    public State(JSONObject object) {
        hospital = JSONUtil.getValueStringIfExists(object, HOSPITAL_ID);
        group = JSONUtil.getValueStringIfExists(object, GROUP_ID);
        location = JSONUtil.getValueStringIfExists(object, LOCATION_ID);
        mode = JSONUtil.getValueStringIfExists(object, MODE_ID);
        physician = JSONUtil.getValueStringIfExists(object, PHYSICIAN);
        resident = JSONUtil.getValueStringIfExists(object, RESIDENT);
        nurse = JSONUtil.getValueStringIfExists(object, NURSE);
        chiefComplaint = JSONUtil.getValueStringIfExists(object, CHIEF_COMPLAINT);
        painRating = JSONUtil.getValueIntIfExists(object, PAIN_RATING);
        isConnectedValue = JSONUtil.getValueBooleanFromIntIfExists(object, CONNECTION_INDICATOR_ID);
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

    public boolean isConnected() {
        return isConnectedValue;
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

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();

        object.put(HOSPITAL_ID, hospital);
        object.put(GROUP_ID, group);
        object.put(LOCATION_ID, location);
        object.put(PHYSICIAN, physician);
        object.put(NURSE, nurse);
        object.put(RESIDENT, resident);
        object.put(CHIEF_COMPLAINT, chiefComplaint);
        object.put(PAIN_RATING, painRating);

        return object;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HOSPITAL_ID + ": " + hospital);
        sb.append(GROUP_ID + ": " + group);
        sb.append(PHYSICIAN + ": " + physician);
        sb.append(LOCATION_ID + ": " + location);
        sb.append(CHIEF_COMPLAINT + ": " + chiefComplaint);
        sb.append(PAIN_RATING + ": " + painRating);
        return sb.toString();
    }

    public int getPainRating() {
        return painRating;
    }

    public void setPainRating(int painRating) {
        this.painRating = painRating;
    }

    public String getTabletName() {
        return hospital + "_" + group + "_" + location;
    }

    public void setConnected(boolean connected) {
        this.isConnectedValue = connected;
    }
}
