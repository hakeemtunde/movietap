package com.gudacity.scholar.movietap.eventhandlerinterface;

import android.widget.AdapterView;

import com.gudacity.scholar.movietap.database.model.Movie;

public interface MainActivityAction extends AdapterView.OnItemSelectedListener{

    void startNewActivityWithMovie(Movie movie);

}
