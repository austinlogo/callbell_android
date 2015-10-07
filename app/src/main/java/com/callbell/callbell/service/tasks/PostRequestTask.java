package com.callbell.callbell.service.tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.callbell.callbell.data.ServerMessageToJSONTranslator;
import com.callbell.callbell.models.ServerMessage;
import com.callbell.callbell.presentation.CallBellApplication;
import com.callbell.callbell.service.ServerEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.inject.Inject;

public class PostRequestTask extends AsyncTask<ServerMessage, String, JSONArray> {


    private static final String TAG = PostRequestTask.class.getSimpleName();
    private Context context;

    @Inject
    ServerMessageToJSONTranslator mJsonTranslator;

    @Inject
    ServerEndpoints mServerEndpoints;

    public PostRequestTask (Application application) {
        ((CallBellApplication) application).inject(this);
    }

    @Override
    protected JSONArray doInBackground(ServerMessage... params) {
        ServerMessage message = params[0];
        JSONObject request = mJsonTranslator.ServerMessageToJSON(message);

        Log.d(TAG, "String: " + request.toString());

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

        } catch (Exception e) {
            Log.e(TAG, "Caught Error on Post Request: " + e);
        }
        return null;
    }
}
