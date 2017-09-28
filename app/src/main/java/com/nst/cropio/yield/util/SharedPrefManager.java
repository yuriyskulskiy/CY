package com.nst.cropio.yield.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.nst.cropio.yield.YieldApplication;

/**
 * Created by yuriy on 9/28/17.
 */

public class SharedPrefManager {

    private static String IS_LOGGED_IN = "is_logged_in";
    private static final String USER_EMAIL = "user_email";
    private static final String COMPANY = "company";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String USER_ID = "user_id";

    private static SharedPrefManager sInstance;
    private static SharedPreferences mPreferences;

    private SharedPrefManager() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(YieldApplication.get());
    }

    public static SharedPrefManager get() {
        if (sInstance == null) {
            sInstance = new SharedPrefManager();
        }
        return sInstance;
    }

    public boolean isLoggedIn() {
        return getBoolean(IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        putBoolean(IS_LOGGED_IN, isLoggedIn);
    }

    public String getUserEmail() {
        return getString(USER_EMAIL, null);
    }

    @Nullable
    public String getCompany() {
        return getString(COMPANY, null);
    }

    public void addUserEmail(String email) {
        putString(USER_EMAIL, email);
    }

    public void addToken(String token) {
        putString(AUTH_TOKEN, token);
    }

    public void addUser_id(String user_id) {
        putString(USER_ID, user_id);
    }

    public String getUser_id() {
        return getString(USER_ID, null);
    }











    private void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }
}
