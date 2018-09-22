package com.gudacity.scholar.movietap.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void addMovie(Movie movie);

    @Query("SELECT * FROM movie")
    List<Movie> getAll();
}
