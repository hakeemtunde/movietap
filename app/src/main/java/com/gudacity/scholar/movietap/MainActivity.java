package com.gudacity.scholar.movietap;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.PathBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainActivityAction {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String DEFAULT_CRITERIA = "Most Popular";
    private boolean isPopular;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //check network status
        ifNetworkErrorLaunchNetworkErrorActivity(getApplicationContext());

        AppCompatSpinner spinner = (AppCompatSpinner)findViewById(R.id.spinner);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.rc_movie);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.movie_sort_options, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);



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
        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), movies, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchMovie(String criteria) {

        isPopular = criteria.equals(DEFAULT_CRITERIA) ? true : false;

        MovieRequestAsyncThread movieRequest = new MovieRequestAsyncThread(getApplicationContext(),
                this );

        movieRequest.execute(PathBuilder.getMoviePath(isPopular));
    }

    @Override
    public void startNewActivityWithMovie(Movie movie) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_PARCELABLE_KEY, movie);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String criteria = (String) adapterView.getSelectedItem();
        fetchMovie(criteria);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void launchNetworkErrorActivity(String errormsg) {

        Intent intent = new Intent(this, NetworkErrorActivity.class);
        intent.putExtra(NetworkErrorActivity.NETWORK_ERROR_EXTRA, errormsg);
        startActivity(intent);

        finish();
    }

    private void ifNetworkErrorLaunchNetworkErrorActivity(Context context)  {

        ConnectivityManager manager =  (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        try {

            if (networkInfo == null || !networkInfo.isConnected()) {

                throw new NetworkErrorException(
                        getString(R.string.network_error_msg));
            }

        } catch (NetworkErrorException e) {

            launchNetworkErrorActivity(e.getMessage());

        }


    }
}
