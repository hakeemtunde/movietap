package com.gudacity.scholar.movietap.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class MovieApiClient {

    private static final String TAG = MovieApiClient.class.getSimpleName();

    private HttpURLConnection clientConnection;
    private BufferedReader bufferedReader;
    private int statusCode;

    private static final String MOVIE_ID = "id";
    private static final String MOVIE_VOTE = "vote_average";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POST_PATH = "poster_path";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_OVERVIEW = "overview";

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

            long id = jsonArray.getJSONObject(i).optLong(MOVIE_ID, -1);
            double voteAvg = jsonArray.getJSONObject(i).optDouble(MOVIE_VOTE, 0.0);
            String title = jsonArray.getJSONObject(i).optString(MOVIE_TITLE, "");
            String posterPath = jsonArray.getJSONObject(i).optString(MOVIE_POST_PATH, "-");
            String releaseDate = jsonArray.getJSONObject(i).optString(MOVIE_RELEASE_DATE, "00-00-0000");
            String overview = jsonArray.getJSONObject(i).optString(MOVIE_OVERVIEW, "-");

            Movie movie = new Movie(id, title, voteAvg, posterPath, overview, releaseDate);
            movies.add(movie);
        }

        return movies;
    }
}
