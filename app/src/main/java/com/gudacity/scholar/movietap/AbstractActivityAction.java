package com.gudacity.scholar.movietap;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

abstract public class AbstractActivityAction
        extends AppCompatActivity implements ActivityActionHandlerInterface {

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
}
