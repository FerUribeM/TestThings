package com.ferbajoo.testthings;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;


/**
 * Created by
 *          feuribe on 01/12/2017.
 */

public class Utilities {

    public static void getToolbar(AppCompatActivity activity, Toolbar toolbar, String title) {
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(title);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setTitleTextColor(ContextCompat.getColor(activity, android.R.color.white));
        }
    }

    /**
     * @param context get current fragment or activity
     * @return true if detect device in landscape
     */
    public static boolean isLanscape(Context context) {
        Display getOrient = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        boolean orientation;
        Point size = new Point();
        getOrient.getSize(size);
        int width = size.x;
        int height = size.y;
        orientation = width != height && width >= height;
        return orientation;
    }

}
