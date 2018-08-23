package com.gudacity.scholar.movietap.utils;

import com.gudacity.scholar.movietap.BuildConfig;

public class PathBuilder {

    private static final String CONFIG_PATH = "?api_key=";
    private static final String API_BASE_URL = "http://api.themoviedb.org/";
    private static final int API_VERSION = 3;
    private static final String POPULAR_MOVIE_PATH = "/movie/popular";
    private static final String TOP_RATED_MOVIE_PATH = "/movie/top_rated";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE ="w185";

    private static final String API_KEY = BuildConfig.API_KEY;

    public static String buildPosterImagePath(String posterPath) {

        return POSTER_BASE_URL + POSTER_SIZE + posterPath;

    }

    public static String getApiConfiguration() {
        return CONFIG_PATH + API_KEY;
    }

    public static String getMoviePath(boolean popular) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(API_BASE_URL).append(API_VERSION);

        if (popular) {
            stringBuilder.append(POPULAR_MOVIE_PATH);
        } else {
            stringBuilder.append(TOP_RATED_MOVIE_PATH);
        }

        stringBuilder.append(getApiConfiguration());

        return stringBuilder.toString();
    }

}
