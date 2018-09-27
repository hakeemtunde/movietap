package com.gudacity.scholar.movietap;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gudacity.scholar.movietap.utils.MovieReview;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<MovieReview> movieReviews;

    public ReviewAdapter(List<MovieReview> reviews) {
        this.movieReviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieReview review = movieReviews.get(position);
        holder.authorTv.setText(review.getAuthor());
        holder.contentTv.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView authorTv;
        public TextView contentTv;

        public ViewHolder(View view) {
            super(view);
            authorTv = (TextView)view.findViewById(R.id.tv_author);
            contentTv = (TextView)view.findViewById(R.id.tv_content);
        }
    }
}
