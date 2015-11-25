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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.adapter.PlanOfCareCheckBoxAdapter;
import com.callbell.callbell.presentation.bed.view.DisplayItemList;
import com.callbell.callbell.presentation.toast.BeaToast;
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

    ArrayAdapter<String> autoCompleteOptions;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_simple_spinner, spinnerArray);
        chiefComplaintSpinner.setAdapter(adapter);
        Log.d(TAG, "Current Selection: " + prefs.getCurrentState().getChiefComplaint());
        chiefComplaintSpinner.setSelection(adapter.getPosition(prefs.getCurrentState().getChiefComplaint()), false);

        //Inflate the Test List
        mPlanOfCareTests.setTitle(R.string.poc_current_tests_title);
        List<String> prefsAdminList = prefs.allTestItems();
        List<String> initialAdminValues = (!prefsAdminList.isEmpty() )
                ? prefsAdminList
                : new ArrayList<>(POCValues.pocMap.get(chiefComplaintSpinner.getSelectedItem().toString()));
        mPlanOfCareTests.setAdminAdapter(new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminValues));
        mPlanOfCareTests.setCheckedItems(prefs.shownTestItems());

        //Inflate the Medication List
        List<String> initialAdminMedicationValues =  new ArrayList<>(MedicationValues.medicationMap.keySet());
        mPlanOfCareMedications.setTitle(R.string.poc_current_medications_title);
        mPlanOfCareMedications.setAdminAdapter(new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminMedicationValues));
        mPlanOfCareMedications.setCheckedItems(prefs.shownMedicationItems());

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

    public void setSuperUserPermissions(boolean isSuperUser) {

        prefs.setShownTestItems(mPlanOfCareTests.updatePatientList());
        prefs.setAllActionTestItems(mPlanOfCareTests.getActionList());

        prefs.setShownMedicationItems(mPlanOfCareMedications.updatePatientList());
        prefs.setAllActionMedicationItems(mPlanOfCareMedications.getActionList());

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
        prefs.setShownTestItems(new ArrayList<Integer>());
        prefs.setShownMedicationItems(new ArrayList<Integer>());
        mPlanOfCareTests.clear();
        mPlanOfCareMedications.clear();
//        mPlanOfCareTests.setCheckedItems(prefs.shownTestItems());
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedComplaint = parent.getSelectedItem().toString();

            prefs.getCurrentState().setChiefComplaint(selectedComplaint);
            mPlanOfCareTests.resetList(selectedComplaint);
            prefs.setShownTestItems(null);
            mPlanOfCareTests.setCheckedItems(prefs.shownTestItems());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // NOOP
        }
    }

    public interface PlanOfCareInteraction {
        void showInfoDialog(String tit, String expandedName, String bod);
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
