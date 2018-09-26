package com.gudacity.scholar.movietap;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.gudacity.scholar.movietap.VideoUtil.VideoThumbnailLoader;
import com.gudacity.scholar.movietap.utils.MovieTrailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>
        implements RecyclerClickListener {

    private static final String TAG = TrailerAdapter.class.getSimpleName();
    private Context context;
    private List<MovieTrailer> trailers;
    private final Activity activity;


    public TrailerAdapter(Activity activity, Context context, List<MovieTrailer> trailers) {
        this.context = context;
        this.trailers = trailers;
        this.activity = activity;

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

        VideoThumbnailLoader.initializeVideoThumbnail(activity, context,
                holder.thumbnailView, trailer.getKey());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    @Override
    public void onItemClicked(int position) {

        //start to play movie

    }

    class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public YouTubeThumbnailView thumbnailView;
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
