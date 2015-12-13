package com.callbell.callbell.presentation.bed;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
import android.widget.EditText;
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
import com.callbell.callbell.presentation.bed.view.ToggleListView;
import com.callbell.callbell.presentation.toast.BeaToast;
import com.callbell.callbell.presentation.view.TernaryListItem;
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

    @InjectView(R.id.fragment_poc_tests_or_title_container)
    LinearLayout mPOCSpinnerAndAcceptablePainContainer;

    @InjectView(R.id.fragment_poc_acceptable_pain_value)
    EditText mAcceptablePainText;

    @InjectView(R.id.fragment_poc_tests)
    ToggleListView mPlanOfCareTests;

    @InjectView(R.id.fragment_poc_medications)
    ToggleListView mPlanOfCareMedications;

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

        initListsAndValues();
        initListeners();
        setSuperUserPermissions(prefs.isSuperUser());

        return view;
    }

    private void initListsAndValues() {
        //Inflate the spinner
        List<String> spinnerArray = new ArrayList<>(POCValues.pocMap.keySet());
        Collections.sort(spinnerArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_simple_spinner, spinnerArray);
        chiefComplaintSpinner.setAdapter(adapter);
        Log.d(TAG, "Current Selection: " + mState.getChiefComplaint());
        chiefComplaintSpinner.setSelection(adapter.getPosition(mState.getChiefComplaint()), false);

        //Inflate the Test List
        mPlanOfCareTests.setTitle(R.string.poc_current_tests_title);

        mPlanOfCareTests.setAdminAdapter(ToggleListView.getAdminAdapter(getActivity(),
                mState.getAllTests(),
                chiefComplaintSpinner.getSelectedItem().toString()));

        mPlanOfCareTests.setCheckedItems(mState.getPendingTests(), mState.getDoneTests());

        //Inflate the Medication List
        List<String> savedMeds = mState.getAllMedications();
        List<String> initialAdminMedicationValues = (savedMeds != null && savedMeds.size() > 0)
                ? savedMeds
                : new ArrayList<>(MedicationValues.medicationMap.keySet());

        mPlanOfCareMedications.setAdminAdapter(new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_state_test, initialAdminMedicationValues));
        mPlanOfCareMedications.setTitle(R.string.poc_current_medications_title);
        mPlanOfCareMedications.setCheckedItems(mState.getPendingMedications(), mState.getDoneMedications());



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

        mAcceptablePainText.setText(String.format("%d", mState.getAcceptablePain()));
    }

    private void initListeners() {

        chiefComplaintSpinner.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        mPlanOfCareTests.getPatientListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = ((TernaryListItem) view).getText();
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
                String itemText = ((TernaryListItem) view).getText().toString();
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

        mPlanOfCareMedications.updatePatientList();
        mPlanOfCareTests.updatePatientList();

        mState.setPendingMedications(mPlanOfCareMedications.getPendingIndexes());
        mState.setPendingTests(mPlanOfCareTests.getPendingIndexes());
        mState.setDoneTests(mPlanOfCareTests.getDoneIndexes());
        mState.setDoneMedications(mPlanOfCareMedications.getDoneIndexes());
        mState.setAllTests(mPlanOfCareTests.getActionList());
        mState.setAllMedications(mPlanOfCareMedications.getActionList());

        try {
            int acceptablePain = Integer.parseInt(mAcceptablePainText.getText().toString());
            if (acceptablePain > State.MAX_PAIN) {
                acceptablePain = State.MAX_PAIN;
            }

            mState.setAcceptablePain(acceptablePain);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            mState.setAcceptablePain(0);
        }
    }

    public void setSuperUserPermissions(boolean isSuperUser) {

        saveValues();

        mListener.savePOCState(mState);

        //Setting UI Values
        mPlanOfCareTests.setDisplayMode(isSuperUser);
        mPlanOfCareMedications.setDisplayMode(isSuperUser);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        chiefComplaintSpinner.setEnabled(isSuperUser);
        mPOCSpinnerAndAcceptablePainContainer.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);

        Log.d(TAG, "AFL " + mState.getPendingTests().size());

        mPlanOfCareTests.setVisibility((!isSuperUser && mState.getPendingTests().size() == 0) ? View.GONE : View.VISIBLE);
        Log.d(TAG, "AFL " + mPlanOfCareTests.getShownItemCount());
        mPlanOfCareMedications.setVisibility( (!isSuperUser && mState.getPendingMedications().size() == 0 ) ? View.GONE : View.VISIBLE);
    }

    public void clearValues() {
        mState.setPendingMedications(new ArrayList<Integer>());
        mState.setPendingTests(new ArrayList<Integer>());

        mPlanOfCareTests.clear();
        mPlanOfCareMedications.clear();
    }

    public void updateState(State st) {
        mState = st;
        mListener.savePOCState(mState);
        initListsAndValues();
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

            mState.setPendingTests(new ArrayList<Integer>());
            mState.setDoneTests(new ArrayList<Integer>());
            mPlanOfCareTests.setCheckedItems(mState.getPendingTests(), mState.getDoneTests());

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
