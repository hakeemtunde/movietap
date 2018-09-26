package com.gudacity.scholar.movietap.VideoUtil;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import static com.gudacity.scholar.movietap.BuildConfig.YOUTUBE_API_KEY;

public class YouTubeVideoLoader {

    public static void loadTrailerThumbnail(final Activity activity, final Context context,
                                                YouTubeThumbnailView thumbnailView,
                                                final String videoKey) {

        thumbnailView.initialize(YOUTUBE_API_KEY,
                new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(
                            YouTubeThumbnailView youTubeThumbnailView,
                            final YouTubeThumbnailLoader thumbnailLoader) {

                        thumbnailLoader.setVideo(videoKey);

                        thumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                //if thumbnail load successfully make proper cleanup
                                thumbnailLoader.release();

                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                                                         YouTubeThumbnailLoader.ErrorReason errorReason) {
                                //on error while trying to load
                                Toast.makeText(context, "Error occur while loading "
                                        + videoKey + " thumbnail", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onInitializationFailure(
                            YouTubeThumbnailView youTubeThumbnailView,
                            YouTubeInitializationResult youTubeInitializationResult) {

                        youTubeInitializationResult.getErrorDialog(activity, 1).show();

                    }
                });
    }

    public static void playTrailer(final Activity activity, final String trailerKey, final YouTubePlayerView playerView) {

        playerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean wasRestored) {

                if(!wasRestored) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.cueVideo(trailerKey);
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

                youTubeInitializationResult.getErrorDialog(activity, 1).show();

            }
        });
    }
}
