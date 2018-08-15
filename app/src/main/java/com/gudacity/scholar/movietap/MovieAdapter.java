package com.gudacity.scholar.movietap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gudacity.scholar.movietap.utils.Movie;
import com.gudacity.scholar.movietap.utils.PathBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

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

        ViewHolder viewHolder = new ViewHolder(view);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView posterImageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            posterImageView = (ImageView)view.findViewById(R.id.list_item_poster);
        }
    }
}
