package com.callbell.callbell.service.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.callbell.callbell.presentation.dialogs.PainRatingDialog;

/**
 * Created by austin on 10/26/15.
 */
public class PainRatingAsyncTask
        extends AsyncTask<Integer, Integer, Void> {

    private static final String TAG = PainRatingAsyncTask.class.getSimpleName();
    private int mIntervalInMinutes;
    private long mIntervalInMilliseconds;
    private Activity activity;
    private Thread mThread;

    public PainRatingAsyncTask(Activity act) {
        activity = act;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.d(TAG, "onPostExecute");

        PainRatingDialog dialog = new PainRatingDialog();
        dialog.show(activity.getFragmentManager(), "TASK TIMER");
    }

    @Override
    protected Void doInBackground(Integer... params) {
        if (params.length == 0) {
            return null;
        }

        mIntervalInMinutes = params[0];
        mIntervalInMilliseconds = mIntervalInMinutes * 60 * 1000;
        Log.d(TAG, "Pain Rating Started: " + mIntervalInMinutes + " minutes");

        try {
            mThread = Thread.currentThread();
            Thread.sleep(mIntervalInMilliseconds);
        } catch (InterruptedException e) {
            Log.e(TAG, "Thread interrupted: " + e);
        }


        return null;
    }

    public void interrupt() {
        if (mThread != null) {
            Log.d(TAG, "Interrupting thread " + mThread.toString());
            mThread.interrupt();
        } else
            Log.d(TAG, "Thread null");
    }
}
