package com.callbell.callbell.presentation;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.test.mock.MockContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith (PowerMockRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "javax.management.*"})
@PrepareForTest(MediaPlayer.class)
public class BaseActivityTest {
//
//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    @Mock
    MediaPlayer mediaPlayer;

    @Mock
            Bundle b;

    MockContext ctx = new MockContext();
    BaseActivity activity = new BaseActivity();

    @Before
    public void setUp() throws Exception {
//        activity = Robolectric.buildActivity(BaseActivity.class).create().get();

    }

    @Test
    public void start() throws Exception {
        PowerMockito.mockStatic(MediaPlayer.class);
        Mockito.when(MediaPlayer.create(Matchers.any(Context.class), Matchers.anyInt()))
                .thenReturn(mediaPlayer);

        activity.onCreate(b);

        Assert.assertTrue(true);
    }
}