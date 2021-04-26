package com.example.typego.utils;


import android.content.Context;
import android.content.res.Resources;

import com.example.typego.R;

import org.jetbrains.annotations.NotNull;

public class TimeConvert {

    public static String convertSecondsToStamp(int seconds) {
        if (seconds < 1) return "00:00";
        int remainingSeconds = seconds;
        int remainingMinutes = remainingSeconds/60;
        remainingSeconds -= remainingMinutes * 60;
        String convertedSeconds = convertTimeValueToString(remainingSeconds);
        String convertedMinutes = convertTimeValueToString(remainingMinutes);
        return convertedMinutes + ":" + convertedSeconds;
    }

    public static String convertSeconds(@NotNull Context context, int seconds) {
        Resources res = context.getResources();
        int remainingSeconds = seconds;
        int remainingMinutes = remainingSeconds/60;
        remainingSeconds -= remainingMinutes * 60;
        String convertedTime =
                remainingMinutes+" "+ res.getString(R.string.test_setup_minutes);
        if (remainingSeconds > 0)
        convertedTime += " " + remainingSeconds + " " + res.getString(R.string.test_setup_seconds);
        return convertedTime;
    }

    private static String convertTimeValueToString(int timeValue) {
        if (timeValue < 10) return "0" + timeValue;
        else return "" + timeValue;
    }
}
