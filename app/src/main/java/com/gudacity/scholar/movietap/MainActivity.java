package com.gudacity.scholar.movietap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;

import com.gudacity.scholar.movietap.utils.MovieRequestAsyncThread;
import com.gudacity.scholar.movietap.utils.PathBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rc_movie);

        MovieRequestAsyncThread movieRequest = new MovieRequestAsyncThread(this, recyclerView );
        movieRequest.execute(PathBuilder.getMoviePath(isPopular));

    }


}
