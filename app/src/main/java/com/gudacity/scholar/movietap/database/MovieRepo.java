package com.gudacity.scholar.movietap.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public class MovieRepo {

    private final AppDatabase mAppDatabase;

    private static MovieRepo INSTANCE;

    public static MovieRepo getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new MovieRepo(context);
        }
        return INSTANCE;
    }

    public MovieRepo(Context context) {
        mAppDatabase = AppDatabase.getsIntance(context);
    }

    public void save(Movie movie) {
        mAppDatabase.movieDAO().addMovie(movie);
    }

    public LiveData<List<Movie>> getAll() {
        return mAppDatabase.movieDAO().getAll();
    }

    public void unFavorite(Movie movie) {
        mAppDatabase.movieDAO().delete(movie);
    }
}
