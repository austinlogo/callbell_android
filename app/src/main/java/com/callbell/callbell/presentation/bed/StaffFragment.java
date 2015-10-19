package com.callbell.callbell.presentation.bed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/10/15.
 */
public class StaffFragment extends Fragment {


    private static final String TAG = StaffFragment.class.getSimpleName();
    @Inject
    PrefManager prefs;

    @InjectView(R.id.staff_fragment_layout)
    LinearLayout layout;

    @InjectView(R.id.staff_physician_field)
    EditText physicianField;

    @InjectView(R.id.staff_resident_field)
    EditText residentField;

    @InjectView(R.id.staff_nurse_field)
    EditText nurseField;

    @InjectView(R.id.staff_physician_box)
    LinearLayout physicianBox;

    @InjectView(R.id.staff_nurse_box)
    LinearLayout nurseBox;

    @InjectView(R.id.staff_resident_box)
    LinearLayout residentBox;



    public static StaffFragment newInstance() {
        StaffFragment fragment = new StaffFragment();
//        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        ((CallBellApplication) getActivity().getApplication()).inject(this);
        ButterKnife.inject(this, view);

        setStaffValues(false);
        enableSuperUserAccess(prefs.isSuperUser());

        return view;
    }

    private void setStaffValues(boolean isSuperUser) {

        Log.d(TAG, "SEtting staff values");
        Log.d(TAG,prefs.physician());
        Log.d(TAG,prefs.resident());
        Log.d(TAG,prefs.nurse());

        physicianField.setText(prefs.physician());
        nurseField.setText(prefs.nurse());
        residentField.setText(prefs.resident());

        setStaffVisibility(isSuperUser);
    }


    public void enableSuperUserAccess(boolean setSuperUserStatus) {

//        before disabling set staff information)
        if (!setSuperUserStatus) {
            Log.d(TAG, "Disabling SU mode");
            prefs.setStaff(
                    physicianField.getText().toString(),
                    residentField.getText().toString(),
                    nurseField.getText().toString());

            //Remove focusUnderline
            physicianField.setBackgroundColor(0);
            nurseField.setBackgroundColor(0);
            residentField.setBackgroundColor(0);
        }

        setStaffVisibility(setSuperUserStatus);

        //Enable or disable Text based on mode
        physicianField.setEnabled(setSuperUserStatus);
        nurseField.setEnabled(setSuperUserStatus);
        residentField.setEnabled(setSuperUserStatus);


        prefs.setSuperUserStatus(setSuperUserStatus);
    }

    public void setStaffVisibility(boolean isSuperUser) {
        physicianBox.setVisibility((isSuperUser || physicianField.getText().length() > 0) ? View.VISIBLE : View.GONE);
        residentBox.setVisibility((isSuperUser || residentField.getText().length() > 0) ? View.VISIBLE : View.GONE);
        nurseBox.setVisibility((isSuperUser || nurseField.getText().length() > 0) ? View.VISIBLE : View.GONE);
    }
}
