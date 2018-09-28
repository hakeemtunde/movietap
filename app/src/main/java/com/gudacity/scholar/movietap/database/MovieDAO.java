package com.gudacity.scholar.movietap.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void addMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovie(long id);
}
