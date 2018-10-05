package com.toplin.handlersample.thread_communication.data;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class Time {

    private static DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.US);
    private static Calendar cal = Calendar.getInstance();

    private long time;

    public Time(long time) {
        this.time = time;
    }

    @NonNull
    public String convert() {
        cal.setTimeInMillis(time);
        return df.format(cal.getTime());
    }
}
