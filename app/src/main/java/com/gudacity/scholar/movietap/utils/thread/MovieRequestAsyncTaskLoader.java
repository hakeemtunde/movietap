package com.gudacity.scholar.movietap.utils.thread;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.gudacity.scholar.movietap.eventhandlerinterface.ActivityActionHandlerInterface;
import com.gudacity.scholar.movietap.restapi.Client;

import java.io.IOException;

public class MovieRequestAsyncTaskLoader extends AsyncTaskLoader<String> {

    private static final String TAG = MovieRequestAsyncTaskLoader.class.getSimpleName();
    private ActivityActionHandlerInterface activityActionHandler;
    private final String path;

    public MovieRequestAsyncTaskLoader(Context context,
                                       ActivityActionHandlerInterface activityHandler,
                                       String path) {
        super(context);
        this.activityActionHandler = activityHandler;
        this.path = path;
    }

    @Override
    public String loadInBackground() {

        String responseData = null;
        Client client = new Client();

        try{
            responseData = client.makeHttpRequest(path);

        }catch (IOException io) {
            activityActionHandler.networkErrorHandler(io.getMessage());

        }finally {
            try {
                client.disconnect();
            } catch (IOException e) {

                Log.e(TAG, "closing connection: "+ e.getMessage(), e);
            }
        }

        return responseData;
    }

}
