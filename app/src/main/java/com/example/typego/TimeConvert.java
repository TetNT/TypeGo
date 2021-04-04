package com.example.typego;


import android.content.Context;
import android.content.res.Resources;

public class TimeConvert {

    public static String convertSecondsToStamp(int seconds) {
        int remainingSeconds = seconds;
        int remainingMinutes = remainingSeconds/60;
        remainingSeconds -= remainingMinutes * 60;
        String convertedSeconds = convertTimeValueToString(remainingSeconds);
        String convertedMinutes = convertTimeValueToString(remainingMinutes);
        String convertedTime = convertedMinutes+":"+convertedSeconds;
        return convertedTime;
    }

    public static String convertSeconds(Context context, int seconds) {
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
