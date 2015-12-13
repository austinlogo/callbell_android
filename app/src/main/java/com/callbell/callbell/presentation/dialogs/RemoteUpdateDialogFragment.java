package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.bed.view.ToggleListView;
import com.callbell.callbell.util.JSONUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 11/16/15.
 */
public class RemoteUpdateDialogFragment extends DialogFragment {

    public static String JSON_STATE_KEY = "JSON_STATE_KEY";

    private State mState;

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

    @InjectView (R.id.fragment_poc_main_complaint_spinner)
    Spinner chiefComplaintSpinner;

    @InjectView(R.id.fragment_poc_tests)
    ToggleListView mPlanOfCareTests;

    @InjectView(R.id.fragment_poc_medications)
    ToggleListView mPlanOfCareMedications;

    @InjectView(R.id.other_layout)
    LinearLayout otherLayout;

    public static RemoteUpdateDialogFragment newInstance(State st) {
        RemoteUpdateDialogFragment dialogFragment = new RemoteUpdateDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(JSON_STATE_KEY, st.toJSON().toString());
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_remote_update);



        ButterKnife.inject(this, dialog);
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        physicianField.setText(mState.getPhysician());
        nurseField.setText(mState.getNurse());
        residentField.setText(mState.getResident());

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // arg --> String --> JSONObject --> State
        mState = new State(JSONUtil.getJSONFromString(getArguments().getString(JSON_STATE_KEY)));


    }
}
