package com.gudacity.scholar.movietap.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.gudacity.scholar.movietap.MovieAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRequestAsyncThread extends AsyncTask<String, Void, String> {

    private static final String TAG = MovieRequestAsyncThread.class.getSimpleName();
    private MovieApiClient client;
    private Context context;
    private ListView listView;

    public MovieRequestAsyncThread(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        List<Movie> movies = parseResponseToMovie(response);
        MovieAdapter adapter = new MovieAdapter(context, movies );

        listView.setAdapter(adapter);

    }

    @Override
    protected String doInBackground(String... params) {
        String responseData = null;
        String path = params[0];
        client = new MovieApiClient();

        try{

            responseData = client.makeHttpRequest(path);

        }catch (IOException io) {
            Log.e(TAG, "doInBackground: "+ io.getMessage(), io );

        }finally {
            try {
                client.disconnect();
            } catch (IOException e) {

                Log.e(TAG, "closing connection: "+ e.getMessage(), e);
            }
        }

        return responseData;
    }

    private List<Movie> parseResponseToMovie(String response) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = client.parseResponseToMovieList(response);
        } catch (JSONException io) {
            Log.e(TAG, io.getMessage(), io);
        }

        return movies;
    }
}
