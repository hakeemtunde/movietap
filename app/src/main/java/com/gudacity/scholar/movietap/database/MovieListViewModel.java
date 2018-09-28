package com.gudacity.scholar.movietap.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public class MovieListViewModel extends AndroidViewModel {

    public MovieListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMovies() {
        return MovieRepo.getInstance(this.getApplication()).getAll();
    }
}
