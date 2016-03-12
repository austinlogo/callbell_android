package com.callbell.callbell.util;

import com.callbell.callbell.BuildConfig;

/**
 * Created by austin on 3/11/16.
 */
public class FragmentAssertionsUtil {

    public static void assertStatement(boolean assertion) {
        if (BuildConfig.DEBUG && !assertion) {
            throw new AssertionError();
        }
    }
}
