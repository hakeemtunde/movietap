package com.gudacity.scholar.movietap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
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

public class DetailActivity extends AbstractActivityAction {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE_PARCELABLE_KEY = "movie";

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

    private Movie movie;
    private MovieRepo movieRepo;
    private String mPosterPath;
    private boolean isFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        movieRepo = new MovieRepo(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        movie = (Movie)bundle.getParcelable(MOVIE_PARCELABLE_KEY);
        mPosterPath = PathBuilder.buildPosterImagePath(movie.getPosterPath());

        //isFavoriteMovie
        ifFavoriteHideBtn();

        populateUI();

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
        MovieRequestAsyncThread movieRequestAsyncThread =
                new MovieRequestAsyncThread(this);
        movieRequestAsyncThread.execute(criteria);
    }

    @Override
    public void LoadData(String data) {

        List<MovieReview> movieReviews = JsonParser.parseMovieReview(data);

        if (movieReviews.size() == 0 ) {
            reviewLabelTv.setText("No Reviews");
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter adapter = new ReviewAdapter(movieReviews);
        recyclerView.setAdapter(adapter);
        hideViews();
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

        //Picasso.get().load(new File(...)).into(imageView3);
        FileOutputStream foStream;
        String posterName = movie.getPosterPath().substring(1,
                movie.getPosterPath().indexOf("."));
        try {
            Bitmap bitmap = Picasso.with(this)
                    .load(mPosterPath)
                    .get();

            foStream = getApplicationContext().openFileOutput(
                    posterName, getApplicationContext().MODE_PRIVATE);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);

            foStream.close();

        }catch (IOException ioe) {
            Log.e(TAG, "image download failed...", ioe);
            Toast.makeText(getApplicationContext(),
                    "Error Occur while downloading poster", Toast.LENGTH_LONG).show();

        }

    }


}
