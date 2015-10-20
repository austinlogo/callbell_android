package com.callbell.callbell.presentation.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.presentation.bed.BedModeActivity;
import com.callbell.callbell.presentation.station.StationActivity;
import com.callbell.callbell.service.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragmentCallback} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginFragmentCallback mListener;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private State LastState;

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
        setSUpermissions(prefs.isSuperUser());

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
                setSUpermissions(prefs.isSuperUser());
            }
        }, new IntentFilter(PrefManager.EVENT_SU_MODE_CHANGE));

        location_id.requestFocus();
        return view;
    }

    @Override
    public void onAttach(Context activity) {
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
        State thisState = new State(hospital_id.getText().toString(),
                group_id.getText().toString(),
                location_id.getText().toString(),
                mod, prefs.physician(), prefs.nurse(), prefs.resident(), prefs.chiefComplaint());

        prefs.setState(thisState);

//        check if this has the same info we have on the server
        if (LastState.equals(thisState) && !forceRegister) {
            Log.d(TAG, "Already Registered");
        //Something has changed or it's a new tablet either way we should update the token on the server.
        } else if (checkPlayServices()) {
            prefs.getPreferences().edit().putBoolean(prefs.REG_UPLOADED_KEY, false).apply();
            Intent intent = new Intent(getActivity(), RegistrationIntentService.class);

            if (thisState.getMode().equals(prefs.STATION_MODE) ) {
                thisState.setLocation(prefs.getStationTabletName());
                prefs.setState(thisState);
            }

            Log.d(TAG, "starting Service: " + thisState.getMode());
            getActivity().startService(intent);

        }else{
            Toast.makeText(getActivity().getApplicationContext(), "This device is not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    private boolean hasStateChanged(String mod) {
        String mode = prefs.mode();
        String hospital = prefs.hospital();
        String location = prefs.location();

        //check if this has the same info we have on the server
        if (mode.length() > 0 && mode.equals(mod)
                && hospital.length() > 0 && hospital.equals(hospital_id.getText().toString())
                && location.length() > 0 && location.equals(location_id.getText().toString())) {

            return false;
        }
        return true;
    }

    private void intiStateAndUI() {
        LastState = new State(prefs);
        hospital_id.setText(LastState.getHospital());
        group_id.setText(LastState.getGroup());
        location_id.setText(LastState.getLocation().endsWith(PrefManager.STATION_SUFFIX) ? "" : LastState.getLocation());
    }

    private void setSUpermissions(boolean isSuperUser) {
        hospital_id.setEnabled(isSuperUser);
        group_id.setEnabled(isSuperUser);
        stationButton.setEnabled(isSuperUser);
    }
    
    private void setButtonsEnableStatus() {
        boolean enableStation = group_id.getText().length() > 0
                && hospital_id.getText().length() > 0;
        boolean enableBed = location_id.getText().length() > 0
                && hospital_id.getText().length() > 0
                && group_id.getText().length() > 0;

        bedButton.setEnabled(enableBed);
        stationButton.setEnabled(enableStation);
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
        // TODO: Update argument type and name

    }

}
