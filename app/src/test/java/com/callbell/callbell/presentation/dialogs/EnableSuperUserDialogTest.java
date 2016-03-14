package com.callbell.callbell.presentation.dialogs;

import android.app.Activity;
import android.view.inputmethod.EditorInfo;

import com.callbell.callbell.BuildConfig;
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
import org.robolectric.util.ActivityController;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EnableSuperUserDialogTest {

    Activity activity = new Activity();

    @Mock(name = "prefs")
    PrefManager prefs;

    @InjectMocks
    EnableSuperUserDialog dialog;

    @Before
    public void setUp() {

        dialog = new EnableSuperUserDialog();

        ActivityController controller = Robolectric.buildActivity(Activity.class);
        activity = (Activity)controller.setup().get();
        dialog.show(activity.getFragmentManager(), "TAG");

        Assert.assertNotNull(dialog);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void correctPasswordTest() {
        dialog.passwordEditText.setText(PrefManager.DEFAULT_SU_PASSWORD);
        dialog.submitButton.performClick();
        Mockito.verify(prefs).setSuperUser(Matchers.eq(true));
    }

    @Test
    public void incorrectPasswordTest() {
        dialog.passwordEditText.setText("");
        dialog.submitButton.performClick();
        Mockito.verify(prefs, Mockito.times(0)).setSuperUser(Matchers.anyBoolean());
    }

    @Test
    public void HitEnterTest() {
        dialog.passwordEditText.setText(PrefManager.DEFAULT_SU_PASSWORD);
        dialog.passwordEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        Mockito.verify(prefs).setSuperUser(Matchers.eq(true));
    }
}