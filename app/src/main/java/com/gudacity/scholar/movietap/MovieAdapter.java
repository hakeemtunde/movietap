package com.gudacity.scholar.movietap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gudacity.scholar.movietap.utils.Movie;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(List<Movie> movies, @NonNull Context context) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(null, parent, false);
        }



        return convertView;
    }
}
