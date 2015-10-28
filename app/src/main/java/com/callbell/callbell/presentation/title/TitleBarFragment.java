package com.callbell.callbell.presentation.title;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.presentation.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/27/15.
 */
public class TitleBarFragment extends Fragment {

    @InjectView(R.id.fragment_title_mode_button)
    Button mAdminButton;

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

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).adminSettingsAction();
            }
        });
        
        return view;
    }
}
