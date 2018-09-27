package com.gudacity.scholar.movietap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gudacity.scholar.movietap.database.AppDatabase;
import com.gudacity.scholar.movietap.database.MovieRepo;
import com.gudacity.scholar.movietap.utils.AppExecutor;
import com.gudacity.scholar.movietap.utils.JsonParser;
import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieReview;
import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gudacity.scholar.movietap.MainActivity.CRITERIA_POSITION;
import static com.gudacity.scholar.movietap.utils.PathBuilder.MOVIE_PATH;

public class DetailActivity extends AbstractActivityAction {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    TextView titleTv;
    @BindView(R.id.tv_release_date)
    TextView releaseDateTv;
    @BindView(R.id.tv_vote)
    TextView voteTv;
    @BindView(R.id.tv_synopsis)
    TextView synopsisTv;
    @BindView(R.id.detail_movie_poster)
    ImageView moviePosterIv;
    @BindView(R.id.btn_load_review)
    Button loadReviewBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rv_reviews)
    RecyclerView recyclerView;
    @BindView(R.id.tv_review_label)
    TextView reviewLabelTv;
    @BindView(R.id.network_error_tv)
    TextView networkErrorTv;
    @BindView(R.id.btn_favorite)
    Button favoriteBtn;
    @BindView(R.id.btn_show_trailer)
    Button showTrailerBtn;

    private Movie movie;
    private MovieRepo movieRepo;
    private String mPosterPath;
    private boolean isFavorite = false;

    private static final int LOADER_ID = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(MOVIE_PARCELABLE_KEY);
            criteriaPosition = savedInstanceState.getInt(CRITERIA_POSITION);
        }else {

            if (getIntent().hasExtra(MOVIE_PARCELABLE_KEY)) {

                Bundle bundle = getIntent().getExtras();
                movie = bundle.getParcelable(MOVIE_PARCELABLE_KEY);
                criteriaPosition = bundle.getInt(CRITERIA_POSITION);
            } else { return; }
        }

        mPosterPath = PathBuilder.buildPosterImagePath(movie.getPosterPath());

        //isFavoriteMovie
        ifFavoriteHideBtn();

        populateUI();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_PARCELABLE_KEY, movie);
        outState.putInt(CRITERIA_POSITION, criteriaPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(CRITERIA_POSITION, criteriaPosition);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateUI() {

        titleTv.setText(movie.getTitle());
        voteTv.setText(String.valueOf(movie.getVoteAverage()));
        releaseDateTv.setText(movie.getDate());
        synopsisTv.setText(movie.getSynopsis());

        Picasso picasso = Picasso.with(this);
        RequestCreator requestCreator;

        if(isFavorite) {
            String posterName = movie.getPosterPath().substring(1,
                    movie.getPosterPath().indexOf("."));
                    requestCreator = picasso.load(new File(posterName));

        } else {
            requestCreator = picasso.load(mPosterPath);
        }

        requestCreator.into(moviePosterIv);

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

        List<MovieReview> movieReviews = JsonParser.parseMovieReview(data);

        if (movieReviews.size() == 0 ) {
            Toast.makeText(getApplicationContext(),
                    "Movie has no reviews", Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter adapter = new ReviewAdapter(movieReviews);
        recyclerView.setAdapter(adapter);
        hideViews();
    }

    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public void networkErrorHandler(String errorMsg) {
        networkErrorTv.setText(errorMsg);
        networkErrorTv.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btn_load_review)
    public void onLoadReviewBtnClick(View view) {

        String path = PathBuilder.getMovieReviewPath(movie.getId());
        fetchMovie(path);
    }

    @OnClick(R.id.btn_favorite)
    public void onFavoriteBtnClick(View view) {

        movieRepo = new MovieRepo(getApplicationContext());
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                movieRepo.save(movie);
                //save image
                downloadMoviePoster();

            }
        });

        //hide favorite btn
        favoriteBtn.setVisibility(View.INVISIBLE);

        Toast.makeText(getApplicationContext(),
                "Favorite movie saved", Toast.LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.btn_show_trailer)
    public void onShowTrailersBtnClick(View view) {

        Intent intent = makeIntentWithParcelableData(
                        this, TrailerActivity.class, movie);
        startActivity(intent);
    }

    private void ifFavoriteHideBtn() {

        final DetailActivity detailActivity = this;

        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Movie favoriteMovie = AppDatabase.getsIntance(detailActivity.getApplicationContext())
                        .movieDAO().getMovie(movie.getId());

                detailActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (favoriteMovie == null) return;

                        //hide favorite btn
                        if (favoriteMovie.getId() == movie.getId()) {
                            favoriteBtn.setVisibility(View.INVISIBLE);
                            isFavorite = true;
                        }
                    }
                });

            }
        });


    }

    private void hideViews() {
        loadReviewBtn.setVisibility(View.GONE);
        reviewLabelTv.setVisibility(View.VISIBLE);
    }

    private void downloadMoviePoster() {

        FileOutputStream foStream;
        String posterName = movie.getPosterPath().substring(1,
                movie.getPosterPath().indexOf("."));
        try {
            Bitmap bitmap = Picasso.with(this)
                    .load(mPosterPath)
                    .get();

            foStream = getApplicationContext().openFileOutput(
                    posterName, Context.MODE_PRIVATE);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);

            foStream.close();

        }catch (IOException ioe) {
            Log.e(TAG, "image download failed...", ioe);
            Toast.makeText(getApplicationContext(),
                    "Error Occur while downloading poster", Toast.LENGTH_LONG).show();

        }

    }

}
