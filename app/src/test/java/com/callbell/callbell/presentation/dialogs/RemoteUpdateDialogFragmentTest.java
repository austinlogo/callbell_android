package com.callbell.callbell.presentation.dialogs;

import android.content.Intent;
import android.os.Bundle;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.util.PrefManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class RemoteUpdateDialogFragmentTest {

    String title= "title";
    String exp = "expanded";
    String body = "body";

    Bundle bundle, bundle2;

    @Mock(name = "prefs")
    PrefManager prefs;

    @InjectMocks
    PlanOfCareInfoDialog dialog;

    @Before
    public void setUp() {

        bundle = PlanOfCareInfoDialog.newInstance(title, exp, body);

        Intent intent = new Intent();
        intent = intent.putExtras(bundle);

        dialog = Robolectric.buildActivity(PlanOfCareInfoDialog.class)
                .withIntent(intent)
                .create(bundle)
                .get();

        MockitoAnnotations.initMocks(this);
        Assert.assertNotNull(dialog);
    }

    @Test
    public void started_expaned() {
        Assert.assertTrue(dialog.mTitleText.getText().equals(title));
        Assert.assertTrue(dialog.mExpandedNameText.getText().toString().equals(exp));
        Assert.assertTrue(dialog.mBodyText.getText().equals(body));
    }

    @Test
    public void started() {
        Intent intent = new Intent();
        intent = intent.putExtras(PlanOfCareInfoDialog.newInstance(title, body));

        dialog = Robolectric.buildActivity(PlanOfCareInfoDialog.class)
                .withIntent(intent)
                .create(bundle)
                .get();

        Assert.assertTrue(dialog.mTitleText.getText().equals(title));
        Assert.assertTrue(dialog.mExpandedNameText.getText().toString().isEmpty());
        Assert.assertTrue(dialog.mBodyText.getText().equals(body));
    }
}