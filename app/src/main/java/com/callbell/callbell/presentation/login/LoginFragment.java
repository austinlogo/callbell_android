package com.callbell.callbell.presentation.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.presentation.bed.BedModeActivity;
import com.callbell.callbell.presentation.station.StationActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginFragmentCallback mListener;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private State lastState;

    @InjectView(R.id.login_id)
    EditText location_id;

    @InjectView(R.id.group_id)
    EditText group_id;
    
    @InjectView(R.id.hospital_id)
    EditText hospital_id;

    @InjectView(R.id.launch_bed_experience)
    Button bedButton;

    @InjectView(R.id.launch_station_experience)
    Button stationButton;

    @Inject
    PrefManager prefs;

    //for Debug purposes
    private boolean forceRegister = true;

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((CallBellApplication) getActivity().getApplication()).inject(this);
        ButterKnife.inject(this, view);

        intiStateAndUI();
        setButtonsEnableStatus();
        setSuperUserPermission(prefs.isSuperUser());

        bedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Bed");

                register(prefs.BED_MODE);
                Intent newActivity = new Intent(getActivity().getApplicationContext(), BedModeActivity.class);
                startActivity(newActivity);
            }
        });

        stationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(prefs.STATION_MODE);
                Intent newActivity = new Intent(getActivity().getApplicationContext(), StationActivity.class);
                startActivity(newActivity);
            }
        });

        location_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // NOOP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // NOOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonsEnableStatus();
            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setSuperUserPermission(prefs.isSuperUser());
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));

        location_id.requestFocus();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LoginFragmentCallback) activity;
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


    public void register(String mod) {
        if (PrefManager.BED_MODE.equals(mod)) {
            State thisState = new State(
                    hospital_id.getText().toString(),
                    group_id.getText().toString(),
                    location_id.getText().toString(),
                    mod,
                    prefs.physician(),
                    prefs.nurse(),
                    prefs.resident(),
                    prefs.chiefComplaint(),
                    prefs.shownTestItems(),
                    prefs.shownMedicationItems(),
                    prefs.allTestItems(),
                    prefs.getAllMedicationItems(),
                    prefs.painRating());
            prefs.setState(thisState);

        } else {
            prefs.setState(
                new State(
                    hospital_id.getText().toString(),
                    group_id.getText().toString(),
                    PrefManager.STATION_SUFFIX,
                    mod,
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<Integer>(),
                    new ArrayList<Integer>(),
                    new ArrayList<String>(),
                    new ArrayList<String>(),
                    0
                )
            );
        }

        mListener.register();
    }

    private void intiStateAndUI() {
        lastState = new State(prefs);
        hospital_id.setText(lastState.getHospital());
        group_id.setText(lastState.getGroup());
        location_id.setText(lastState.getLocation().endsWith(PrefManager.STATION_SUFFIX) ? "" : lastState.getLocation());
    }

    private void setSuperUserPermission(boolean isSuperUser) {
        hospital_id.setEnabled(isSuperUser);
        group_id.setEnabled(isSuperUser);
        stationButton.setEnabled(isSuperUser);
    }
    
    private void setButtonsEnableStatus() {
        boolean enableStation = prefs.isSuperUser()
                &&group_id.getText().length() > 0
                && hospital_id.getText().length() > 0;

        boolean enableBed = location_id.getText().length() > 0
                && hospital_id.getText().length() > 0
                && group_id.getText().length() > 0
                && !location_id.getText().equals(PrefManager.STATION_SUFFIX);

        bedButton.setEnabled(enableBed);
        stationButton.setEnabled(enableStation);
    }

    public void clearValues() {
        hospital_id.setText( (hospital_id.isEnabled()) ? "" : hospital_id.getText());
        group_id.setText( (group_id.isEnabled()) ? "" : group_id.getText());
        location_id.setText( (location_id.isEnabled()) ? "" : location_id.getText());
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
    public interface LoginFragmentCallback {
        void register();

    }
}
