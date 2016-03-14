package com.callbell.callbell.presentation.dialogs;


import android.app.Activity;
import android.app.FragmentManager;
import android.widget.Button;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import staticTestData.TestData;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CallBellDialogTest {

    Activity activity;

    MessageReason pain = MessageReason.PAIN;
    MessageReason help = MessageReason.HELP;
    MessageReason food = MessageReason.FOOD;
    MessageReason blanket = MessageReason.BLANKET;
    MessageReason restroom = MessageReason.RESTROOM;

    State mState = TestData.state_bed();

    @Mock
    MessageRouting mMessageRouting;

    @InjectMocks
    CallBellDialog dialog;

    @Before
    public void setUp() {

        dialog = new CallBellDialog();
        dialog.setArguments(CallBellDialog.newBundle(mState, pain));

        activity = Robolectric.setupActivity(Activity.class);
        dialog.show(activity.getFragmentManager(), "TAG");

        Assert.assertNotNull(dialog);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testClick() {
        dialog.acknowledgeButton.performClick();
        Mockito.verify(mMessageRouting).sendMessage(TestData.state_bed().getLocation(), PrefManager.CATEGORY_CALL_BELL_RESPONSE,MessageReason.ACKNOWLEDGED);
    }

    @Test
    public void newInstance() {
        CallBellDialog newDialog = CallBellDialog.newInstance(mState, help);
        newDialog.show(activity.getFragmentManager(), "TAG");
        Assert.assertNotNull(newDialog);
        newDialog = CallBellDialog.newInstance(mState, restroom);
        newDialog.show(activity.getFragmentManager(), "TAG");
        Assert.assertNotNull(newDialog);
        newDialog = CallBellDialog.newInstance(mState, blanket);
        newDialog.show(activity.getFragmentManager(), "TAG");
        Assert.assertNotNull(newDialog);
        newDialog = CallBellDialog.newInstance(mState, food);
        newDialog.show(activity.getFragmentManager(), "TAG");
        Assert.assertNotNull(newDialog);
    }
}
