package com.gudacity.scholar.movietap.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.gudacity.scholar.movietap.DetailActivity;
import com.gudacity.scholar.movietap.R;

public class ExtraUtil {

    public static int calculateBestSpanCount(int posterWidth, WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    public static Intent makeIntentWithParcelableData(Context context, Class<?> activity, Movie movie) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(DetailActivity.MOVIE_PARCELABLE_KEY, movie);
        return intent;

    }
}
