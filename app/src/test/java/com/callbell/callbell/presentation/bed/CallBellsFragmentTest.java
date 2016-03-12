package com.callbell.callbell.presentation.bed;


import android.app.Activity;
import android.widget.RelativeLayout;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.util.shell.BlankCallBellsFragmentActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CallBellsFragmentTest  {

    @Mock(name = "mockListener")
    CallBellsFragment.onCallBellFragmentInteractionListener mActivityListener;

    @Mock(name="mockActivity")
    BlankCallBellsFragmentActivity activity;

    @InjectMocks
    CallBellsFragment fragment = CallBellsFragment.newInstance(false);

    RelativeLayout mBlanketButton;
    RelativeLayout mPain;
    RelativeLayout mWater;
    RelativeLayout mrestroom;
    RelativeLayout mhelp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ClassCastException.class)
    public void start_classexception() {
        FragmentTestUtil.startFragment(fragment, Activity.class);
    }

    @Test
    public void start() throws NullPointerException {
        FragmentTestUtil.startFragment(fragment, activity.getClass());
        fragment.onAttach(activity);

        mBlanketButton = (RelativeLayout) fragment.getView().findViewById(R.id.call_button_blanket);
        mPain = (RelativeLayout) fragment.getView().findViewById(R.id.call_button_pain);
        mWater = (RelativeLayout) fragment.getView().findViewById(R.id.call_button_food_water);
        mrestroom = (RelativeLayout) fragment.getView().findViewById(R.id.call_button_restroom);
        mhelp = (RelativeLayout) fragment.getView().findViewById(R.id.call_button_help);
        Assert.assertNotNull(mBlanketButton);

        mBlanketButton.performClick();
        mPain.performClick();
        mWater.performClick();
        mrestroom.performClick();
        mhelp.performClick();


        verify(activity, times(5)).onCallBellPressed(any(MessageReason.class));
    }



}
