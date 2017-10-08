package com.nst.cropio.yield.util;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yuriy on 10/6/17.
 */

public class LocaleCalendarH extends CalendarHandler {

    LocaleCalendarH() {}

    public static SimpleDateFormat mmmDateFormat;
    public static SimpleDateFormat mmmDateFormat2;
    public static SimpleDateFormat weatherDateFormat;
    public static SimpleDateFormat weatherDateFormat2;

    public static SimpleDateFormat reportDateFormat;
    public static SimpleDateFormat operationDateFormat;
    public static SimpleDateFormat operationTimeFromat;
    public static SimpleDateFormat operationCompletedDatetimeFromat;
    public static SimpleDateFormat weatherDateFormatDayOfWeek;
    public static SimpleDateFormat weatherDateTimeFormat;

    public static SimpleDateFormat syncDateFormat;

    static {
        initialize();
    }

    public static void initialize() {
        Locale locale = Locale.getDefault();
        CalendarHandler.initialize(locale);
        mmmDateFormat =
                new SimpleDateFormat("dd MMM yyyy", locale);

        mmmDateFormat2 =
                new SimpleDateFormat("dd MMM", locale);

        weatherDateFormat =
                new SimpleDateFormat("dd MMM, EE", locale);

        weatherDateFormat2 =
                new SimpleDateFormat("kk:mm, dd MMM", locale);

        reportDateFormat =
                new SimpleDateFormat("dd MMM yyyy, kk:mm", locale);

        operationDateFormat =
                new SimpleDateFormat("dd MMM, yyyy", locale);

        operationTimeFromat =
                new SimpleDateFormat("kk:mm, dd MMM yyyy", locale);

        operationCompletedDatetimeFromat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ", locale);

        weatherDateFormatDayOfWeek =
                new SimpleDateFormat("EEEE", locale);

        weatherDateTimeFormat =
                new SimpleDateFormat("kk:mm", locale);

        syncDateFormat =
                new SimpleDateFormat("dd.MM.yyyy", locale);
    }

//    @BindingAdapter("bind:formatSimpleStringDate")
//    public static void formatString(TextView view, String string){
//        String result;
//        if(string != null && !string.equals("")) {
//            result = formatString(string, simpleDateFormat, mmmDateFormat);
//        }else{
//            result = string;
//        }
//        view.setText(result);
//    }
//
//    @BindingAdapter("bind:formatDate")
//    public static void formatString(TextView view, Date date){
//        String result;
//        if(date != null) {
//            result = formatDate(date, mmmDateFormat);
//        }else{
//            result = "";
//        }
//        view.setText(result);
//    }
//
//    @BindingAdapter("bind:formatSimpleStringDateCompleted")
//    public static void formatStringDateCompleted(TextView view, String string){
//        String result;
//        if (string != null && !string.equals("")) {
//            result = formatString(string, simpleDateFormat, mmmDateFormat);
//        } else {
//            result = string;
//        }
//        view.setText(String.format(Locale.getDefault(), "%s %s",
//                view.getContext().getResources().getString(R.string.completed), result));
//    }
}
