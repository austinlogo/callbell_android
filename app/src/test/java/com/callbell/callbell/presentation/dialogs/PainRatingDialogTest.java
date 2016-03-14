package com.callbell.callbell.presentation.dialogs;


import android.app.Activity;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import staticTestData.TestData;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PainRatingDialogTest {

    Activity activity;

    MessageReason pain = MessageReason.PAIN;
    MessageReason help = MessageReason.HELP;
    MessageReason food = MessageReason.FOOD;
    MessageReason blanket = MessageReason.BLANKET;
    MessageReason restroom = MessageReason.RESTROOM;

    State mState = TestData.state_bed();

    @Mock (name = "prefs")
    PrefManager prefs;

    @Mock (name = "messageRouting")
    MessageRouting messageRouting;

    @InjectMocks
    PainRatingDialog dialog;

    String station = "TEST_Station";

    @Before
    public void setUp() {

        dialog = new PainRatingDialog();

        activity = Robolectric.setupActivity(Activity.class);
        dialog.show(activity.getFragmentManager(), "TAG");

        Assert.assertNotNull(dialog);
        MockitoAnnotations.initMocks(this);

        Mockito.when(prefs.getStationName()).thenReturn(station);
    }

    @Test
    public void testSettingInitialChoice() {

        for (int i = 0; i <= 10; i++) {
            Mockito.when(prefs.painRating()).thenReturn(i);
            dialog.dismiss();
            dialog.show(activity.getFragmentManager(), "TAG");
            MockitoAnnotations.initMocks(this);
            dialog.dismiss();
        }
    }

    @Test
    public void testClick() {

        dialog.mPainRating0.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));

    }

    @Test
    public void testClick2() {
        dialog.mPainRating1.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));

    }

    @Test
    public void testClick3() {
        dialog.mPainRating2.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick4() {
        dialog.mPainRating3.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick5() {
        dialog.mPainRating4.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick6() {
        dialog.mPainRating5.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick7() {
        dialog.mPainRating6.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick8() {
        dialog.mPainRating7.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick9() {
        dialog.mPainRating8.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick10() {
        dialog.mPainRating9.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }

    @Test
    public void testClick11() {
        dialog.mPainRating10.performClick();
        Mockito.verify(messageRouting).updateState(Matchers.eq(PrefManager.getCurrentState()), Matchers.eq(station));
    }
}
