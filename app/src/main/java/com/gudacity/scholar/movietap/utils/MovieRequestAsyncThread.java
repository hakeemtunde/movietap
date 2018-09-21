package com.gudacity.scholar.movietap.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.gudacity.scholar.movietap.ActivityActionHandlerInterface;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRequestAsyncThread extends AsyncTask<String, Void, String> {

    private static final String TAG = MovieRequestAsyncThread.class.getSimpleName();

    private MovieApiClient client;
    private final ActivityActionHandlerInterface activityActionHandler;

    public MovieRequestAsyncThread(ActivityActionHandlerInterface activityAsyncAction) {
        this.activityActionHandler = activityAsyncAction;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activityActionHandler.loadProgressBar();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        activityActionHandler.LoadData(response);
        activityActionHandler.unLoadProgressBar();
    }

    @Override
    protected String doInBackground(String... params) {
        String responseData = null;
        String path = params[0];
        client = new MovieApiClient();

        try{

            responseData = client.makeHttpRequest(path);

        }catch (IOException io) {
            Log.e(TAG, "doInBackground: "+ io.getMessage(), io );
            activityActionHandler.networkErrorHandler(io.getMessage());

        }finally {
            try {
                client.disconnect();
            } catch (IOException e) {

                Log.e(TAG, "closing connection: "+ e.getMessage(), e);
            }
        }

        return responseData;
    }


}
