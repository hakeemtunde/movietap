package com.gudacity.scholar.movietap.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gudacity.scholar.movietap.R;
import com.gudacity.scholar.movietap.adapter.TrailerAdapter;
import com.gudacity.scholar.movietap.utils.ExtraUtil;
import com.gudacity.scholar.movietap.utils.JsonParser;
import com.gudacity.scholar.movietap.database.model.Movie;
import com.gudacity.scholar.movietap.database.model.MovieTrailer;
import com.gudacity.scholar.movietap.restapi.PathBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gudacity.scholar.movietap.restapi.PathBuilder.MOVIE_PATH;

public class TrailerActivity extends AbstractActivityAction {

    private static final String TAG = TrailerActivity.class.getSimpleName() ;

    @BindView((R.id.progressBar))
    public ProgressBar progressBar;
    @BindView(R.id.rv_trailers)
    public RecyclerView recyclerView;

    private Movie movie;

    private static final int LOADER_ID = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(DetailActivity.MOVIE_PARCELABLE_KEY);
            criteriaPosition = savedInstanceState.getInt(CRITERIA_POSITION);
        }else {
            Intent intent = getIntent();
            if (intent.hasExtra(DetailActivity.MOVIE_PARCELABLE_KEY)) {
                movie = intent.getParcelableExtra(DetailActivity.MOVIE_PARCELABLE_KEY);
                criteriaPosition = intent.getIntExtra(CRITERIA_POSITION, 0);
            }
        }

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                ExtraUtil.calculateBestSpanCount(420, getWindowManager()));

        recyclerView.setLayoutManager(layoutManager);

        fetchMovie(PathBuilder.getMovieTrailers(movie.getId()));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_PARCELABLE_KEY, movie);
        outState.putInt(CRITERIA_POSITION, criteriaPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = makeIntentWithParcelableData(
                    this, DetailActivity.class, movie);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void fetchMovie(String criteria) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_PATH, criteria);
        initializeLoader(bundle);
    }

    @Override
    public void LoadData(String data) {
        List<MovieTrailer> trailers = JsonParser.perseTrailer(data);

        if (trailers.size() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Movie has no trailer", Toast.LENGTH_LONG).show();
        }

        TrailerAdapter adapter = new TrailerAdapter(this, trailers);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void networkErrorHandler(String errorMsg) {

        Log.e(TAG, "networkErrorHandler: "+errorMsg );

    }

    public void launchYoutubePlayerActivity(String trailerKey) {
        Intent intent = new Intent(this, YoutubeTrailerPlayerActivity.class);
        intent.putExtra(JsonParser.REVIEW_KEY, trailerKey);
        startActivity(intent);
    }

}
