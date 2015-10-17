package com.callbell.callbell.presentation.bed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
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

    @InjectView (R.id.main_complaint_spinner)
    Spinner chiefComplaint;

    @InjectView(R.id.action_list_admin)
    ListView actionListAdmin;

    @InjectView(R.id.action_list_patient)
    ListView actionListPatient;

    @InjectView(R.id.other_layout)
    LinearLayout otherLayout;

    @InjectView(R.id.other_submit)
    Button submitOther;

    @InjectView(R.id.other_edittext)
    EditText otherEditText;

    @Inject
    PrefManager prefs;

    @Inject
    POCValues pocValues;

    PlanOfCareCheckBoxAdapter actionArrayAdapter;
    ArrayAdapter<String> patientPlanOfCareListAdapter;

    private List<String> patientPlanOfCareList;

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

        return view;
    }

    private void initLists() {
        //Inflate the spinner
        List<String> spinnerArray = new ArrayList<>(pocValues.pocMap.keySet());
        Collections.sort(spinnerArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        chiefComplaint.setAdapter(adapter);
        Log.d(TAG, "Current Selection: " + prefs.getCurrentState().getChiefComplaint());
        chiefComplaint.setSelection(adapter.getPosition(prefs.getCurrentState().getChiefComplaint()), false);


        //Inflate the Admin Checkbox
        List<String> prefsAdminList = prefs.allActionItems();
        List<String> initialAdminValues = (!prefsAdminList.isEmpty() )
                ? prefsAdminList
                : new ArrayList<>(POCValues.pocMap.get(chiefComplaint.getSelectedItem().toString()));
//        Collections.sort(initialAdminValues); //initialAdminValue
        actionListAdmin.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        actionArrayAdapter = new PlanOfCareCheckBoxAdapter(getActivity(), R.layout.item_multi_check, initialAdminValues);
        actionListAdmin.setAdapter(actionArrayAdapter);
        setCheckedAdminItems();

        //Inflate the patient Checkbox
        patientPlanOfCareList = filterSelectedChoices();
        Log.d(TAG, "patientPOCLIST length:" + patientPlanOfCareList.size());
        Log.d(TAG, "Activity: " + getActivity());
        patientPlanOfCareListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, patientPlanOfCareList);
        actionListPatient.setAdapter(patientPlanOfCareListAdapter);
        actionListPatient.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        actionListPatient.setItemsCanFocus(true);

        //Set enabled state
        boolean isSuperUser = prefs.isSuperUser();
        chiefComplaint.setEnabled(isSuperUser);
        actionListAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
    }

    private void initListeners() {

        chiefComplaint.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        actionListPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.showInfoDialog("EDUCATION TITLE", "This is some education text. It will get filled in later");
            }
        });

        submitOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = otherEditText.getText().toString();
                if (item.isEmpty()) {
                    Toast.makeText(getContext(), R.string.empty_action_item, Toast.LENGTH_SHORT).show();
                    return;
                } else if (actionArrayAdapter.contains(item)) {
                    Toast.makeText(getContext(), R.string.duplicate_item, Toast.LENGTH_SHORT).show();
                    return;
                }
                String action = otherEditText.getText().toString();
                actionArrayAdapter.add(action);
                actionListAdmin.setItemChecked(actionArrayAdapter.getCount() - 1, true);
                otherEditText.setText("");
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach called");
        try {
            mListener = (PlanOfCareInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach called");
        mListener = null;
    }

    public void setSuperUserPermissions(boolean isSuperUser) {
        SparseBooleanArray checked = actionListAdmin.getCheckedItemPositions();

        // Setting Checked Items
        if (!isSuperUser && checked != null) {

            Log.d(TAG, "booleanArray: " + checked.toString());
            Log.d(TAG, "checked size is " + actionListAdmin.getCheckedItemCount());
            List<Integer> checkedArray = new ArrayList<>(getCheckedItemCount(actionListAdmin));

            for (int index = 0; index < actionArrayAdapter.getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checked.get(index));
                if (checked.get(index)) {
                    checkedArray.add(index);
                }
            }

            prefs.setShownActions(checkedArray);
            prefs.setAllActionItems(actionArrayAdapter.getList());
            setPatientListAdapter();
        }

        //Setting UI Values
        actionListAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
        otherLayout.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        chiefComplaint.setEnabled(isSuperUser);
        actionListAdmin.setEnabled(isSuperUser);
    }

    private int getCheckedItemCount(ListView list) {
        SparseBooleanArray checkedItems = actionListAdmin.getCheckedItemPositions();
        int count = 0;

        for (int index = 0; index < list.getAdapter().getCount(); index++) {
            if (checkedItems.get(index)) {
                count++;
            }
        }
        return count;
    }

    private void setCheckedAdminItems() {
        for (int i = 0; i < actionListAdmin.getCount(); i++) {
            actionListAdmin.setItemChecked(i, prefs.shownActions().contains(i));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private void setPatientListAdapter() {
        patientPlanOfCareList = filterSelectedChoices();
        ((ArrayAdapter)actionListPatient.getAdapter()).clear();
        ((ArrayAdapter)actionListPatient.getAdapter()).addAll(patientPlanOfCareList);
    }

    /**
     * Filters out unchecked Items from List when displaying to a patient.
     * @return List of Strings to be shown
     */
    private List<String> filterSelectedChoices() {
        Log.d(TAG, "Filter Selected Choices");
        Log.d(TAG, "Length: " + prefs.shownActions().size());
        ArrayList<String> selectedActions = new ArrayList<>(prefs.shownActions().size());

        for (int i : prefs.shownActions()) {
            //check for if there were any temp actions stored when we closed out.
            if (i >= actionListAdmin.getCount()) {
                continue;
            }
            Log.d(TAG, "ITEM: " + actionArrayAdapter.getItem(i));
            selectedActions.add(actionArrayAdapter.getItem(i));
        }

        return selectedActions == null ? new ArrayList<String>() : selectedActions;
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedComplaint = parent.getSelectedItem().toString();

            prefs.getCurrentState().setChiefComplaint(selectedComplaint);
            actionArrayAdapter.resetList(selectedComplaint);
            prefs.setShownActions(null);
            setCheckedAdminItems();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // NOOP
        }
    }

    public interface PlanOfCareInteraction {
        public void showInfoDialog(String tit, String bod);
    }
}
