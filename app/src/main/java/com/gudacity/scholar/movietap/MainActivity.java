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

import com.gudacity.scholar.movietap.database.AppDatabase;
import com.gudacity.scholar.movietap.utils.AppExecutor;
import com.gudacity.scholar.movietap.utils.ExtraUtil;
import com.gudacity.scholar.movietap.utils.JsonParser;
import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.PathBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.gudacity.scholar.movietap.utils.PathBuilder.MOVIE_PATH;

public class MainActivity extends AbstractActivityAction
        implements MainActivityAction {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_CRITERIA = "Most Popular";
    private int posterWidth = 250;

    @butterknife.BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @butterknife.BindView(R.id.rc_movie)
    public RecyclerView recyclerView;

    private List<Movie> mFavoriteMovies = new ArrayList<>();

    private static final int LOADER_ID = 101;

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

        if (savedInstanceState != null) {
            criteriaPosition = savedInstanceState.getInt(CRITERIA_POSITION);
                spinner.setSelection(criteriaPosition);
                adapter.notifyDataSetChanged();
        }

        if(getIntent().hasExtra(CRITERIA_POSITION)) {
            criteriaPosition = getIntent().getIntExtra(CRITERIA_POSITION, 0);
            spinner.setSelection(criteriaPosition);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CRITERIA_POSITION, criteriaPosition);
    }

    @Override
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

    @Override
    public void LoadData(String data) {
        List<Movie> movies = JsonParser.parseResponseToMovie(data);
        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), movies, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchMovie(String criteria) {

        //favorite movie
        if (criteria.equalsIgnoreCase(getString(R.string.my_favorite_txt))) {

            final MainActivity mainActivity = this;

            AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mFavoriteMovies = AppDatabase.getsIntance(mainActivity.getApplicationContext())
                            .movieDAO().getAll();

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MovieAdapter adapter = new MovieAdapter(mainActivity.getApplicationContext(),
                                    mFavoriteMovies, mainActivity);
                            recyclerView.setAdapter(adapter);
                        }
                    });


                }
            });

            return;
        }

        //online
        boolean isPopular = criteria.equals(DEFAULT_CRITERIA);

        String path = PathBuilder.getMoviePath(isPopular);
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_PATH, path);

        initializeLoader(bundle);


    }

    @Override
    public void startNewActivityWithMovie(Movie movie) {

        Intent intent = makeIntentWithParcelableData(this,
                DetailActivity.class, movie);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        criteriaPosition = adapterView.getSelectedItemPosition();
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

    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

}
