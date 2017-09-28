package com.nst.cropio.yield.util;

import android.util.Log;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarHandler {

    CalendarHandler() {
    }

    public static SimpleDateFormat simpleDateFormat;

    //clouds', fields', mounts' images for map
    public static SimpleDateFormat storageDateFormat;

    //use for  notes' dates when you save them or send to server
    public static final java.text.DateFormat ISO_8601_DATE_FORMAT = new ISO8601DateFormat();

    static void initialize(Locale locale) {
        simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd", locale);
        storageDateFormat =
                new SimpleDateFormat("yyyyMMdd", locale);
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String formatDate(Date date, DateFormat dateFormat) {
        if (date != null && dateFormat != null) {
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    public static Date parseDate(String date, DateFormat dateFormat) {
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            Log.e("tag",CalendarHandler.class+ "unable to parse date: " + date, e);
        }
        return new Date();
    }

    public static String formatString(String stringDate, SimpleDateFormat fromFormat, SimpleDateFormat toFormat) {
        Date date = CalendarHandler.parseDate(stringDate, fromFormat);
        stringDate = CalendarHandler.formatDate(date, toFormat);
        return stringDate;
    }



}
