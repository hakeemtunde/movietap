package com.gudacity.scholar.movietap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.PathBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityAction {

    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean isPopular = true;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.rc_movie);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        MovieRequestAsyncThread movieRequest = new MovieRequestAsyncThread(getApplicationContext(),
                 this );

        movieRequest.execute(PathBuilder.getMoviePath(isPopular));

    }


    @Override
    public void loadProgressBar() {

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void unLoadProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void LoadAdapterData(List<Movie> movies) {

        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), movies);
        recyclerView.setAdapter(adapter);

    }
}
