package com.gudacity.scholar.movietap;

import android.support.v4.app.LoaderManager;
import android.widget.ProgressBar;

public interface ActivityActionHandlerInterface
        extends LoaderManager.LoaderCallbacks<String>{
    void loadProgressBar();
    void unLoadProgressBar();
    abstract ProgressBar getProgressBar();
    abstract void fetchMovie(String criteria);
    abstract void LoadData(String data);
    abstract void networkErrorHandler(String errorMsg);
    abstract int getLoaderId();
}
