package com.gudacity.scholar.movietap;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncTaskLoader;

import static com.gudacity.scholar.movietap.utils.PathBuilder.MOVIE_PATH;

abstract public class AbstractActivityAction
        extends AppCompatActivity implements ActivityActionHandlerInterface {

    public static final String CRITERIA_POSITION = "position";
    public static final String MOVIE_PARCELABLE_KEY = "movie";

    protected int criteriaPosition;

    @Override
    public void loadProgressBar() {
        getProgressBar().setIndeterminate(true);
        getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    public void unLoadProgressBar() {
        getProgressBar().setVisibility(View.GONE);
    }

    public void ifNetworkErrorLaunchNetworkErrorActivity(Context context) {

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {

                throw new NetworkErrorException(
                        getString(R.string.network_error_msg));
            }

        } catch (NetworkErrorException e) {

            networkErrorHandler(e.getMessage());

        }

    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        loadProgressBar();

        String path = args.getString(MOVIE_PATH);
        MovieRequestAsyncTaskLoader asyncTaskLoader =
                new MovieRequestAsyncTaskLoader(getApplicationContext(),
                        this, path);
        return asyncTaskLoader;
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String data) {
        LoadData(data);
        unLoadProgressBar();
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {
    }

    protected void initializeLoader(Bundle bundle) {
        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(getLoaderId());

        if(loader == null) {
            loaderManager.initLoader(getLoaderId(), bundle, this).forceLoad();
        }else {
            loaderManager.restartLoader(getLoaderId(), bundle, this).forceLoad();
        }
    }

    protected Intent makeIntentWithParcelableData(Context context, Class<?> activity, Movie movie) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(MOVIE_PARCELABLE_KEY, movie);
        intent.putExtra(CRITERIA_POSITION, criteriaPosition);
        return intent;

    }

}
