package com.nst.cropio.yield.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


public class FocusableWebView extends WebView {

    public FocusableWebView(Context context) {
        this(context, null);
    }

    public FocusableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }
}
