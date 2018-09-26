package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gudacity.scholar.movietap.utils.ExtraUtil;
import com.gudacity.scholar.movietap.utils.JsonParser;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.MovieTrailer;
import com.gudacity.scholar.movietap.utils.PathBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerActivity extends AbstractActivityAction {

    public static final String ID = "MOVIE_ID";
    private static final String TAG = TrailerActivity.class.getSimpleName() ;

    @BindView((R.id.progressBar))
    public ProgressBar progressBar;
    @BindView(R.id.rv_trailers)
    public RecyclerView recyclerView;

    private long movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            movieId = savedInstanceState.getLong(ID);
        }else {
            Intent intent = getIntent();
            if (intent.hasExtra(ID)) {
                movieId = intent.getLongExtra(ID, 0);
            }
        }


        Toast.makeText(getApplicationContext(), "Number: "+ movieId, Toast.LENGTH_SHORT).show();

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                ExtraUtil.calculateBestSpanCount(420, getWindowManager()));

        recyclerView.setLayoutManager(layoutManager);

        fetchMovie(PathBuilder.getMovieTrailers(movieId));

    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void fetchMovie(String criteria) {
        Log.i(TAG, "location: "+ criteria);
        MovieRequestAsyncThread movieRequestAsyncThread = new MovieRequestAsyncThread(this);
        movieRequestAsyncThread.execute(criteria);
    }

    @Override
    public void LoadData(String data) {
        List<MovieTrailer> trailers = JsonParser.perseTrailer(data);
        TrailerAdapter adapter = new TrailerAdapter(this, trailers);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void networkErrorHandler(String errorMsg) {

        Log.e(TAG, "networkErrorHandler: "+errorMsg );

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ID, movieId);
    }

    public void launchYoutubePlayerActivity(String trailerKey) {
        Intent intent = new Intent(this, TrailerYoutubePalyerActivity.class);
        intent.putExtra(JsonParser.REVIEW_KEY, trailerKey);
        startActivity(intent);
    }

}
