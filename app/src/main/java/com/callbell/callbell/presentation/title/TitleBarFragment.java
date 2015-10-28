package com.callbell.callbell.presentation.title;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.presentation.BaseActivity;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.util.PrefManager;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/27/15.
 */
public class TitleBarFragment extends Fragment {

    @Inject
    PrefManager prefs;

    @InjectView(R.id.fragment_title_mode_button)
    Button mAdminButton;

    @InjectView(R.id.fragment_title_bar_date)
    TextView mDateTextView;

    private static final String TAG = TitleBarFragment.class.getSimpleName();


    public static TitleBarFragment newInstance() {
        return new TitleBarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_title_bar, container, false);

        ((CallBellApplication) getActivity().getApplication()).inject(this);
        ButterKnife.inject(this, view);

        //Set the Date
        mDateTextView.setText(DateFormat.getDateInstance().format(new Date()));

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminSettings();
            }
        });
        
        return view;
    }

    public void setSuperUserSettings(boolean isSuperUser) {

        if (isSuperUser) {
            mAdminButton.setText(R.string.user_mode);
        }
    }

    public void adminSettings() {
        if (!prefs.isSuperUser()) {
            EnableSuperUserDialog d = EnableSuperUserDialog.newInstance(null);
            d.show(getFragmentManager(), "SUDO");

        } else {
            prefs.setSuperUserStatus(false);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(i);
            mAdminButton.setText(R.string.admin_mode);
            prefs.setState(prefs.getCurrentState());
        }
    }
}
