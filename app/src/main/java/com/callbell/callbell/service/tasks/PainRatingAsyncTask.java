package com.callbell.callbell.service.tasks;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;

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
    private Activity activity;
    private Thread mThread;
    private MediaPlayer notificationSound;

    public PainRatingAsyncTask(Activity act) {
        activity = act;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.d(TAG, "onPostExecute");

        if (!threadCompletedSuccessfully) {
            return;
        }

        notificationSound = MediaPlayer.create(activity, R.raw.notification);
        notificationSound.setLooping(false);
        notificationSound.start();

        PainRatingDialog dialog = new PainRatingDialog();
        dialog.show(activity.getFragmentManager(), "TASK TIMER");
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
