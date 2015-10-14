package com.callbell.callbell.service.tasks;

import android.app.Application;
import android.util.Log;

import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

/**
 * Created by austin on 10/13/15.
 */
public class PostRequestWithCallbackTask extends PostRequestTask {

    private static final String TAG = PostRequestWithCallbackTask.class.getSimpleName();

    @Inject
    ServerEndpoints mServerEndpoints;

    @Inject
    PrefManager prefs;

    private Application app;
    private String intentEvent;
    private PostRequestTaskListener mListener;


    public PostRequestWithCallbackTask(Application application, String ie, Object caller) {
        super(application);

        intentEvent = ie;

        try {
            mListener = (PostRequestTaskListener) caller;
        } catch (ClassCastException e) {
            Log.e(TAG, "Caller does not implement PostRequestTaskListener");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        mListener.onTaskCompleted(s);
//        Intent i = new Intent(intentEvent);
//        i.putExtra("response", s);

//        LocalBroadcastManager.getInstance(app).sendBroadcast(i);
    }



    public interface PostRequestTaskListener {
        void onTaskCompleted(String response);
    }

}
