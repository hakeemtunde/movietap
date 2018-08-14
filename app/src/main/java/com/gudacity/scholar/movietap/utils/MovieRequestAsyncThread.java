package com.gudacity.scholar.movietap.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

public class MovieRequestAsyncThread extends AsyncTask<Void, Void, String> {

    private static final String TAG = MovieRequestAsyncThread.class.getSimpleName();

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            MovieApiClient.parseJson(s);
        } catch (JSONException io) {
            Log.e(TAG, io.getMessage(), io);
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        String responseData = null;
        try{
            responseData = MovieApiClient.getPopularMovies();
        }catch (IOException io) {
            io.printStackTrace();
            Log.e(TAG, "doInBackground: "+ io.getMessage(), io );
        }

        return responseData;
    }
}
