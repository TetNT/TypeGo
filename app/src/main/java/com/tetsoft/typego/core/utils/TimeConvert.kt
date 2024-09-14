package com.tetsoft.typego.core.utils

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

    private fun convertTimeValueToString(timeValue: Int): String {
        return if (timeValue < 10) "0$timeValue" else "" + timeValue
    }
}