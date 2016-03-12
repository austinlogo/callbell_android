package com.callbell.callbell.presentation.bed;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State.MessageReason;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CallBellsFragment extends Fragment {
    private static String  ORIENTATION_KEY = "ORIENTATION_KEY";
    private onCallBellFragmentInteractionListener mActivityListener;

    @InjectView(R.id.call_button_layout)
    LinearLayout mCallButtonLayout;

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

    private int mOrientation = -1;

    public static CallBellsFragment newInstance(boolean isSimpleMode) {
        CallBellsFragment fragment = new CallBellsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORIENTATION_KEY, isSimpleMode);
        fragment.setArguments(bundle);

        return fragment;
    }

    public CallBellsFragment() {
        // NOOP: Required empty public constructormCallBellsFragment.toggleMode(LinearLayout.VERTICAL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mOrientation = LinearLayout.VERTICAL;
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

        if (mOrientation == -1) {
            mOrientation = getArguments().getBoolean(ORIENTATION_KEY)
                    ? LinearLayout.HORIZONTAL
                    : LinearLayout.VERTICAL;
        }

        toggleMode(mOrientation == LinearLayout.HORIZONTAL);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivityListener = (onCallBellFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onCallBellFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityListener = null;
    }

    public void toggleMode(boolean mSimpleMode) {
        int requestedOrientation = mSimpleMode ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL;
        mOrientation = requestedOrientation;
        mCallButtonLayout.setOrientation(requestedOrientation);

        if (mSimpleMode) {
            mButtonBlanket.setBackgroundColor(getResources().getColor(R.color.blanket_color));
            mButtonRestroom.setBackgroundColor(getResources().getColor(R.color.restroom_color));
            mButtonFoodWater.setBackgroundColor(getResources().getColor(R.color.food_color));
            mButtonHelp.setBackgroundColor(getResources().getColor(R.color.help_color));
            mButtonPain.setBackgroundColor(getResources().getColor(R.color.pain_color));

            mButtonBlanket.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.heavy_margin));
            mButtonRestroom.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.heavy_margin));
            mButtonFoodWater.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.heavy_margin));
            mButtonHelp.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.heavy_margin));
            mButtonPain.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.heavy_margin));
        } else {
            mButtonBlanket.setBackground(getResources().getDrawable(R.drawable.background_button_default));
            mButtonRestroom.setBackground(getResources().getDrawable(R.drawable.background_button_default));
            mButtonFoodWater.setBackground(getResources().getDrawable(R.drawable.background_button_default));
            mButtonHelp.setBackground(getResources().getDrawable(R.drawable.background_button_default));
            mButtonPain.setBackground(getResources().getDrawable(R.drawable.background_button_default));

            mButtonBlanket.setPadding(0, 0, 0, 0);
            mButtonRestroom.setPadding(0,0,0,0);
            mButtonFoodWater.setPadding(0,0,0,0);
            mButtonHelp.setPadding(0,0,0,0);
            mButtonPain.setPadding(0,0,0,0);
        }

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
    public interface onCallBellFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onCallBellPressed(MessageReason reason);
    }

    public class CallBellListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MessageReason clickedText;
            RelativeLayout clicked = (RelativeLayout) v;
            switch (clicked.getId()) {
                case R.id.call_button_pain:
                    clickedText = MessageReason.PAIN;
                    break;
                case R.id.call_button_blanket:
                    clickedText = MessageReason.BLANKET;
                    break;
                case R.id.call_button_food_water:
                    clickedText = MessageReason.FOOD;
                    break;
                case R.id.call_button_restroom:
                    clickedText = MessageReason.RESTROOM;
                    break;
                case R.id.call_button_help:
                    clickedText = MessageReason.HELP;
                    break;
                default:
                    clickedText = MessageReason.HELP;
            }
            mActivityListener.onCallBellPressed(clickedText);

        }
    }
}


