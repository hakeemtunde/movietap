package com.gudacity.scholar.movietap;

import android.widget.AdapterView;

import com.gudacity.scholar.movietap.utils.Movie;

public interface MainActivityAction extends AdapterView.OnItemSelectedListener{

    void startNewActivityWithMovie(Movie movie);

}
