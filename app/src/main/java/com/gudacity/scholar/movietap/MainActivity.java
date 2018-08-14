package com.gudacity.scholar.movietap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieRequestAsyncThread movieRequest = new MovieRequestAsyncThread();
        movieRequest.execute();

    }
}
