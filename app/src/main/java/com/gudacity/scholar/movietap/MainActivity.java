package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.gudacity.scholar.movietap.utils.ExtraUtil;
import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.PathBuilder;

import java.util.List;

public class MainActivity extends AbstractActivityAction
        implements MainActivityAction {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_CRITERIA = "Most Popular";
    int posterWidth = 250;

    @butterknife.BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @butterknife.BindView(R.id.rc_movie)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butterknife.ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //check network status
        ifNetworkErrorLaunchNetworkErrorActivity(getApplicationContext());

        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.movie_sort_options, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                ExtraUtil.calculateBestSpanCount(posterWidth, getWindowManager()),
                GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);

    }

    @Override
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

    @Override
    public void LoadData(List data) {
        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), data, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchMovie(String criteria) {
        boolean isPopular = criteria.equals(DEFAULT_CRITERIA);
        MovieRequestAsyncThread movieRequest = new MovieRequestAsyncThread(this);
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
    public void networkErrorHandler(String errorMsg) {

        Intent intent = new Intent(this, NetworkErrorActivity.class);
        intent.putExtra(NetworkErrorActivity.NETWORK_ERROR_EXTRA, errorMsg);
        startActivity(intent);

        finish();
    }

}
