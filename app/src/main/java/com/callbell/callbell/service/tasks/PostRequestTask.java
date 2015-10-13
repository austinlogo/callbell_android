package com.callbell.callbell.service.tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.data.ServerMessageToJSONTranslator;
import com.callbell.callbell.models.Request;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.service.ServerEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.inject.Inject;

public class PostRequestTask extends AsyncTask<Request, String, JSONArray> {


    private static final String TAG = PostRequestTask.class.getSimpleName();
    private Context context;

    @Inject
    ServerMessageToJSONTranslator mJsonTranslator;

    @Inject
    ServerEndpoints mServerEndpoints;

    @Inject
    PrefManager prefs;

    public PostRequestTask (Application application) {
        ((CallBellApplication) application).inject(this);
    }

    @Override
    protected JSONArray doInBackground(Request... params) {
        Request message = params[0];
        JSONObject request = message.toJSON();

        Log.d(TAG, "Request: " + request.toString());

        try {

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

            String response = "";

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
        return null;
    }
}
