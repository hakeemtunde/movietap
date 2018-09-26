package com.gudacity.scholar.movietap.utils;

import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    private static final String ID = "id";
    private static final String MOVIE_VOTE = "vote_average";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POST_PATH = "poster_path";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String RESULT_KEY = "results";

    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    private static final String REVIEW_NAME = "name";
    public static final String REVIEW_KEY = "key";
    private static final String REVIEW_SITE = "site";
    private static final String REVIEW_SIZE = "size";
    private static final String REVIEW_TYPE = "type";


    public static List<Movie> parseResponseToMovie(String response) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = parseMovieJSON(response);
        } catch (JSONException io) {
            Log.e(TAG, io.getMessage(), io);
        }

        return movies;
    }

    public static List<MovieReview> parseMovieReview(String response) {
        List<MovieReview> movieReviews = new ArrayList<>();
        try{
            movieReviews = parseMovieReviewJSON(response);
        }catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return movieReviews;
    }

    public static List<MovieTrailer> perseTrailer(String response) {
        List<MovieTrailer> trailers = new ArrayList<>();
        try{
            trailers = parseTrailerJSON(response);
        }catch (JSONException e) {
            Log.e(TAG,e.getMessage(), e);
        }
        return trailers;

    }

    private static List<Movie> parseMovieJSON(String responseData) throws JSONException {

        List<Movie> movies = new ArrayList<>();

        if (responseData == null) return movies;

        JSONObject jsonObject = new JSONObject(responseData);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULT_KEY);

        for(int i=0; i<jsonArray.length(); i++) {

            long id = jsonArray.getJSONObject(i).optLong(ID, -1);
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

    private  static List<MovieReview> parseMovieReviewJSON(String data) throws  JSONException {
        List<MovieReview> movieReviews = new ArrayList<>();

        if (data == null) return movieReviews;

        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULT_KEY);

        for(int i=0; i< jsonArray.length(); i++) {
            int id = jsonArray.getJSONObject(i).optInt(ID, -1);
            String author = jsonArray.getJSONObject(i).getString(AUTHOR);
            String content = jsonArray.getJSONObject(i).optString(CONTENT, "---");
            String url = jsonArray.getJSONObject(i).getString(URL);

            MovieReview movieReview = new MovieReview(id, author, content, url);
            movieReviews.add(movieReview);
        }

        return movieReviews;
    }

    private static List<MovieTrailer> parseTrailerJSON(String responseData) throws JSONException {

        List<MovieTrailer> trailers = new ArrayList<>();

        if (responseData == null) return trailers;

        JSONObject jsonObject = new JSONObject(responseData);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULT_KEY);

        for (int i = 0; i < jsonArray.length(); i++) {

            String id = jsonArray.getJSONObject(i).optString(ID, "-");
            String name = jsonArray.getJSONObject(i).optString(REVIEW_NAME, "");
            String key = jsonArray.getJSONObject(i).optString(REVIEW_KEY, "");
            String type = jsonArray.getJSONObject(i).optString(REVIEW_TYPE, "-");
            String site = jsonArray.getJSONObject(i).optString(REVIEW_SITE, "");
            int size = jsonArray.getJSONObject(i).optInt(REVIEW_SIZE, 0);

            MovieTrailer trailer = new MovieTrailer(id, name, key, type, site, size);
            trailers.add(trailer);
        }
        return trailers;
    }
}
