package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    TextView title_tv;
    @BindView(R.id.tv_release_date)
    TextView release_date_tv;
    @BindView(R.id.tv_vote)
    TextView vote_tv;
    @BindView(R.id.tv_synopsis)
    TextView synopsis_tv;
    @BindView(R.id.detail_movie_poster)
    ImageView movie_poster_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        populateUI(intent);
    }

    private void populateUI(Intent intent) {

        Bundle bundle = intent.getExtras();
        Movie movie = (Movie)bundle.getParcelable(MOVIE_PARCELABLE_KEY);

        title_tv.setText(movie.getTitle());
        vote_tv.setText(String.valueOf(movie.getVoteAverage()));
        release_date_tv.setText(movie.getDate());
        synopsis_tv.setText(movie.getSynopsis());

        String posterpath = movie.getPosterPath();

        Picasso.with(this)
                .load(PathBuilder.buildPosterImagePath(posterpath))
                .into(movie_poster_iv);
    }
}