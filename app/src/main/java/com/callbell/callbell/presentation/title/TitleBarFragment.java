package com.callbell.callbell.presentation.title;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.dialogs.SetPainRatingDialog;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/27/15.
 */
public class TitleBarFragment extends Fragment {

    public static final int BED_MODE_ACTIVITY = 0x0;
    public static final int STATION_MODE_ACTIVITY = 0x1;
    public static final int LOGIN_MODE_ACTIVITY = 0x2;

    private static final String ACTIVITY = "ACTIVITY";
    private int mActivityValue = -1;

    private TitleBarListener mListener;

    @Inject
    PrefManager prefs;

    @InjectView(R.id.fragment_title_mode_button)
    ImageButton mAdminButton;

    @InjectView(R.id.fragment_title_bar_bed_number)
    TextView mBedNumber;

    @InjectView(R.id.fragment_title_bar_clear)
    Button mClearButton;

    @InjectView(R.id.fragment_title_bar_simple)
    Button mSimpleToggle;

    @InjectView(R.id.fragment_title_bar_pain)
    Button mPainButton;

    @InjectView(R.id.fragment_title_disconnected_layout)
    LinearLayout mDisconnectedLayout;

    @InjectView(R.id.fragment_title_default_layout)
    RelativeLayout mDefaultLayout;

    private static final String TAG = TitleBarFragment.class.getSimpleName();


    public static TitleBarFragment newInstance(int fragmentValue) {
        Bundle bundle = new Bundle();
        bundle.putInt(ACTIVITY, fragmentValue);
        TitleBarFragment fragment = new TitleBarFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_title_bar, container, false);

        Bundle bundle = getArguments();

        ((CallBellApplication) getActivity().getApplication()).inject(this);
        ButterKnife.inject(this, view);

        //Set Values
        mBedNumber.setText(String.format(
                "%s %s #%s",
                prefs.getCurrentState().getGroup(),
                getText(R.string.bed),
                prefs.getCurrentState().getLocation()));

        mActivityValue = bundle.getInt(ACTIVITY);

        setUI();
        initListeners();

        setSuperUserPermissions(prefs.isSuperUser());
        
        return view;
    }

    private void setUI() {

        switch (mActivityValue) {
            case BED_MODE_ACTIVITY:
                break;
            case STATION_MODE_ACTIVITY:
                mClearButton.setVisibility(View.GONE);
                mPainButton.setVisibility(View.GONE);
                break;
            case LOGIN_MODE_ACTIVITY:
                mClearButton.setVisibility(View.VISIBLE);
                mPainButton.setVisibility(View.GONE);
        }

    }

    public void initListeners() {
        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminSettings();
            }
        });

        mPainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.isSuperUser()) {
                    SetPainRatingDialog dialog = new SetPainRatingDialog();
                    dialog.show(getFragmentManager(), "SET PAIN RATING");
                } else {
                    PainRatingDialog dialog = new PainRatingDialog();
                    dialog.show(getFragmentManager(), "PAIN RATING");
                }
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearValues();
            }
        });

        mSimpleToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.toggleSimpleMode();
            }
        });

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        try {
            mListener = ((TitleBarListener) getActivity());
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TitleBarListener");
        }
    }

    public void setSuperUserPermissions(boolean isSuperUser) {

        int showForSuperUserOnly = isSuperUser ? View.VISIBLE : View.GONE;

        mDefaultLayout.setBackgroundColor(isSuperUser
                ? getActivity().getApplicationContext().getResources().getColor(R.color.navy)
                : getActivity().getApplicationContext().getResources().getColor(R.color.colorPrimary));
        mAdminButton.setImageResource(isSuperUser ? R.drawable.save : R.drawable.pencil);
        mClearButton.setVisibility(showForSuperUserOnly);
        mPainButton.setVisibility(showForSuperUserOnly);
        mSimpleToggle.setVisibility(showForSuperUserOnly);

        setUI();
    }

    public void adminSettings() {
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUser(false);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(i);
            mAdminButton.setImageResource(R.drawable.save);
            prefs.setState(prefs.getCurrentState());
        }
    }

    public void toggleServerconnectedView(boolean isServerConnected) {
        // TODO: We need proper handling of this
//        mDisconnectedLayout.setVisibility(!isServerConnected ? View.VISIBLE : View.GONE);
//        mDefaultLayout.setVisibility(!isServerConnected ? View.GONE : View.VISIBLE);
    }


    public interface TitleBarListener {
        void clearValues();
        void toggleSimpleMode();
    }
}
