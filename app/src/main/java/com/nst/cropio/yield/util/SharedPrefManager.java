package com.nst.cropio.yield.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.nst.cropio.yield.YieldApplication;

/**
 * Created by yuriy on 9/28/17.
 */

public class SharedPrefManager {

    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String USER_EMAIL = "user_email";
    private static final String COMPANY = "company";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String USER_ID = "user_id";
    private static final String LANGUAGE = "language";


    private static SharedPrefManager sInstance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(YieldApplication.get());
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

    public void addUserEmail(String email) {
        putString(USER_EMAIL, email);
    }


    @Nullable
    public String getCompany() {
        return getString(COMPANY, null);
    }

    public void addCompany(String company) {
        putString(COMPANY, company);
    }


    public void addToken(String token) {
        putString(AUTH_TOKEN, token);
    }

    public String getAuthToken() {
        return getByKey(AUTH_TOKEN);
    }


    public void addUser_id(String user_id) {
        putString(USER_ID, user_id);
    }

    public String getUser_id() {
        return getString(USER_ID, null);
    }


    public void addLanguage(String language) {
        putString(LANGUAGE, language);
    }

    public String getLanguage() {
        String language = getByKey(LANGUAGE);
        if (language.equals("en")
                || language.equals("es")
                || language.equals("ru")
                || language.equals("uk")
                || language.equals("hu")
                || language.equals("de")) {
            return language;
        } else {
            return "en";
        }
    }

    private String getByKey(String key) {
        return sharedPreferences.getString(key, "en");
    }


    private void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
}
