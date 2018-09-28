package com.gudacity.scholar.movietap.eventhandlerinterface;

import android.support.v4.app.LoaderManager;
import android.widget.ProgressBar;

public interface ActivityActionHandlerInterface
        extends LoaderManager.LoaderCallbacks<String>{
    void loadProgressBar();
    void unLoadProgressBar();
    ProgressBar getProgressBar();
    void fetchMovie(String criteria);
    void LoadData(String data);
    void networkErrorHandler(String errorMsg);
    int getLoaderId();
}
