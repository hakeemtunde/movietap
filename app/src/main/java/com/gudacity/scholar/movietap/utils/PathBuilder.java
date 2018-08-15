package com.gudacity.scholar.movietap.utils;

import com.gudacity.scholar.movietap.BuildConfig;

public class PathBuilder {

    public static final String CONFIG_PATH = "?api_key=";
    public static final String API_BASE_URL = "http://api.themoviedb.org/";
    public static int API_VERSION = 3;
    public static final String POPULAR_MOVIE_PATH = "/movie/popular";
    public static final String TOP_RATED_MOVIE_PATH = "/movie/top_rated";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE ="w185";

    private static final String API_KEY = BuildConfig.API_KEY;

    public static String buildPosterUri(String posterPath) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(POSTER_BASE_URL).append(POSTER_SIZE).append(posterPath);
        return stringBuilder.toString();

    }

    public static String getApiConfiguration() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONFIG_PATH).append(API_KEY);
        return stringBuilder.toString();
    }

    public static String getPopularMoviePath() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(API_BASE_URL).append(API_VERSION)
                .append(POPULAR_MOVIE_PATH).append(getApiConfiguration());

        return stringBuilder.toString();
    }

    public static String getTopRatedMoviePath() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(API_BASE_URL).append(API_VERSION)
                .append(TOP_RATED_MOVIE_PATH).append(getApiConfiguration());

        return stringBuilder.toString();
    }
}
