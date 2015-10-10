package com.callbell.callbell.presentation.bed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.presentation.CallBellApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BedModeActivity extends AppCompatActivity implements CallBellsFragment.OnFragmentInteractionListener {

    private static final String TAG = BedModeActivity.class.getSimpleName();

    @Inject
    MessageRouting messageRouting;

    @Inject
    PrefManager prefs;

    @InjectView(R.id.test_text)
    TextView mTextView;

    CallBellsFragment mCallBellsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "BedModeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_mode);
        ButterKnife.inject(this);
        ((CallBellApplication) getApplication()).inject(this);

        mCallBellsFragment = CallBellsFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.bed_mode_CallBellsFragment_container, mCallBellsFragment, "Call Bells").commit();
    }


    @Override
    public void onCallBellPressed(String msg) {
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        getApplicationContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        //Send GCM Message
        messageRouting.sendMessage(prefs.getStationTabletName(), prefs.CATEGORY_CALL_BELL, msg);

    }
}
