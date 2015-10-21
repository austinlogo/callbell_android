package com.callbell.callbell.presentation.bed;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.callbell.callbell.R;
import com.callbell.callbell.models.request.CallBellRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CallBellsFragment extends Fragment {
    private OnFragmentInteractionListener mActivityListener;

    @InjectView(R.id.call_button_restroom)
    RelativeLayout mButtonRestroom;

    @InjectView(R.id.call_button_food_water)
    RelativeLayout mButtonFoodWater;

    @InjectView(R.id.call_button_blanket)
    RelativeLayout mButtonBlanket;

    @InjectView(R.id.call_button_pain)
    RelativeLayout mButtonPain;

    @InjectView(R.id.call_button_help)
    RelativeLayout mButtonHelp;

    public static CallBellsFragment newInstance() {

        CallBellsFragment fragment = new CallBellsFragment();

        return fragment;
    }

    public CallBellsFragment() {
        // NOOP: Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call_bells, container, false);
        ButterKnife.inject(this, view);

        mButtonRestroom.setOnClickListener(new CallBellListener());
        mButtonFoodWater.setOnClickListener(new CallBellListener());
        mButtonBlanket.setOnClickListener(new CallBellListener());
        mButtonPain.setOnClickListener(new CallBellListener());
        mButtonHelp.setOnClickListener(new CallBellListener());

        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mActivityListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onCallBellPressed(int msg);
    }

    public class CallBellListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int clickedText;
            RelativeLayout clicked = (RelativeLayout) v;
            switch (clicked.getId()) {
                case R.id.call_button_pain:
                    clickedText = CallBellRequest.PAIN_ID;
                    break;
                case R.id.call_button_blanket:
                    clickedText = CallBellRequest.BLANKET_ID;
                    break;
                case R.id.call_button_food_water:
                    clickedText = CallBellRequest.FOOD_ID;
                    break;
                case R.id.call_button_restroom:
                    clickedText = CallBellRequest.RESTROOM_ID;
                    break;
                case R.id.call_button_help:
                    clickedText = CallBellRequest.HELP_ID;
                    break;
                default:
                    clickedText = CallBellRequest.PAIN_ID;
            }
            mActivityListener.onCallBellPressed(clickedText);

        }
    }
}


