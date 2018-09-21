package com.gudacity.scholar.movietap;

import android.widget.ProgressBar;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public interface ActivityActionHandlerInterface {
    void loadProgressBar();
    void unLoadProgressBar();
    abstract ProgressBar getProgressBar();
    abstract void fetchMovie(String criteria);
    abstract void LoadData(String data);
    abstract void networkErrorHandler(String errorMsg);
}
