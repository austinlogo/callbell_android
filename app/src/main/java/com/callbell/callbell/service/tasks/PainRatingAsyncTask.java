package com.callbell.callbell.service.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.util.PrefManager;

/**
 * Created by austin on 10/26/15.
 */
public class PainRatingAsyncTask
        extends AsyncTask<Integer, Integer, Void> {

    public static PainRatingAsyncTask mRunningTask = null;

    private static final String TAG = PainRatingAsyncTask.class.getSimpleName();
    private int mIntervalInMinutes;
    private long mIntervalInMilliseconds;
    private boolean threadCompletedSuccessfully;
    private Context applicationContext;
    private Thread mThread;
    private MediaPlayer notificationSound;

    public PainRatingAsyncTask(Context act) {
        applicationContext = act;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.d(TAG, "onPostExecute");

        if (!threadCompletedSuccessfully) {
            return;
        }

        Bundle bundle = new Bundle();
        Intent i = new Intent(PrefManager.EVENT_RATE_PAIN);
        i.putExtras(bundle);

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i);
    }

    @Override
    protected Void doInBackground(Integer... params) {
        if (params.length == 0) {
            return null;
        }

        threadCompletedSuccessfully = false;
        mIntervalInMinutes = params[0];

        if (mIntervalInMinutes == 0) {
            return null;
        }

        mIntervalInMilliseconds = mIntervalInMinutes * 60 * 1000;
        Log.d(TAG, "Pain Rating Started: " + mIntervalInMinutes + " minutes");

        try {
            mRunningTask = this;
            mThread = Thread.currentThread();
            Thread.sleep(mIntervalInMilliseconds);
        } catch (InterruptedException e) {
            mRunningTask = null;
            Log.w(TAG, "Thread interrupted: " + e);
            return null;
        }

        threadCompletedSuccessfully = true;
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mRunningTask != null) {
            mRunningTask.interrupt();
        }
    }

    public void interrupt() {
        if (mThread != null) {
            Log.d(TAG, "Interrupting thread " + mThread.toString());
            mThread.interrupt();
        } else
            Log.d(TAG, "Thread null");

        mRunningTask = null;
    }
}
