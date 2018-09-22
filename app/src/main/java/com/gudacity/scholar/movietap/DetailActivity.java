package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gudacity.scholar.movietap.utils.JsonParser;
import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.MovieReview;
import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        populateUI(intent);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateUI(Intent intent) {

        Bundle bundle = intent.getExtras();
        movie = (Movie)bundle.getParcelable(MOVIE_PARCELABLE_KEY);

        titleTv.setText(movie.getTitle());
        voteTv.setText(String.valueOf(movie.getVoteAverage()));
        releaseDateTv.setText(movie.getDate());
        synopsisTv.setText(movie.getSynopsis());

        String posterPath = movie.getPosterPath();

        Picasso.with(this)
                .load(PathBuilder.buildPosterImagePath(posterPath))
                .into(moviePosterIv);
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

    }

    private void hideViews() {
        loadReviewBtn.setVisibility(View.GONE);
        reviewLabelTv.setVisibility(View.VISIBLE);
    }

}
