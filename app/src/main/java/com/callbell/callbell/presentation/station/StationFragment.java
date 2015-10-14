package com.callbell.callbell.presentation.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State;
import com.callbell.callbell.models.adapter.StationItemAdapter;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StationFragment extends Fragment {
    private static final String TAG = StationFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    @Inject
    PrefManager prefs;

    @Inject
    MessageRouting messageRouting;

    @InjectView(R.id.station_state_list)
    ListView stationStateList;

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

        messageRouting.getDeviceStates();


//        List<State> sl = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            sl.add(prefs.getCurrentState());
//        }
//        StationItemAdapter adapter = new StationItemAdapter(getActivity().getApplicationContext(), sl);
//        stationStateList.setAdapter(adapter);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String stateList = intent.getStringExtra("response");
                setListFromJSONString(stateList);
            }
        }, new IntentFilter(PrefManager.EVENT_STATES_RECEIVED));

        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setListFromJSONString(String stateList) {
        Log.d(TAG, "STATELIST: " + stateList);
        List<State> sl = getArray(stateList);

        StationItemAdapter adapter = new StationItemAdapter(getActivity().getApplicationContext(), sl);
        stationStateList.setAdapter(adapter);
    }

    private List<State> getArray(String array) {
        ArrayList<State> result = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(array);
            JSONArray myArray = obj.getJSONArray("stateList");

            Log.d(TAG, "ARRAY: " + ((JSONObject) myArray.get(0)).getString("LOCATION_ID"));

            for (int index = 0; index < myArray.length(); index++) {
                result.add(new State( (JSONObject) myArray.get(index)));
                Log.d(TAG, result.get(result.size() - 1).getPhysician());
            }

            return result;

        } catch (JSONException e) {

        }

        return new ArrayList<>();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
