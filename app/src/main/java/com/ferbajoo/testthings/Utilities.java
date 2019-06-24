package com.ferbajoo.testthings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;


/**
 * Created by
 *          feuribe on 01/12/2017.
 */

public class Utilities {

    private static int REQUEST_CODE_PERMISSIONS = 300;


    public static final String[] PERMITION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    /**
     * @param context        current activity or fragment in app
     * @param permissions    arraylist of permission for app
     * @return true if have all permission
     */
    public static boolean verifyHavePermissions(AppCompatActivity context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            for (String permission : permissions)
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        createDialogPermissions(context);
                    } else {
                        ActivityCompat.requestPermissions(context, permissions, REQUEST_CODE_PERMISSIONS);
                    }
                    return false;
                }
        return true;
    }

    /**
     * create alert dialog active all permission
     *
     * @param activity       context app
     */
    private static void createDialogPermissions(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.warning)
                .setCancelable(false)
                .setMessage(R.string.permissions)
                .setPositiveButton(R.string.configuration, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
                    }
                }).create().show();
    }

}
