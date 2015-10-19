package com.callbell.callbell.presentation.station;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import com.callbell.callbell.util.JSONUtil;
import com.callbell.callbell.util.PrefManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StationFragment extends Fragment {
    private static final String TAG = StationFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

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

        messageRouting.getDeviceStates();

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
    }

    public void setListFromJSONString(String stateListString) {
        Log.d(TAG, "STATELIST: " + stateListString);

        List<State> sl;
        if (!stateListString.isEmpty()) {
            sl = JSONUtil.getStateListFromJSONArray(JSONUtil.getJSONFromString(stateListString));
        } else {
            sl = new ArrayList<>();
        }

        adapter = new StationItemAdapter(getContext(), sl);
        stationStateList.setAdapter(adapter);
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

    }

}
