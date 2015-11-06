package com.callbell.callbell.util;

import android.util.Log;

/**
 * Created by austin on 11/3/15.
 */
public class ThreadUtil {

    private static final String TAG = ThreadUtil.class.getSimpleName();

    public static void sleep(long timeInMs, Thread thread) {
        try {
            thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            Log.e(TAG, "Thread Interrupted: " + e);
        }
    }
}
