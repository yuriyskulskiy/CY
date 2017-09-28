package com.nst.cropio.yield.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nst.cropio.yield.R;
import com.nst.cropio.yield.network.LoginResponse;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    private static final String EXTRA_URL = "url";
    static final int REQUEST_CODE = 1;

    public static final String SUCCESS_EXTRA = "success_extra";
    public static final String USER_API_TOKEN_EXTRA = "user_api_token";
    public static final String LANGUAGE_EXTRA = "language_extra";
    public static final String COMAPNY_EXTRA = "company_extra";


    public static void startWebActivity(String url, Context activity) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }

    public static void startWebActivityForResult(String url, FragmentActivity fActivity) {
        Intent intent = new Intent(fActivity, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        fActivity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_web);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.web_view);
        webView.setVerticalScrollBarEnabled(true);
        android.webkit.CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                // a callback which is executed when the cookies have been removed
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.e("tag", "onCreateDialog,onReceiveValue() ");
                    loadWebView(webView);
                }
            });
        } else {
            cookieManager.removeAllCookie();
            Log.e("tag", "onCreateDialog ");
            loadWebView(webView);
        }


        final View activityRootView = findViewById(R.id.root_view);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(WebActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                    //todo listener when keyboard popups
//                    Toast.makeText(WebActivity.this, "mazafaka", Toast.LENGTH_SHORT).show();
//                    webView.pageDown(true);
//                    webView.scrollTo(0, webView.getContentHeight());
//                    webView.scrollTo(0, heightDiff);
                }
            }
        });

    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
//        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.9);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO doesnt work
                super.onPageFinished(view, url);
//                WebActivity.this.webView.scrollTo(0,  300);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                String schema = uri.getScheme().toLowerCase();
                if (schema.equals("cropioapp")) {
//                    LoginResponse response = new LoginResponse();
//                    response.setSuccess(Boolean.parseBoolean(uri.getQueryParameter("success")));
//                    response.setCompany_name(uri.getQueryParameter("company"));
//                    response.setLanguage(uri.getQueryParameter("language"));
//                    response.setUser_api_token(uri.getQueryParameter("user_api_token"));
                    Intent intent = new Intent();

                    boolean successResult = Boolean.parseBoolean(uri.getQueryParameter("success"));
                    String companyName = uri.getQueryParameter("company");
                    String language = uri.getQueryParameter("language");
                    String userApiToken = uri.getQueryParameter("user_api_token");
                    putToIntentAgroinvestData(intent, successResult, companyName, language, userApiToken);

                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        webView.loadUrl(getIntent().getExtras().getString(EXTRA_URL));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static void putToIntentAgroinvestData(Intent i, Boolean success, String company, String language, String userApiToken) {
        i.putExtra(SUCCESS_EXTRA, success);
        i.putExtra(COMAPNY_EXTRA, company);
        i.putExtra(LANGUAGE_EXTRA, language);
        i.putExtra(USER_API_TOKEN_EXTRA, userApiToken);
    }

    public static LoginResponse getAgroinvestDataAsResponse(Intent intent) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(intent.getBooleanExtra(SUCCESS_EXTRA, false));
        response.setCompany_name(intent.getStringExtra(COMAPNY_EXTRA));
        response.setLanguage(intent.getStringExtra(LANGUAGE_EXTRA));
        response.setUser_api_token(intent.getStringExtra(USER_API_TOKEN_EXTRA));
        return response;
    }
}
