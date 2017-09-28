package com.nst.cropio.yield.view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Locale;

/**
 * Created by yuriy on 9/28/17.
 */
//todo replace this class to the proper place
public enum RobotoFont {
    ROBOTO_MEDIUM("fonts/Roboto-Medium.ttf"),
    ROBOTO_REGULAR("fonts/Roboto-Regular.ttf"),
    ROBOTO_BOLD("fonts/Roboto-Bold.ttf");

    private final String fileName;

    RobotoFont(String fileName) {
        this.fileName = fileName;
    }

    static RobotoFont fromString(String fontName) {
        return RobotoFont.valueOf(fontName.toUpperCase(Locale.US));
    }

    public Typeface asTypeface(Context context) {

        // TODO: cache
        return Typeface.createFromAsset(context.getAssets(), fileName);
    }
}
