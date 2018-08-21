package com.gudacity.scholar.movietap;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.widget.AdapterView;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public interface MainActivityAction extends AdapterView.OnItemSelectedListener{

    public void loadProgressBar();

    public void unLoadProgressBar();

    public void LoadAdapterData(List<Movie> movies);

    public void fetchMovie(String criteria);

    public void startNewActivityWithMovie(Movie movie);

    public void launchNetworkErrorActivity(String errormsg);
}
