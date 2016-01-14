package com.callbell.callbell.presentation.station;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.adapter.StationItemAdapter;
import com.callbell.callbell.models.response.ConnectionStatusUpdateResponse;
import com.callbell.callbell.models.response.MessageResponse;
import com.callbell.callbell.presentation.dialogs.CallBellDialog;
import com.callbell.callbell.presentation.dialogs.RemoteUpdateDialogFragment;
import com.callbell.callbell.presentation.remoteUpdate.RemoteUpdateActivity;
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StationFragment
        extends Fragment {

    private static final String TAG = StationFragment.class.getSimpleName();
    private StationActivityListener mListener;

    // Inserted to fix Bug in V4 Fragment implementation
    private static final Field sChildFragmentManagerField;
    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }

    @Inject
    PrefManager prefs;

    @Inject
    MessageRouting messageRouting;

    @InjectView(R.id.station_state_list)
    ListView stationStateList;

    private StationItemAdapter adapter;

    // TODO: Rename and change types and number of parameters
    public static StationFragment newInstance() {
        StationFragment fragment = new StationFragment();
        return fragment;
    }

    public StationFragment() {
        // NOOP: Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Created Fragment");
        View view = inflater.inflate(R.layout.fragment_station, container, false);
        ButterKnife.inject(this, view);
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        initListeners();
        getDeviceStates();

        return view;
    }

    public void getDeviceStates() {
        messageRouting.getDeviceStates();
    }

    private void initListeners() {
        stationStateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "LIst item clicked: " + position);
                MessageReason reason = adapter.getReason(position);
                mListener.stopSound();

                if (reason == MessageReason.QUIET) {
                    Intent i = new Intent(getActivity(), RemoteUpdateActivity.class);
                    i.putExtra(State.STATE_ID, adapter.getItem(position).toJSON().toString());

                    mListener.startRemoteUpdateActivity(i);
                    return;
                }

                State st = adapter.getItem(position);
                CallBellDialog dialog = CallBellDialog.newInstance(st, reason);

                adapter.updateItem(position, MessageReason.QUIET);

                dialog.show(getFragmentManager(), "Call Bell Dialog");
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StationActivityListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;

        //Needed to Fix Bug in V4 Fragment Implementation
        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e(TAG, "Error setting mChildFragmentManager field", e);
            }
        }
    }

    public void updateList(State st) {
        adapter.updateItem(st);

        if (st.isInPain()) {
            adapter.updateItem(st, MessageReason.PAIN);
        }
    }


    public void setListFromJSONString(String stateListString) {
        Log.d(TAG, "STATELIST: " + stateListString);

        List<State> sl;
        if (!stateListString.isEmpty()) {
            sl = JSONUtil.getStateListFromJSONArray(JSONUtil.getJSONFromString(stateListString));
        } else {
            sl = new ArrayList<>();
        }

        adapter = new StationItemAdapter(getActivity(), sl, messageRouting);
        stationStateList.setAdapter(adapter);
    }

    public void updateListItemStatus(MessageResponse response) {
        adapter.updateItem(response.state, response.messageReason);
    }

    public void updateListItemStatus(State st, MessageReason messageReason) {
        adapter.updateItem(st, messageReason);
    }

    public void updateConnectionStatuses(List<String> connectedTablets) {

        if (adapter != null) {
            adapter.updateConnectedStatuses(connectedTablets);
        }
    }

    public void updateConnectionStatuses(ConnectionStatusUpdateResponse response) {
        if (adapter != null) {
            adapter.updateConnectedStatus(response.getTabletName(), response.getConnectionStatus());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface StationActivityListener {
        void stopSound();
        void startRemoteUpdateActivity(Intent i);
    }

}
