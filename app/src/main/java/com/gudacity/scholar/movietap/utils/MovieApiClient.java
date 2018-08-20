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
import java.util.ArrayList;
import java.util.List;

public class MovieApiClient {

    private static final String TAG = MovieApiClient.class.getSimpleName();

    private HttpURLConnection clientConnection;
    private BufferedReader bufferedReader;
    private int statusCode;

    public MovieApiClient() {

    }

    public String makeHttpRequest(String moviePath) throws IOException {

        URL url = new URL(moviePath);
        clientConnection = (HttpURLConnection)url.openConnection();

        StringBuilder responseStringBuilder = new StringBuilder();
        String inputLine;

        statusCode = clientConnection.getResponseCode();

        bufferedReader = new BufferedReader(
                new InputStreamReader(clientConnection.getInputStream()));

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseStringBuilder.append(inputLine);
        }

        return responseStringBuilder.toString();

    }

    public void disconnect() throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }

        clientConnection.disconnect();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<Movie> parseResponseToMovieList(String responseData) throws JSONException {

        List<Movie> movies = new ArrayList<>();

        if (responseData == null) return movies;

        JSONObject jsonObject = new JSONObject(responseData);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        for(int i=0; i<jsonArray.length(); i++) {

            long id = jsonArray.getJSONObject(i).getLong("id");
            double voteAvg = jsonArray.getJSONObject(i).getDouble("vote_average");
            String title = jsonArray.getJSONObject(i).getString("title");
            String posterPath = jsonArray.getJSONObject(i).getString("poster_path");
            String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
            String overview = jsonArray.getJSONObject(i).getString("overview");

            Movie movie = new Movie(id, title, voteAvg, posterPath, overview, releaseDate);
            movies.add(movie);
        }

        return movies;
    }
}
