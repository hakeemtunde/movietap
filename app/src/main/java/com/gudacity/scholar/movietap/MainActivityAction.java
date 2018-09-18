package com.gudacity.scholar.movietap;

import android.widget.AdapterView;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public interface MainActivityAction extends AdapterView.OnItemSelectedListener{

    public void startNewActivityWithMovie(Movie movie);

}
