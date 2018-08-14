package com.gudacity.scholar.movietap.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieApiClient {

    private static final String TAG = MovieApiClient.class.getSimpleName();

    public static String getPopularMovies() throws IOException {

        URL url = new URL(PathBuilder.getPopularMoviePath());
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        StringBuilder responseStringBuilder = new StringBuilder();
        String inputLine;

        int responseCode = connection.getResponseCode();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseStringBuilder.append(inputLine);
        }

        bufferedReader.close();
        connection.disconnect();

        Log.i(TAG, "status: "+ responseCode);

        return responseStringBuilder.toString();

    }

    public static void parseJson(String responseData) throws JSONException {

        JSONObject jsonObject = new JSONObject(responseData);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        for(int i=0; i<jsonArray.length(); i++) {

            long id = jsonArray.getJSONObject(i).getLong("id");
            double voteAvg = jsonArray.getJSONObject(i).getDouble("vote_average");
            String title = jsonArray.getJSONObject(i).getString("title");
            String posterPath = jsonArray.getJSONObject(i).getString("poster_path");
            String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
            String overview = jsonArray.getJSONObject(i).getString("overview");
            Log.i(TAG, "test: id"+ id + " vote_avg: "+
                    voteAvg + " title: "+ title + " poster path: "+ posterPath
                    + " release_date: "+ releaseDate + " overview: "+ overview);
        }
    }
}
