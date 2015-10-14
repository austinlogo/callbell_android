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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.util.PrefManager;

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
public class PlanOfCareFragment extends Fragment implements AbsListView.OnItemClickListener {
    private static final String TAG = PlanOfCareFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    @InjectView (R.id.main_complaint_spinner)
    Spinner chiefComplaint;

    @InjectView(R.id.action_list_admin)
    ListView actionListAdmin;

    @InjectView(R.id.action_list_patient)
    ListView actionListPatient;

    @Inject
    PrefManager prefs;

    @Inject
    POCValues pocValues;

    private String[] allComplaintActions;
    private ListView checkedItems;
    private boolean initComplete = false;

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

        //Inflate the spinner
        String[] spinnerArray = pocValues.pocMap.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        chiefComplaint.setAdapter(adapter);
        chiefComplaint.setSelection(adapter.getPosition(prefs.getCurrentState().getChiefComplaint()));
        chiefComplaint.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        //Inflate the Admin Checkbox
        allComplaintActions = POCValues.pocMap.get(chiefComplaint.getSelectedItem().toString());
        setAdminListAdapter();
        actionListAdmin.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setCheckedItems();

        //Inflate the patient Checkbox
        setPatientListAdapter();

        setSuperUserPermissions(prefs.isSuperUser());

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setSuperUserPermissions(prefs.isSuperUser());
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));

        return view;
    }





    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }


    private void setSuperUserPermissions(boolean isSuperUser) {
        SparseBooleanArray checked = actionListAdmin.getCheckedItemPositions();

        actionListAdmin.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        actionListPatient.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);

        if (!isSuperUser && checked != null) {

            Log.d(TAG, "booleanArray: " + checked.toString());
            Log.d(TAG, "checked size is " + actionListAdmin.getCheckedItemCount());
            int[] checkedArray = new int[getCheckedItemCount(actionListAdmin)];

            int checkedArrayIndex = 0;
            for (int index = 0; index < actionListAdmin.getAdapter().getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checked.get(index));
                if (checked.get(index)) {
                    checkedArray[checkedArrayIndex++] = index;
                }
            }

            prefs.setShownActions(checkedArray);
            setPatientListAdapter();
        }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void setCheckedItems() {
        int oldMode = ListView.CHOICE_MODE_MULTIPLE;
        if(actionListAdmin.getChoiceMode() != ListView.CHOICE_MODE_MULTIPLE) {
            oldMode = actionListAdmin.getChoiceMode();
            actionListAdmin.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }

        int[] checkedItems = prefs.shownActions();
        Log.d(TAG, "mode: " + actionListAdmin.getChoiceMode());
        Log.d(TAG, "Checked items length: "+ checkedItems.length);

        for (int index : checkedItems) {
            Log.d(TAG, "CheckedItem: " + index);
            actionListAdmin.setItemChecked(index, true);
        }

        Log.d(TAG, "cool: " + actionListAdmin.getCheckedItemPositions().toString());
        actionListAdmin.setChoiceMode(oldMode);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private void setAdminListAdapter() {
        ArrayAdapter<String> actionArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_multi_check, allComplaintActions);
        actionListAdmin.setAdapter(actionArrayAdapter);
    }

    private void setPatientListAdapter() {
        String[] patientPlanOfCareList = filterSelectedChoices();
        ArrayAdapter<String> patientPlanOfCareListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, patientPlanOfCareList);
        actionListPatient.setAdapter(patientPlanOfCareListAdapter);
        actionListPatient.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        actionListPatient.setItemsCanFocus(true);
    }

    private String[] filterSelectedChoices() {
        Log.d(TAG, "Filter Selected Choices");
        int[] shownActions = prefs.shownActions();
        String[] selectedActions = new String[shownActions.length];

        int index = 0;
        for (int i : shownActions) {
            Log.d(TAG, "ITEM: "+ allComplaintActions[i]);
            selectedActions[index++] = allComplaintActions[i];
        }

        return selectedActions;
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String selectedComplaint = parent.getSelectedItem().toString();
            prefs.getCurrentState().setChiefComplaint(selectedComplaint);

            allComplaintActions = POCValues.pocMap.get(selectedComplaint);
            setAdminListAdapter();
            setPatientListAdapter();
            if (!initComplete) {
                initComplete = true;
                setCheckedItems();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
