package com.ferbajoo.testthings;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


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

}
