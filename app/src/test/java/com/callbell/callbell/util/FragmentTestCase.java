package com.callbell.callbell.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

import org.junit.After;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

/**
 * Created by austin on 3/11/16.
 */
public class FragmentTestCase<T extends Fragment> {

    private static final String FRAGMENT_TAG = "fragment";

    private ActivityController controller;
    private FragmentActivity activity;
    private T fragment;

    /**
     * Adds the fragment to a new blank activity, thereby fully
     * initializing its view.
     */
    public void startFragment(T fragment) {
        this.fragment = fragment;
        controller = Robolectric.buildActivity(FragmentActivity.class);
        activity = (FragmentActivity) controller.create().start().visible().get();
        FragmentManager manager = activity.getFragmentManager();
        manager.beginTransaction()
                .add(fragment, FRAGMENT_TAG).commit();
    }

    @After
    public void destroyFragment() {
        if (fragment != null) {
            FragmentManager manager = activity.getFragmentManager();
            manager.beginTransaction().remove(fragment).commit();
            fragment = null;
            activity = null;
        }
    }

    public void pauseAndResumeFragment() {
        controller.pause().resume();
    }

    public T recreateFragment() {
        activity.recreate();
        // Recreating the activity creates a new instance of the
        // fragment which we need to retrieve by tag.
        fragment = (T) activity.getFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG);
        return fragment;
    }

}
