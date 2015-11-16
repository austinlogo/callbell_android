package com.callbell.callbell.presentation.bed;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @InjectView(R.id.staff_physician_title)
    TextView physicianTitle;

    @InjectView(R.id.staff_physician_box)
    LinearLayout physicianBox;

    @InjectView(R.id.staff_nurse_box)
    LinearLayout nurseBox;

    @InjectView(R.id.staff_resident_box)
    LinearLayout residentBox;

    private Drawable defaultTextBackgroundPhysician;
    private Drawable defaultTextBackgroundNurse;
    private Drawable defaultTextBackgroundResident;

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

        defaultTextBackgroundPhysician = physicianField.getBackground();
        defaultTextBackgroundNurse = nurseField.getBackground();
        defaultTextBackgroundResident = residentField.getBackground();

        setStaffValues(false);
        setSuperUserPermissions(prefs.isSuperUser());
        setListeners();

        return view;
    }

    private void setListeners() {
        physicianTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefs.isSuperUser()) {
                    return;
                }

                TextView view = (TextView) v;
                if (view.getText().equals(getText(R.string.staff_physician))) {
                    view.setText(R.string.staff_nurse_practitioner);
                } else {
                    view.setText(R.string.staff_physician);
                }
            }
        });
    }

    private void setStaffValues(boolean isSuperUser) {

        Log.d(TAG, "SEtting staff values");
        Log.d(TAG, prefs.physician());
        Log.d(TAG, prefs.resident());
        Log.d(TAG,prefs.nurse());

        physicianField.setText(prefs.physician());
        nurseField.setText(prefs.nurse());
        residentField.setText(prefs.resident());

        setStaffVisibility(isSuperUser);
    }


    public void setSuperUserPermissions(boolean setSuperUserStatus) {

//        before disabling set staff information)
        if (!setSuperUserStatus) {
            Log.d(TAG, "Disabling SU mode");
            prefs.setStaff(
                    physicianField.getText().toString(),
                    residentField.getText().toString(),
                    nurseField.getText().toString());
        }

        //setFocusUnderline
        physicianField.setBackground(setSuperUserStatus ? defaultTextBackgroundPhysician : null);
        nurseField.setBackground(setSuperUserStatus ? defaultTextBackgroundNurse : null);
        residentField.setBackground(setSuperUserStatus ? defaultTextBackgroundResident : null);

        setStaffVisibility(setSuperUserStatus);

        //Enable or disable Text based on mode
        physicianField.setEnabled(setSuperUserStatus);
        nurseField.setEnabled(setSuperUserStatus);
        residentField.setEnabled(setSuperUserStatus);

        //TestsTitle



        prefs.setSuperUser(setSuperUserStatus);
    }

    public void setStaffVisibility(boolean isSuperUser) {
        physicianBox.setVisibility((isSuperUser || physicianField.getText().length() > 0) ? View.VISIBLE : View.GONE);
        residentBox.setVisibility((isSuperUser || residentField.getText().length() > 0) ? View.VISIBLE : View.GONE);
        nurseBox.setVisibility((isSuperUser || nurseField.getText().length() > 0) ? View.VISIBLE : View.GONE);
    }

    public void clearValues() {
        physicianField.setText("");
        residentField.setText("");
        nurseField.setText("");
        prefs.setStaff("", "", "");
    }
}
