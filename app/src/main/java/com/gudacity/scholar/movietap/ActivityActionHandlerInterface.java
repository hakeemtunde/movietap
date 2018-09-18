package com.gudacity.scholar.movietap;

import android.widget.ProgressBar;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public interface ActivityActionHandlerInterface {
    void loadProgressBar();
    void unLoadProgressBar();
    abstract ProgressBar getProgressBar();
    abstract void LoadAdapterData(List data);
    abstract void fetchMovie(String criteria);
    abstract void networkErrorHandler(String errorMsg);
}
