package com.callbell.callbell.service.tasks;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.models.Request;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.inject.Inject;

/**
 * Created by austin on 10/13/15.
 */
public class PostRequestWithResponseTask extends AsyncTask<Request, String, String> {

    private static final String TAG = PostRequestWithResponseTask.class.getSimpleName();

    @Inject
    ServerEndpoints mServerEndpoints;

    @Inject
    PrefManager prefs;

    private Application app;
    private String intentEvent;

    private String response;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Intent i = new Intent(intentEvent);
        i.putExtra("response", s);
        LocalBroadcastManager.getInstance(app).sendBroadcast(i);
    }

    public PostRequestWithResponseTask (Application application, String ie) {
        app = application;
        ((CallBellApplication) app).inject(this);

        intentEvent = ie;
        response = "";
    }

    @Override
    protected String doInBackground(Request... params) {

        try {

            Request message = params[0];
            JSONObject request = message.toJSON();

            Log.d(TAG, "Request: " + request.toString());


            //Setup connection
            URL url = new URL("http://" + mServerEndpoints.EMULATOR_LOCALHOST_SERVER_ENDPOINT + message.getOperation());
            HttpURLConnection httpRequest = (HttpURLConnection) url.openConnection();

            httpRequest.setDoInput(true);
            httpRequest.setDoOutput(true);
            httpRequest.setRequestMethod("POST");
            httpRequest.setRequestProperty("Content-Type","application/json");
            httpRequest.connect();

            //Write JSON
            DataOutputStream printout;
            printout = new DataOutputStream(httpRequest.getOutputStream());
            printout.write(request.toString().getBytes("UTF-8"));
            printout.flush();
            printout.close();

            Scanner in = new Scanner(httpRequest.getInputStream());
            while (in.hasNextLine()) {
                response += in.nextLine();
            }
            Log.d(TAG, "Response: " + response);

            if (message.getOperation() == "/register") {
                //TODO ADD logic that actually checks this
                prefs.uploadedToken(true);
            }

        } catch (Exception e) {
            Log.e(TAG, "Caught Error on Post Request: " + e);
        }
        return response;
    }
}
