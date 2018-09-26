package com.gudacity.scholar.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.gudacity.scholar.movietap.VideoUtil.YouTubeVideoLoader;
import com.gudacity.scholar.movietap.utils.JsonParser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerYoutubePalyerActivity extends YouTubeBaseActivity {

    private static final String TAG = TrailerYoutubePalyerActivity.class.getSimpleName();

    @BindView(R.id.ytp_player)
    public YouTubePlayerView playerView;
    private String trailerKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_youtube_palyer);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra(JsonParser.REVIEW_KEY)) {
            trailerKey = intent.getStringExtra(JsonParser.REVIEW_KEY);
        }

        Log.i(TAG, "play trailer ID: "+ trailerKey);
        YouTubeVideoLoader.playTrailer(this, trailerKey, playerView);

    }


}
