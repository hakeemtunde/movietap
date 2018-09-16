package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        populateUI(intent);
    }

    private void populateUI(Intent intent) {

        Bundle bundle = intent.getExtras();
        Movie movie = (Movie)bundle.getParcelable(MOVIE_PARCELABLE_KEY);

        titleTv.setText(movie.getTitle());
        voteTv.setText(String.valueOf(movie.getVoteAverage()));
        releaseDateTv.setText(movie.getDate());
        synopsisTv.setText(movie.getSynopsis());

        String posterPath = movie.getPosterPath();

        Picasso.with(this)
                .load(PathBuilder.buildPosterImagePath(posterPath))
                .into(moviePosterIv);
    }
}
