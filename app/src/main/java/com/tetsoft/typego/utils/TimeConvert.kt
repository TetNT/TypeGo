package com.tetsoft.typego.utils

import android.content.Context
import com.tetsoft.typego.utils.TimeConvert
import com.tetsoft.typego.R

object TimeConvert {
    @JvmStatic
    fun convertSecondsToStamp(seconds: Int): String {
        if (seconds < 1) return "0:00"
        var remainingSeconds = seconds
        val remainingMinutes = remainingSeconds / 60
        remainingSeconds -= remainingMinutes * 60
        val convertedSeconds = convertTimeValueToString(remainingSeconds)
        return "$remainingMinutes:$convertedSeconds"
    }

    @JvmStatic
    fun convertSeconds(context: Context, seconds: Int): String {
        val res = context.resources
        var remainingSeconds = seconds
        val remainingMinutes = remainingSeconds / 60
        remainingSeconds -= remainingMinutes * 60
        var convertedTime =
            remainingMinutes.toString() + " " + res.getString(R.string.test_setup_minutes)
        if (remainingSeconds > 0) convertedTime += " " + remainingSeconds + " " + res.getString(R.string.test_setup_seconds)
        return convertedTime
    }

    private fun convertTimeValueToString(timeValue: Int): String {
        return if (timeValue < 10) "0$timeValue" else "" + timeValue
    }
}