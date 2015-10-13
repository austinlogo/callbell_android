package com.callbell.callbell.presentation.station;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State;
import com.callbell.callbell.models.adapter.StationItemAdapter;
import com.callbell.callbell.util.PrefManager;

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


        List<State> sl = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sl.add(prefs.getCurrentState());
        }
        StationItemAdapter adapter = new StationItemAdapter(getActivity().getApplicationContext(), sl);
        stationStateList.setAdapter(adapter);

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
