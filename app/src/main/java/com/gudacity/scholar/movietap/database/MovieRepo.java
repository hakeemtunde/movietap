package com.gudacity.scholar.movietap.database;

import android.content.Context;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public class MovieRepo {

    private final AppDatabase mAppDatabase;

    public MovieRepo(Context context) {
        mAppDatabase = AppDatabase.getsIntance(context);
    }

    public void save(Movie movie) {
        mAppDatabase.movieDAO().addMovie(movie);
    }

    public List<Movie> getAll() {
        return mAppDatabase.movieDAO().getAll();
    }

    public void unFavorite(Movie movie) {
        mAppDatabase.movieDAO().delete(movie);
    }
}
