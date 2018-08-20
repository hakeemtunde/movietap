package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE_TITLE = "TITLE";
    public static final String MOVIE_POSTER_PATH = "POSTER_PATH";
    public static final String MOVIE_SYNOPSIS = "SYNOPSIS";
    public static final String RELEASE_DATE = "RELEASE_DATE";
    public static final String MOVIE_VOTES = "VOTES";

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

        ButterKnife.bind(this);

        Intent intent = getIntent();

        populateUI(intent);
    }

    private void populateUI(Intent intent) {

        title_tv.setText(intent.getStringExtra(MOVIE_TITLE));
        vote_tv.setText(String.valueOf(intent.getDoubleExtra(MOVIE_VOTES, 0)));
        release_date_tv.setText(intent.getStringExtra(RELEASE_DATE));
        synopsis_tv.setText(intent.getStringExtra(MOVIE_SYNOPSIS));

        String posterpath = intent.getStringExtra(MOVIE_POSTER_PATH);

        Picasso.with(this)
                .load(PathBuilder.buildPosterImagePath(posterpath))
                .into(movie_poster_iv);
    }
}
