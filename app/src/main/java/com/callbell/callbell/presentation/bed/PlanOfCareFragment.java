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

    @InjectView(R.id.action_list)
    ListView actionList;

    @Inject
    PrefManager prefs;

    @Inject
    POCValues pocValues;

    private String[] allComplaintActions;
    private String[] shownComplaintActions;
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

        String[] spinnerArray = pocValues.pocMap.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        chiefComplaint.setAdapter(adapter);
        chiefComplaint.setSelection(adapter.getPosition(prefs.getCurrentState().getChiefComplaint()));
        chiefComplaint.setOnItemSelectedListener(new ChiefComplaintItemSelectedListener());

        String[] allComplaintActions = POCValues.pocMap.get(chiefComplaint.getSelectedItem().toString());
        ArrayAdapter<String> actionArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_multi_check, allComplaintActions);
        actionList.setAdapter(actionArrayAdapter);
        actionList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setCheckedItems();
        setSuperUserpermissions(prefs.isSuperUser());

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setSuperUserpermissions(prefs.isSuperUser());
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


    private void setSuperUserpermissions(boolean isSuperUser) {
        SparseBooleanArray checked = actionList.getCheckedItemPositions();

        if (!isSuperUser && checked != null) {

            Log.d(TAG, "booleanArray: " + checked.toString());
            Log.d(TAG, "checked size is " + actionList.getCheckedItemCount());
            int[] checkedArray = new int[getCheckedItemCount(actionList)];

            int checkedArrayIndex = 0;
            for (int index = 0; index < actionList.getAdapter().getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checked.get(index));
                if (checked.get(index)) {
                    checkedArray[checkedArrayIndex++] = index;
                }
            }

            prefs.setShownActions(checkedArray);
        }

        chiefComplaint.setEnabled(isSuperUser);
        actionList.setEnabled(isSuperUser);
    }

    private int getCheckedItemCount(ListView list) {
        SparseBooleanArray checkedItems = actionList.getCheckedItemPositions();
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
        if(actionList.getChoiceMode() != ListView.CHOICE_MODE_MULTIPLE) {
            oldMode = actionList.getChoiceMode();
            actionList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }

        int[] checkedItems = prefs.shownActions();
        Log.d(TAG, "mode: " + actionList.getChoiceMode());
        Log.d(TAG, "Checked items length: "+ checkedItems.length);

        for (int index : checkedItems) {
            Log.d(TAG, "CheckedItem: " + index);
            actionList.setItemChecked(index, true);
        }

        Log.d(TAG, "cool: " + actionList.getCheckedItemPositions().toString());
        actionList.setChoiceMode(oldMode);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    //Spinner Listener
    public class ChiefComplaintItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String selectedComplaint = parent.getSelectedItem().toString();
            prefs.getCurrentState().setChiefComplaint(selectedComplaint);

            String[] actionArray = POCValues.pocMap.get(selectedComplaint);
            ArrayAdapter<String> actionArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, actionArray);
            actionList.setAdapter(actionArrayAdapter);
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