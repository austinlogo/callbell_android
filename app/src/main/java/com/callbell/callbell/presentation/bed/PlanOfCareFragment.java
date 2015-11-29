package com.callbell.callbell.presentation.bed;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.adapter.PlanOfCareCheckBoxAdapter;
import com.callbell.callbell.presentation.bed.view.DisplayItemList;
import com.callbell.callbell.presentation.toast.BeaToast;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class PlanOfCareFragment extends Fragment {
    private static final String TAG = PlanOfCareFragment.class.getSimpleName();
    private PlanOfCareInteraction mListener;

    @InjectView (R.id.fragment_poc_main_complaint_spinner)
    Spinner chiefComplaintSpinner;

    @InjectView(R.id.fragment_poc_tests)
    DisplayItemList mPlanOfCareTests;

    @InjectView(R.id.fragment_poc_medications)
    DisplayItemList mPlanOfCareMedications;

    @InjectView(R.id.other_layout)
    LinearLayout otherLayout;

    @InjectView(R.id.other_submit)
    Button submitOther;

    @InjectView(R.id.other_edittext)
    AutoCompleteTextView otherEditText;

    @Inject
    PrefManager prefs;

    private State mState;

    ArrayAdapter<String> autoCompleteOptions;
    private boolean overrideNextChiefComplaintSpinnerUpdate = false;

    // TODO: Rename and change types of parameters
    public static PlanOfCareFragment newInstance(State st) {
        PlanOfCareFragment fragment = new PlanOfCareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(State.STATE_ID, st.toJSON().toString());
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlanOfCareFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = new State(JSONUtil.getJSONFromString(getArguments().getString(State.STATE_ID)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planofcare, container, false);
        ButterKnife.inject(this, view);
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        initLists();
        initListeners();
        setSuperUserPermissions(prefs.isSuperUser());

        return view;
    }

    private void initLists() {
        //Inflate the spinner
        List<String> spinnerArray = new ArrayList<>(POCValues.pocMap.keySet());
        Collections.sort(spinnerArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_simple_spinner, spinnerArray);
        chiefComplaintSpinner.setAdapter(adapter);
        Log.d(TAG, "Current Selection: " + mState.getChiefComplaint());
        chiefComplaintSpinner.setSelection(adapter.getPosition(mState.getChiefComplaint()), false);

        //Inflate the Test List
        mPlanOfCareTests.setTitle(R.string.poc_current_tests_title);
        mPlanOfCareTests.setAdminAdapter(DisplayItemList.getAdminAdapter(getActivity(),
                mState.getAllTests(),
                chiefComplaintSpinner.getSelectedItem().toString()));
//        List<String> prefsAdminList = prefs.allTestItems();
//        List<String> initialAdminValues = (!prefsAdminList.isEmpty() )
//                ? prefsAdminList
//                : new ArrayList<>(POCValues.pocMap.get(chiefComplaintSpinner.getSelectedItem().toString()));
//        mPlanOfCareTests.setAdminAdapter(new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminValues));
        mPlanOfCareTests.setCheckedItems(mState.getShownTests());

        //Inflate the Medication List
        List<String> savedMeds = mState.getAllMedications();
        List<String> initialAdminMedicationValues = (savedMeds != null && savedMeds.size() > 0)
                ? savedMeds
                : new ArrayList<>(MedicationValues.medicationMap.keySet());

        mPlanOfCareMedications.setTitle(R.string.poc_current_medications_title);
        mPlanOfCareMedications.setAdminAdapter(new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminMedicationValues));
        mPlanOfCareMedications.setCheckedItems(mState.getShownMedications());

        //Set AutoComplete options
        String[] str = POCValues.testDescriptions.keySet().toArray(new String[0]);
        autoCompleteOptions = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                str);
        otherEditText.setAdapter(autoCompleteOptions);

        //Set enabled state
        boolean isSuperUser = prefs.isSuperUser();
        chiefComplaintSpinner.setEnabled(isSuperUser);
        mPlanOfCareTests.setDisplayMode(isSuperUser);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
    }

    private void initListeners() {

        chiefComplaintSpinner.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        mPlanOfCareTests.getPatientListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = ((TextView) view).getText().toString();
                String bodyText = POCValues.testDescriptions.get(itemText) != null
                        ? POCValues.testDescriptions.get(itemText)
                        : POCValues.testDescriptions.get(POCValues.DEFAULT_CHOICE);

                String expandedName = POCValues.expandedNames.get(itemText) != null
                        ? POCValues.expandedNames.get(itemText)
                        : null;

                mListener.showInfoDialog(itemText, expandedName, bodyText);

            }
        });

        mPlanOfCareMedications.getPatientListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = ((TextView) view).getText().toString();
                String bodyText = MedicationValues.medicationMap.get(itemText) != null
                        ? MedicationValues.medicationMap.get(itemText)
                        : MedicationValues.medicationMap.get(POCValues.DEFAULT_CHOICE); // Yes I do mean POCValues

                String expandedName = MedicationValues.expandedNames.get(itemText) != null
                        ? MedicationValues.expandedNames.get(itemText)
                        : null;

                mListener.showInfoDialog(itemText, expandedName, bodyText);
            }
        });

        submitOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOtherTestItem();
            }
        });

        otherEditText.setOnEditorActionListener(new SubmitOtherTextListener());
    }

    public void submitOtherTestItem() {
        String item = otherEditText.getText().toString();
        if (item.isEmpty()) {
            BeaToast.makeText(getActivity().getApplicationContext(), R.string.empty_action_item, Toast.LENGTH_SHORT).show();
            return;
        } else if (mPlanOfCareTests.contains(item)) {
            int index = mPlanOfCareTests.getPosition(item);

            mPlanOfCareTests.getAdminListView().setItemChecked(index, true);
            otherEditText.setText("");
            return;
        }

        String action = otherEditText.getText().toString();
        mPlanOfCareTests.add(action);
        mPlanOfCareTests.getAdminListView().setItemChecked(mPlanOfCareTests.getCount() - 1, true);
        otherEditText.setText("");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach called");
        try {
            mListener = (PlanOfCareInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement StationActivityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach called");
        mListener = null;
    }

    private void saveValues() {
        mState.setShownMedications(mPlanOfCareMedications.updatePatientList());

//        prefs.setShownTestItems();
//        prefs.setAllActionTestItems(mPlanOfCareTests.getActionList());

        mState.setShownTests(mPlanOfCareTests.updatePatientList());
//        prefs.setShownMedicationItems();
//        prefs.setAllActionMedicationItems(mPlanOfCareMedications.getActionList());

//        mListener.saveListItems(mPlanOfCareTests.getActionList(), mPlanOfCareMedications.getActionList());

        mState.setAllTests(mPlanOfCareTests.getActionList());
        mState.setAllMedications(mPlanOfCareMedications.getActionList());
    }

    public void setSuperUserPermissions(boolean isSuperUser) {

        saveValues();

        mListener.savePOCState(mState);

        //Setting UI Values
        mPlanOfCareTests.setDisplayMode(isSuperUser);
        mPlanOfCareMedications.setDisplayMode(isSuperUser);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        chiefComplaintSpinner.setEnabled(isSuperUser);
        chiefComplaintSpinner.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        mPlanOfCareTests.setVisibility((!isSuperUser && mPlanOfCareTests.getShownItemCount() == 0) ? View.GONE : View.VISIBLE);
        mPlanOfCareMedications.setVisibility( (!isSuperUser && mPlanOfCareMedications.getShownItemCount() == 0 ) ? View.GONE : View.VISIBLE);
    }

    public void clearValues() {
        mState.setShownMedications(new ArrayList<Integer>());
        mState.setShownTests(new ArrayList<Integer>());

//        prefs.setShownTestItems(new ArrayList<Integer>());
//        prefs.setShownMedicationItems(new ArrayList<Integer>());
        mPlanOfCareTests.clear();
        mPlanOfCareMedications.clear();
//        mPlanOfCareTests.setCheckedItems(prefs.shownTestItems());
    }

    public void updateState(State st) {
        mState = st;
        initLists();
        overrideNextChiefComplaintSpinnerUpdate = true;
        setSuperUserPermissions(prefs.isSuperUser());
    }

    public State getState() {

        saveValues();
        return mState;
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (overrideNextChiefComplaintSpinnerUpdate) {
                overrideNextChiefComplaintSpinnerUpdate = false;
                return;
            }

            String selectedComplaint = parent.getSelectedItem().toString();


            mState.setChiefComplaint(selectedComplaint);
            mPlanOfCareTests.resetList(selectedComplaint);
            mState.setShownTests(new ArrayList<Integer>());
            mPlanOfCareTests.setCheckedItems(mState.getShownTests());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // NOOP
        }
    }

    public interface PlanOfCareInteraction {
        void showInfoDialog(String tit, String expandedName, String bod);
        void savePOCState(State st);
    }

    public class SubmitOtherTextListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitOtherTestItem();
                return true;
            }

            return false;
        }
    }
}
