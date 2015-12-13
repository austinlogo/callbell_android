package com.callbell.callbell.util;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State.State;

import org.json.JSONArray;
import org.json.JSONException;

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
    public static final String SHOWN_TESTS_KEY = "shown_actions_id";
    public static final String SHOWN_MEDICATIONS_KEY = "shown_tests_id";
    public static final String STATELIST_KEY = "stateList";
    public static final String ALL_TEST_ITEMS_KEY = "all_action_items";
    private static final String ALL_MEDICATION_ITEMS_KEY = "all_med_items";


    //GLOBAL VALUES
    public static final String BED_MODE = "bed_mode";
    public static final String STATION_MODE = "station_mode";
    public static final String STATION_SUFFIX = "STATION";
    public static final String GROUP_SUFFIX = "_GROUP";

    //CATEGORIES
    public static final String CATEGORY_CALL_BELL = "call_bell";
    public static final String CATEGORY_RATE_PAIN = "rate_pain";
    public static final String CATEGORY_TABLET_STATE_UPDATE = "tablet_state";

    //EVENTS
    public static final String EVENT_MESSAGE_RECEIVED = "MESSAGE RECEIVED";
    public static final String EVENT_STATES_RECEIVED = "STATES RECEIVED";
    public static final String EVENT_STATE_UPDATE = "TABLET STATE UPDATED";
    public static final String EVENT_SU_MODE_CHANGE = "SU MODE CHANGE";
    public static final String EVENT_SERVER_CONNECTION_CHANGED = "TITLE BAR VIEW TOGGLE";
    public static final String EVENT_TABLET_CONNECTIONS_RECEIVED = "TABLET CONNECTIONS RECEIVED";
    public static final String EVENT_RATE_PAIN = "EVENT_RATE_PAIN";

    //MISC. VALUES
    public static final String DEFAULT_SU_PASSWORD = "2468";
    private static final String TAG = PrefManager.class.getSimpleName();
    public static final String STATELIST_RESPONSE = "STATELIST RESPONSE";
    public static final String CALL_BELL_MESSAGE_BODY = "message_body";
    public static final String CALL_BELL_MESSAGE_FROM = "message_from";
    public static final String CATEGORY_CALL_BELL_RESPONSE = "call_bell_response";
    public static final String SERVER_CONNECTED = "SERVER DISCONNECTED";
    public static final String PAYLOAD = "PAYLOAD";


    public static SharedPreferences prefs;
    public static State currentState;
    public static boolean isSuperUser;

    private static List<Integer> shownTestItems;
    private static List<String> allTestItems;

    private static List<Integer> shownMedicationItems;
    private static List<String> allMedicationItems;

    @Inject
    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        currentState = new State(this);
        isSuperUser = false;
        shownTestItems = getIntListFromPrefs(SHOWN_TESTS_KEY);
        shownMedicationItems = getIntListFromPrefs(SHOWN_MEDICATIONS_KEY);

    }

    public String getToken() {
        return prefs.getString(REG_ID, "");
    }

    // STATE ///////////////////////////////////////////////////////////////////////////////////////

    public String hospital() {
        return prefs.getString(State.HOSPITAL_ID, "");
    }

    public String location() {
        return prefs.getString(State.LOCATION_ID, "");
    }

    public String mode() {
        return prefs.getString(State.MODE_ID, "");
    }

    public String group() {
        return prefs.getString(State.GROUP_ID, "");
    }

    public int painRating() {
        return prefs.getInt(State.PAIN_RATING, 0);
    }

    public String physician() {
        return prefs.getString(State.PHYSICIAN, "");
    }

    public String resident() {
        return prefs.getString(State.RESIDENT, "");
    }

    public String nurse() {
        return prefs.getString(State.NURSE, "");
    }

    public String chiefComplaint() {
        return prefs.getString(State.CHIEF_COMPLAINT, POCValues.DEFAULT_CHOICE);
    }

    public void setState(State newState) {
        SharedPreferences.Editor sp = prefs.edit();

        Log.d(TAG, "RES: " + resident());

        sp.putString(State.HOSPITAL_ID, newState.getHospital());
        sp.putString(State.LOCATION_ID, newState.getLocation());
        sp.putString(State.GROUP_ID, newState.getGroup());
        sp.putString(State.MODE_ID, newState.getMode());
        sp.putString(State.PHYSICIAN, newState.getPhysician());
        sp.putString(State.NURSE, newState.getNurse());
        sp.putString(State.RESIDENT, newState.getResident());
        sp.putString(State.CHIEF_COMPLAINT, newState.getChiefComplaint());
        sp.putInt(State.PAIN_RATING, newState.getPainRating());
        sp.apply();

        setShownTestItems(newState.getShownTests());
        setShownMedicationItems(newState.getShownMedications());

        setAllActionTestItems(newState.getAllTests());
        setAllActionMedicationItems(newState.getAllMedications());

        Log.d(TAG, "RES2: " + resident());

        currentState = new State(newState);
    }

    public void setPainRating(int rating) {
        currentState.setPainRating(rating);
        prefs.edit().putInt(State.PAIN_RATING, rating).apply();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setStaff(String doc, String res, String nurse) {
        currentState.setStaff(doc, res, nurse);

        SharedPreferences.Editor sp = prefs.edit();
        sp.putString(State.PHYSICIAN, doc);
        sp.putString(State.RESIDENT, res);
        sp.putString(State.NURSE, nurse);
        sp.commit();
    }

    // Preferences /////////////////////////////////////////////////////////////////////////////////

    public SharedPreferences getPreferences() {
        return prefs;
    }

    // Plan of Care ////////////////////////////////////////////////////////////////////////////////

    public List<String> allTestItems() {
        if (allTestItems == null) {
            Set<String> set = prefs.getStringSet(ALL_TEST_ITEMS_KEY, new HashSet<String>());
            return new ArrayList<>(set);
        }

        return allTestItems;
    }

    public List<Integer> shownTestItems() {

        return shownTestItems == null ? new ArrayList<Integer>() : shownTestItems;

    }

    public List<Integer> shownMedicationItems() {

        return shownMedicationItems == null ? new ArrayList<Integer>() : shownMedicationItems;

    }


    public void setShownTestItems(List<Integer> sa) {
        currentState.setShownTests(sa);
        shownTestItems = sa;
        setIntList(sa, SHOWN_TESTS_KEY);
    }

    public void setPendingTestItems(List<Integer> sa) {
        shownTestItems = sa;
        setIntList(sa, SHOWN_TESTS_KEY);
    }

    public void setShownMedicationItems(List<Integer> meds) {
        currentState.setShownMedications(meds);
        shownMedicationItems = meds;
        setIntList(meds, SHOWN_MEDICATIONS_KEY);
    }

    public void setPendingMedicationItems(List<Integer> medItems) {
        shownMedicationItems = medItems;
        setIntList(medItems, SHOWN_MEDICATIONS_KEY);
    }

    public void setIntList(List<Integer> list, String key) {
        Log.d(TAG, "SET SHOWN ACTIONS");
        SharedPreferences.Editor sp = prefs.edit();


        if (list == null) {
            sp.putString(key, "").apply();
            return;
        }

        JSONArray array = new JSONArray();
        for (int i : list) {
            array.put(i);
        }

        Log.d(TAG, "setPendingTestItems-Array: " + array.toString());
        sp.putString(key, array.toString()).apply();
    }

    private List<Integer> getIntListFromPrefs(String key) {
        try {
            String arrayString = prefs.getString(key, "");
            Log.d(TAG, "shownTestItems-checkedValuePostions: " + arrayString);

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

    /////////////////////////////////////////////////////////////////

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setAllActionTestItems(List<String> actionList) {
        if (actionList != null) {
            allTestItems = actionList;
        } else {
            allTestItems = new ArrayList<>();
        }
        allTestItems = actionList;

        SharedPreferences.Editor sp = prefs.edit();
        sp.putStringSet(ALL_TEST_ITEMS_KEY, new HashSet<>(actionList)).commit();
    }

    public void setAllActionMedicationItems(List<String> actionList) {
        if (actionList == null) {
            allMedicationItems = new ArrayList<>();
        } else {
            allMedicationItems = actionList;
        }

        SharedPreferences.Editor sp = prefs.edit();
        sp.putStringSet(ALL_MEDICATION_ITEMS_KEY, new HashSet<>(allMedicationItems)).commit();
    }

    public List<String> getAllMedicationItems() {
        return allMedicationItems;
    }

    public void uploadedToken(boolean bool) {
        prefs.edit().putBoolean(REG_UPLOADED_KEY, true).apply();
    }

    public void setSuperUser(boolean bool) {
        isSuperUser = bool;
    }

    public String getStationName() {
        return "STATION";
    }

    public String senderId() {
        return "434312104937";
    }

    public void setPOC(State st) {
        currentState.setChiefComplaint(st.getChiefComplaint());
        currentState.setAllMedications(st.getAllMedications());
        currentState.setAllTests(st.getAllTests());
        currentState.setShownMedications(st.getShownMedications());
        currentState.setShownTests(st.getShownTests());
        setState(currentState);
    }

    public void setAcceptablePain(int acceptablePain) {
        currentState.setAcceptablePain(acceptablePain);
        prefs.edit().putInt(State.ACCEPTABLE_PAIN_ID, acceptablePain).apply();
    }

    public int acceptablePain() {
        return prefs.getInt(State.ACCEPTABLE_PAIN_ID, 0);
    }
}
