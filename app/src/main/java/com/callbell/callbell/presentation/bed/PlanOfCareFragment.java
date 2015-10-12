package com.callbell.callbell.presentation.bed;

import android.app.Activity;
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
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.presentation.bed.adapter.ComplaintActionArrayAdapter;
import com.callbell.callbell.util.PrefManager;

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
        ArrayAdapter<String> actionArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, allComplaintActions);
        actionList.setAdapter(actionArrayAdapter);
        Log.d(TAG, "Count : " + actionList.getCount());
        actionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        actionList.setItemChecked(2, true);
        Log.d(TAG, ""+ actionList.getCheckedItemPosition());
//        setCheckedItems();
//        setSuperUserpermissions(prefs.isSuperUser());

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
            Log.d(TAG, "checked size is " + checked.size());
            int[] checkedArray = new int[checked.size()];

            int checkedArrayIndex = 0;
            for (int index = 0; index < actionList.getAdapter().getCount(); index++) {
                Log.d(TAG, "Checked value at " + index + " is " + checked.get(index));
                if (checked.valueAt(index)) {
                    checkedArray[checkedArrayIndex++] = index;
                }
            }

            prefs.setShownActions(checkedArray);
        }

        chiefComplaint.setEnabled(isSuperUser);

//        actionList.setChoiceMode(!isSuperUser ? ListView.CHOICE_MODE_NONE : ListView.CHOICE_MODE_MULTIPLE);
        actionList.setEnabled(isSuperUser);

        //save which actions are checked.


//        SparseBooleanArray checkedItems = actionList.getCheckedItemPositions();
//        for (int i = 0; i < checkedItems.size(); i++) {
//            boolean itemChecked = checkedItems.get(i);
//            actionList.getAdapter().getItem(i).
//        }
//        actionList.getAdapter().notify();
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
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
