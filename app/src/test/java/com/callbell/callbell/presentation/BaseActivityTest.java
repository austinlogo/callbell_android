package com.callbell.callbell.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.InstrumentationTestCase;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.R;

import junit.framework.Assert;

import org.bouncycastle.jce.provider.symmetric.ARC4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@PrepareForTest({MediaPlayer.class})
@RunWith (PowerMockRunner.class)
@Config(constants = BuildConfig.class)
public class BaseActivityTest extends ActivityUnitTestCase<BaseActivity> {

    @Mock (name = "notificationSound")
    MediaPlayer notificationSound;

    BaseActivity activity;

    public BaseActivityTest() {
        super(BaseActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        PowerMockito.mockStatic(MediaPlayer.class);

        PowerMockito.when(MediaPlayer.create(Matchers.any(Context.class), Matchers.any(Integer.class)))
                .thenReturn(notificationSound);

        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                BaseActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    @Test
    public void start() {
        Assert.assertTrue(true);
    }
}