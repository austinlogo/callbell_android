package com.callbell.callbell.presentation.remoteUpdate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;
import com.callbell.callbell.presentation.bed.StaffFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 11/19/15.
 */
public class RemoteUpdateActivity
        extends BaseActivity
        implements  PlanOfCareFragment.PlanOfCareInteraction,
                    StaffFragment.StaffFragmentListener{

    public static final int REMOTE_UPDATE_ACTIVITY_FINISH_RESULT = 101;

    @Inject
    MessageRouting mMessageRouting;

    @InjectView(R.id.activity_remote_update_submit)
    Button mSubmit;

    PlanOfCareFragment mPOCFragment;
    StaffFragment mStaffFragment;
    private State mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = new State(getIntent().getStringExtra(State.STATE_ID));

        setContentView(R.layout.activity_remote_update);
        ButterKnife.inject(this);

        mPOCFragment = PlanOfCareFragment.newInstance(mState);
        mStaffFragment = StaffFragment.newInstance(mState);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_remote_update_poc_container, mPOCFragment, "PLAN OF CARE FRAGMENT")
                .add(R.id.activity_remote_update_staff_container, mStaffFragment, "STAFF FRAGMENT")
                .commit();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State staffState = mStaffFragment.getState();
                mState = mPOCFragment.getState();

                mState.setStaff(staffState.getPhysician()
                        , staffState.getResident()
                        , staffState.getNurse());

                mMessageRouting.updateState(mState, mState.getLocation());

                Intent i = new Intent();
                i.putExtra(State.STATE_ID, mState.toJSON().toString());
                setResult(REMOTE_UPDATE_ACTIVITY_FINISH_RESULT, i);
                finish();
            }
        });

    }

    @Override
    public void showInfoDialog(String tit, String expandedName, String bod) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void savePOCState(State st) {
        Log.d("AFL", "SAVING");
    }

    @Override
    public void saveStaffState(State st) {
        // NOOP
    }
}
