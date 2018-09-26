package com.gudacity.scholar.movietap;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.gudacity.scholar.movietap.VideoUtil.YouTubeVideoLoader;
import com.gudacity.scholar.movietap.utils.MovieTrailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>
        implements RecyclerClickListener {

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    private TrailerActivity trailerActivity;
    private List<MovieTrailer> trailers;

    public TrailerAdapter(TrailerActivity trailerActivity, List<MovieTrailer> trailers) {
        this.trailers = trailers;
        this.trailerActivity = trailerActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_card, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MovieTrailer trailer = trailers.get(position);

        YouTubeVideoLoader.loadTrailerThumbnail(trailerActivity, trailerActivity.getApplicationContext(),
                holder.thumbnailView, trailer.getKey());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    @Override
    public void onItemClicked(int position) {
        String key = trailers.get(position).getKey();
        trailerActivity.launchYoutubePlayerActivity(key);

    }

    class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        YouTubeThumbnailView thumbnailView;
        RecyclerClickListener clickListener;

        public ViewHolder(View itemView, RecyclerClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbnailView = (YouTubeThumbnailView)itemView.findViewById(R.id.ytv_trailer);
            this.clickListener = listener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getAdapterPosition());
        }
    }

}
