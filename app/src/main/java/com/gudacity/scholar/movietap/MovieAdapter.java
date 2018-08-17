package com.gudacity.scholar.movietap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
        implements RecyclerClickListener{

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Movie movie = movies.get(position);

        Picasso.with(context)
                .load(PathBuilder.buildPosterImagePath(movie.getPosterPath()))
                .into(viewHolder.posterImageView);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onItemClicked(int position) {

        //start new activity
        Movie movie = movies.get(position);
        Log.i(TAG, "onItemClicked: "+ movie.getTitle());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements RecyclerView.OnClickListener{

        public ImageView posterImageView;
        private RecyclerClickListener clickListener;

        public ViewHolder(@NonNull View view, RecyclerClickListener clickListener) {
            super(view);
            view.setOnClickListener(this);
            this.clickListener = clickListener;
            posterImageView = (ImageView)view.findViewById(R.id.list_item_poster);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getAdapterPosition());
        }
    }
}
