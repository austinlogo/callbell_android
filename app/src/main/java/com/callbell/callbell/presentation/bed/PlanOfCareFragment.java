package com.callbell.callbell.presentation.bed;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.adapter.PlanOfCareCheckBoxAdapter;
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
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PlanOfCareFragment extends Fragment {
    private static final String TAG = PlanOfCareFragment.class.getSimpleName();
    private PlanOfCareInteraction mListener;

    @InjectView (R.id.fragment_poc_main_complaint_spinner)
    Spinner chiefComplaintSpinner;

    @InjectView (R.id.fragment_poc_tests_title)
    TextView mTestsTitle;

    @InjectView(R.id.action_list_test_admin)
    ListView actionListTestAdmin;

    @InjectView(R.id.action_list_test_patient)
    ListView actionListTestPatient;

    @InjectView(R.id.action_list_medication_admin)
    ListView actionListMedicationAdmin;

    @InjectView(R.id.action_list_medication_patient)
    ListView actionListMedicationPatient;

    @InjectView(R.id.other_layout)
    LinearLayout otherLayout;

    @InjectView(R.id.other_submit)
    Button submitOther;

    @InjectView(R.id.other_edittext)
    AutoCompleteTextView otherEditText;

    @Inject
    PrefManager prefs;

    @Inject
    POCValues pocValues;

    @Inject
    MedicationValues medValues;

    ArrayAdapter<String> autoCompleteOptions;
    PlanOfCareCheckBoxAdapter actionArrayTestAdapter;
    ArrayAdapter<String> patientPlanOfCareListTestAdapter;
    PlanOfCareCheckBoxAdapter actionArrayMedicationAdapter;
    ArrayAdapter<String> patientPlanOfCareListMedicationAdapter;


    private List<String> patientPlanOfCareTestList;
    private List<String> patientPlanOfCareMedicationList;

    // TODO: Rename and change types of parameters
    public static PlanOfCareFragment newInstance() {
        PlanOfCareFragment fragment = new PlanOfCareFragment();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        chiefComplaintSpinner.setAdapter(adapter);
        Log.d(TAG, "Current Selection: " + prefs.getCurrentState().getChiefComplaint());
        chiefComplaintSpinner.setSelection(adapter.getPosition(prefs.getCurrentState().getChiefComplaint()), false);

        //Inflate the Admin Checkbox
        List<String> prefsAdminList = prefs.allTestItems();
        List<String> initialAdminValues = (!prefsAdminList.isEmpty() )
                ? prefsAdminList
                : new ArrayList<>(POCValues.pocMap.get(chiefComplaintSpinner.getSelectedItem().toString()));
        actionListTestAdmin.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        actionArrayTestAdapter = new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminValues);
        actionListTestAdmin.setAdapter(actionArrayTestAdapter);
        setCheckedAdminItems(prefs.shownTestItems(), actionListTestAdmin);

        //Inflate the patient Test List
        patientPlanOfCareTestList = filterSelectedChoices(prefs.shownTestItems(), actionListTestAdmin, actionArrayTestAdapter);
        Log.d(TAG, "patientPOCLIST length:" + patientPlanOfCareTestList.size());
        Log.d(TAG, "Activity: " + getActivity());
        patientPlanOfCareListTestAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, patientPlanOfCareTestList);
        actionListTestPatient.setAdapter(patientPlanOfCareListTestAdapter);
        actionListTestPatient.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        actionListTestPatient.setItemsCanFocus(true);


        // ======================================================================



        //Inflate the Admin Medication Checkbox
        List<String> prefsAdminMedicationList = prefs.allTestItems();

        List<String> initialAdminMedicationValues = (!prefsAdminMedicationList.isEmpty() )
                ? prefsAdminList
                : new ArrayList<>(MedicationValues.medicationMap.keySet());
        actionListMedicationAdmin.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        actionArrayMedicationAdapter = new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminMedicationValues);
        actionListMedicationAdmin.setAdapter(actionArrayMedicationAdapter);
        setCheckedAdminItems(prefs.shownMedicationItems(), actionListMedicationAdmin);

        //Inflate the patient Medication List
        patientPlanOfCareMedicationList = filterSelectedChoices(prefs.shownMedicationItems(), actionListMedicationAdmin, actionArrayMedicationAdapter);
        Log.d(TAG, "patientPOCLIST length:" + patientPlanOfCareMedicationList.size());
        Log.d(TAG, "Activity: " + getActivity());
        patientPlanOfCareListMedicationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, patientPlanOfCareMedicationList);
        actionListMedicationPatient.setAdapter(patientPlanOfCareListMedicationAdapter);
        actionListMedicationPatient.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        actionListMedicationPatient.setItemsCanFocus(true);


        //Set AutoComplete options
        String[] str = POCValues.testDescriptions.keySet().toArray(new String[0]);
        autoCompleteOptions = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                str);
        otherEditText.setAdapter(autoCompleteOptions);

        //Set enabled state
        boolean isSuperUser = prefs.isSuperUser();
        chiefComplaintSpinner.setEnabled(isSuperUser);
        actionListTestAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListTestPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
    }

    private void initListeners() {

        chiefComplaintSpinner.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        actionListTestPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = ((TextView) view).getText().toString();
                String bodyText = POCValues.testDescriptions.get(itemText) != null
                        ? POCValues.testDescriptions.get(itemText)
                        : POCValues.testDescriptions.get(POCValues.DEFAULT_CHOICE);
                mListener.showInfoDialog(itemText, bodyText);
            }
        });

        actionListMedicationPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = ((TextView) view).getText().toString();
                String bodyText = MedicationValues.medicationMap.get(itemText) != null
                        ? MedicationValues.medicationMap.get(itemText)
                        : MedicationValues.medicationMap.get(POCValues.DEFAULT_CHOICE); // Yes I do mean POCValues
                mListener.showInfoDialog(itemText, bodyText);
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
            Toast.makeText(getActivity().getApplicationContext(), R.string.empty_action_item, Toast.LENGTH_SHORT).show();
            return;
        } else if (actionArrayTestAdapter.contains(item)) {
            int index = actionArrayTestAdapter.getPosition(item);
//            Toast.makeText(getActivity().getApplicationContext(), R.string.duplicate_item, Toast.LENGTH_SHORT).show();
            actionListTestAdmin.setItemChecked(index, true);
            otherEditText.setText("");
            return;
        }
        String action = otherEditText.getText().toString();
        actionArrayTestAdapter.add(action);
        actionListTestAdmin.setItemChecked(actionArrayTestAdapter.getCount() - 1, true);
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

    public void setSuperUserPermissions(boolean isSuperUser) {
        //TESTS
        SparseBooleanArray checked = actionListTestAdmin.getCheckedItemPositions();

        // Setting Checked Items
        if (!isSuperUser && checked != null) {

            Log.d(TAG, "booleanArray: " + checked.toString());
            Log.d(TAG, "checked size is " + actionListTestAdmin.getCheckedItemCount());

            List<Integer> checkedTestArray = new ArrayList<>(getCheckedItemCount(actionListTestAdmin));
            for (int index = 0; index < actionArrayTestAdapter.getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checked.get(index));
                if (checked.get(index)) {
                    checkedTestArray.add(index);
                }
            }

            prefs.setShownTestItems(checkedTestArray);
            prefs.setAllActionTestItems(actionArrayTestAdapter.getList());
            setPatientListAdapter();
        }

        //Medications
        SparseBooleanArray checkedMedications = actionListMedicationAdmin.getCheckedItemPositions();

        // Setting Checked Items
        if (!isSuperUser && checkedMedications != null) {

            Log.d(TAG, "Medication booleanArray: " + checkedMedications.toString());
            Log.d(TAG, "checked size is " + actionListMedicationAdmin.getCheckedItemCount());

            List<Integer> checkedMedicationArray = new ArrayList<>(getCheckedItemCount(actionListMedicationAdmin));
            for (int index = 0; index < actionArrayMedicationAdapter.getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checkedMedications.get(index));
                if (checkedMedications.get(index)) {
                    checkedMedicationArray.add(index);
                }
            }

            prefs.setShownMedicationItems(checkedMedicationArray);
            prefs.setAllActionMedicationItems(actionArrayMedicationAdapter.getList());
            setPatientListMedicationAdapter();
        }

        //Setting UI Values
        actionListTestAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListTestPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
        actionListMedicationAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListMedicationPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        chiefComplaintSpinner.setEnabled(isSuperUser);
        actionListTestAdmin.setEnabled(isSuperUser);
        chiefComplaintSpinner.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        mTestsTitle.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);

    }

    private int getCheckedItemCount(ListView list) {
        SparseBooleanArray checkedItems = actionListTestAdmin.getCheckedItemPositions();
        int count = 0;

        for (int index = 0; index < list.getAdapter().getCount(); index++) {
            if (checkedItems.get(index)) {
                count++;
            }
        }
        return count;
    }

    private void setCheckedAdminItems(List<Integer> shownItems, ListView view) {
        for (int i = 0; i < view.getCount(); i++) {
            view.setItemChecked(i, shownItems.contains(i));
        }
    }

    public void clearValues() {
        prefs.setShownTestItems(new ArrayList<Integer>());
        prefs.setShownMedicationItems(new ArrayList<Integer>());
        patientPlanOfCareListTestAdapter.clear();
        setCheckedAdminItems(prefs.shownTestItems(), actionListTestAdmin);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private void setPatientListAdapter() {
        patientPlanOfCareTestList = filterSelectedChoices(prefs.shownTestItems(), actionListTestAdmin, actionArrayTestAdapter);
        ((ArrayAdapter) actionListTestPatient.getAdapter()).clear();
        ((ArrayAdapter) actionListTestPatient.getAdapter()).addAll(patientPlanOfCareTestList);
    }

    private void setPatientListMedicationAdapter() {
        patientPlanOfCareMedicationList = filterSelectedChoices(prefs.shownMedicationItems(), actionListMedicationAdmin, actionArrayMedicationAdapter);
        ((ArrayAdapter) actionListMedicationPatient.getAdapter()).clear();
        ((ArrayAdapter) actionListMedicationPatient.getAdapter()).addAll(patientPlanOfCareMedicationList);
    }

    /**
     * Filters out unchecked Items from List when displaying to a patient.
     * @return List of Strings to be shown
     */
    private List<String> filterSelectedChoices(List<Integer> shownItems, ListView view, PlanOfCareCheckBoxAdapter adapter) {
        Log.d(TAG, "Filter Selected Choices");
        Log.d(TAG, "Length: " + prefs.shownTestItems().size());
        ArrayList<String> selectedActions = new ArrayList<>(shownItems.size());

        for (int i : shownItems) {
            //check for if there were any temp actions stored when we closed out.
            if (i >= view.getCount()) {
                continue;
            }
            Log.d(TAG, "ITEM: " + adapter.getItem(i));
            selectedActions.add(adapter.getItem(i));
        }

        return selectedActions == null ? new ArrayList<String>() : selectedActions;
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedComplaint = parent.getSelectedItem().toString();

            prefs.getCurrentState().setChiefComplaint(selectedComplaint);
            actionArrayTestAdapter.resetList(selectedComplaint);
            prefs.setShownTestItems(null);
            setCheckedAdminItems(prefs.shownTestItems(), actionListTestAdmin);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // NOOP
        }
    }

    public interface PlanOfCareInteraction {
        public void showInfoDialog(String tit, String bod);
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
