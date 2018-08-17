package com.gudacity.scholar.movietap;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public interface MainActivityAction {

    public void loadProgressBar();

    public void unLoadProgressBar();

    public void LoadAdapterData(List<Movie> movies);
}
