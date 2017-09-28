package com.nst.cropio.yield.view;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class RobotoFontEditText extends AppCompatEditText {
    private static final String sScheme =
            "http://schemas.android.com/apk/res-auto";
    private static final String sAttribute = "et_robotoFont";

    public RobotoFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        final String fontName = attrs.getAttributeValue(sScheme, sAttribute);

        if (fontName == null) {
            throw new IllegalArgumentException("You must provide \"" + sAttribute + "\" for your text view");
        } else {
            final Typeface customTypeface = RobotoFont.fromString(fontName).asTypeface(context);
            setTypeface(customTypeface);
        }
    }
}
