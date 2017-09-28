package com.nst.cropio.yield.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by yuriy on 9/28/17.
 */

public class PermissionManager {

    public static final String STORAGE_CODE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String LOCATION_CODE = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int PERMISSIONS_REQUEST_STORAGE = 101;
    public static final int PERMISSIONS_REQUEST_LOCATION = 103;

    public static boolean checkOutPermission(Context activity, String code) {
        int res = activity.checkCallingOrSelfPermission(code);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void askPermission(Activity activity, String code) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                code)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    code)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                youCantDenyThis(code, activity);
            } else {
                youCantDenyThis(code, activity);
            }
        }
    }

    private static void youCantDenyThis(String code, Activity activity) {
        // No explanation needed, we can request the permission.
        int response = 0;
        switch (code) {
            case STORAGE_CODE:
                response = PERMISSIONS_REQUEST_STORAGE;
                break;
            case LOCATION_CODE:
                response = PERMISSIONS_REQUEST_LOCATION;
                break;
        }
        ActivityCompat.requestPermissions(activity,
                new String[]{code},
                response);
    }
}

