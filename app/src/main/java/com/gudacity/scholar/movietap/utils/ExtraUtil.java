package com.gudacity.scholar.movietap.utils;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public final class ExtraUtil {

    private ExtraUtil() {}

    public static int calculateBestSpanCount(int posterWidth, WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

}
