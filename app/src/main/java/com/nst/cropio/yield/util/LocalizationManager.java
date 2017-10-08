package com.nst.cropio.yield.util;

import android.content.Context;

import com.nst.cropio.yield.YieldApplication;

import java.util.Locale;


public class LocalizationManager {

    private LocalizationManager() {
    }

    public static void setUserLanguage() {
        Context context = YieldApplication.get();
        setUserLanguage(context);
    }

    public static void setUserLanguage(Context context) {
        String language = SharedPrefManager.get().getLanguage();
        if (language != null) {
            setLanguage(language);
        }
    }

    private static void setLanguage(String languageToLoad) {
        if (languageToLoad.equals("ru")
                || languageToLoad.equals("en")
                || languageToLoad.equals("es")
                || languageToLoad.equals("uk")
                || languageToLoad.equals("hu")
                || languageToLoad.equals("de")) {
            Locale locale = new Locale(languageToLoad); //e.g "sv"
            Locale systemLocale = Locale.getDefault();
            if (systemLocale != null && systemLocale.equals(locale)) {
                return;
            }
            Locale.setDefault(locale);
            LocaleCalendarH.initialize();
            Context context = YieldApplication.get();
            android.content.res.Configuration config = context.getResources().getConfiguration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }
}
