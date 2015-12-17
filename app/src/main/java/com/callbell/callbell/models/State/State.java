package com.callbell.callbell.models.State;

import android.util.Log;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public static final String TABLET_NAME_ID = "TABLE_NAME_ID";
    public static final String STATION_TABLET_NAME_ID = "STATION_TABLET_NAME_ID";
    public static final String PENDING_TESTS_ID = "PENDING_TESTS_ID";
    public static final String PENDING_MEDICATIONS_ID = "PENDING_MEDICATIONS_ID";
    public static final String DONE_TESTS_ID = "DONE_TESTS_ID";
    public static final String DONE_MEDICATIONS_ID = "DONE_MEDICATIONS_ID";
    public static final String ALL_TESTS_ID = "ALL_TESTS_ID";
    public static final String ALL_MEDICATIONS_ID = "ALL_MEDICATIONS_ID";
    public static final String ACCEPTABLE_PAIN_ID = "ACCEPTABLE_PAIN_ID";
    public static final String STATE_ID = "STATE_ID";

    private static final String TAG = State.class.getSimpleName();
    public static final int MAX_PAIN = 10;
    private boolean isConnectedValue = true;
    private String hospital,
        group,
        location,
        mode,
        physician,
        nurse,
        resident,
        chiefComplaint;

    private int painRating,
            acceptablePain;

    private List<Integer> pendingTests,
            pendingMedications,
            doneTests,
            doneMedications;

    private List<String> allTests,
            allMedications;

    private String[] shownActions;

    public static State newBlankInstance(String hospital, String group, String location, String mod) {
        return new State(
                hospital,
                group,
                location,
                mod,
                "",
                "",
                "",
                "",
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                0,
                0
        );
    }

    public State(PrefManager prefs) {
        hospital = prefs.hospital();
        group = prefs.group();
        location = prefs.location();
        mode = prefs.mode();
        physician = prefs.physician();
        nurse = prefs.nurse();
        resident = prefs.resident();
        chiefComplaint = prefs.chiefComplaint();
        acceptablePain = prefs.acceptablePain();
        painRating = prefs.painRating();
    }

    public State(
            String hos,
            String grp,
            String loc,
            String mod,
            String doc,
            String nurs,
            String res,
            String cc,
            List<Integer> tests,
            List<Integer> meds,
            List<Integer> dt,
            List<Integer> dm,
            List<String> at,
            List<String> am,
            int ap,
            int pr) {

        hospital = hos;
        group = grp;
        location = loc;
        mode = mod;
        physician = doc;
        nurse = nurs;
        resident = res;
        chiefComplaint = cc;
        pendingTests = tests;
        pendingMedications = meds;
        doneTests = dt;
        doneMedications = dm;
        allTests = at;
        allMedications = am;
        acceptablePain = ap;
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
        pendingTests = st.getPendingTests();
        pendingMedications = st.getPendingMedications();
        doneTests = st.getDoneTests();
        doneMedications = st.getDoneMedications();
        allTests = st.getAllTests();
        allMedications = st.getAllMedications();
        acceptablePain = st.getAcceptablePain();
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
        pendingTests = JSONUtil.getvalueListIfExists(object, PENDING_TESTS_ID);
        pendingMedications = JSONUtil.getvalueListIfExists(object, PENDING_MEDICATIONS_ID);
        doneTests = JSONUtil.getvalueListIfExists(object, DONE_TESTS_ID);
        doneMedications = JSONUtil.getvalueListIfExists(object, DONE_MEDICATIONS_ID);
        allTests = JSONUtil.getValueStringListIfExists(object, ALL_TESTS_ID);
        allMedications = JSONUtil.getValueStringListIfExists(object, ALL_MEDICATIONS_ID);
        acceptablePain = JSONUtil.getValueIntIfExists(object, ACCEPTABLE_PAIN_ID);
        isConnectedValue = JSONUtil.getValueBooleanFromIntIfExists(object, CONNECTION_INDICATOR_ID);
    }

    public State(String stringExtra) {
        this(JSONUtil.getJSONFromString(stringExtra));
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

    public List<Integer> getPendingTests() {
        return pendingTests;
    }

    public List<Integer> getPendingMedications() {
        return pendingMedications;
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

    public void setChiefComplaint(String cc) {
        chiefComplaint = cc;
    }

    public void setStaff(String doc, String res, String nurs) {
        physician = doc;
        resident = res;
        nurse = nurs;
    }

    public void setPendingTests(List<Integer> tests) {
        pendingTests = tests;
    }

    public void setPendingMedications(List<Integer> meds) {
        pendingMedications = meds;
    }

    public void setDoneTests(List<Integer> tests) {
        doneTests = tests;
    }

    public void setDoneMedications(List<Integer> meds) {
        doneMedications = meds;
    }

    public boolean equals(State other) {
        return this.hospital.equals(other.getHospital())
                && this.group.equals(other.getGroup())
                && this.location.equals(other.getLocation());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        try {
            object.put(HOSPITAL_ID, hospital);
            object.put(GROUP_ID, group);
            object.put(LOCATION_ID, location);
            object.put(PHYSICIAN, physician);
            object.put(NURSE, nurse);
            object.put(RESIDENT, resident);
            object.put(CHIEF_COMPLAINT, chiefComplaint);
            object.put(PAIN_RATING, painRating);
            object.put(TABLET_NAME_ID, getTabletName());
            object.put(STATION_TABLET_NAME_ID, getStationTabletName());
            object.put(PENDING_TESTS_ID, JSONUtil.integerListToJSONArray(pendingTests));
            object.put(PENDING_MEDICATIONS_ID, JSONUtil.integerListToJSONArray(pendingMedications));
            object.put(DONE_TESTS_ID, JSONUtil.integerListToJSONArray(doneTests));
            object.put(DONE_MEDICATIONS_ID, JSONUtil.integerListToJSONArray(doneMedications));
            object.put(ALL_TESTS_ID, JSONUtil.stringListToJSONArray(allTests));
            object.put(ALL_MEDICATIONS_ID, JSONUtil.stringListToJSONArray(allMedications));
            object.put(CONNECTION_INDICATOR_ID, isConnectedValue ? 1 : 0);
            object.put(ACCEPTABLE_PAIN_ID, acceptablePain);

            return object;
        } catch (JSONException e) {
            Log.e(TAG, "Unable to make State a JSON: " + e);
            return new JSONObject();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HOSPITAL_ID + ": " + hospital + ", ");
        sb.append(GROUP_ID + ": " + group + ", ");
        sb.append(PHYSICIAN + ": " + physician + ", ");
        sb.append(LOCATION_ID + ": " + location + ", ");
        sb.append(CHIEF_COMPLAINT + ": " + chiefComplaint + ", ");
        sb.append(PAIN_RATING + ": " + painRating + ", ");

        return sb.toString();
    }

    public boolean isTestListEmpty() {
        return pendingTests.isEmpty() && doneTests.isEmpty();
    }

    public boolean isMedicationListEmpty() {
        return pendingMedications.isEmpty() && doneMedications.isEmpty();
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

    public String getStationTabletName() {
        return hospital + "_" + group + "_" + PrefManager.STATION_SUFFIX;
    }

    public void setConnected(boolean connected) {
        this.isConnectedValue = connected;
    }

    public void setAllMedications(List<String> allMeds) {
        allMedications = allMeds;
    }

    public void setAllTests(List<String> at) {
        allTests = at;
    }

    public List<String> getAllMedications() {
        return allMedications;
    }

    public List<String> getAllTests() {
        return allTests;
    }

    public void setAcceptablePain(int ap) {
        acceptablePain = ap;
    }

    public int getAcceptablePain() {
        return acceptablePain;
    }

    public List<Integer> getDoneTests() {
        return doneTests;
    }

    public List<Integer> getDoneMedications() {
        return doneMedications;
    }
}
