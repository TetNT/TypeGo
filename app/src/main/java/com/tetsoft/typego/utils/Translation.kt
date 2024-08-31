package com.tetsoft.typego.utils

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode

/**
 * Handles the translation for the metadata of the game.
 */
class Translation(private val context: Context) {

    fun getAsEnabledDisabled(value: Boolean): String {
        return if (value == true)
            context.getString(R.string.enabled)
        else
            context.getString(R.string.disabled)
    }

    fun get(language: Language): String {
        return when (language.identifier) {
            "ALL" -> context.getString(R.string.ALL)
            "EN" -> context.getString(R.string.EN)
            "RU" -> context.getString(R.string.RU)
            "FR" -> context.getString(R.string.FR)
            "IT" -> context.getString(R.string.IT)
            "ES" -> context.getString(R.string.ES)
            "DE" -> context.getString(R.string.DE)
            "KR" -> context.getString(R.string.KR)
            "BG" -> context.getString(R.string.BG)
            "UA" -> context.getString(R.string.UA)
            "CZ" -> context.getString(R.string.CZ)
            "PL" -> context.getString(R.string.PL)
            else -> context.getString(R.string.undefined)
        }
    }

    fun get(dictionaryType: DictionaryType): String {
        return when (dictionaryType) {
            DictionaryType.BASIC -> context.getString(R.string.basic)
            DictionaryType.ENHANCED -> context.getString(R.string.enhanced)
        }
    }

    fun get(screenOrientation: ScreenOrientation): String {
        return when (screenOrientation) {
            ScreenOrientation.PORTRAIT -> context.getString(R.string.portrait)
            ScreenOrientation.LANDSCAPE -> context.getString(R.string.landscape)
        }
    }

    fun get(timeMode: TimeMode): String {
        var remainingSeconds = timeMode.timeInSeconds
        val remainingMinutes = remainingSeconds / 60
        remainingSeconds -= remainingMinutes * 60
        var convertedTimeStr =
            remainingMinutes.toString() + " " + context.resources.getString(R.string.test_setup_minutes)
        if (remainingSeconds > 0) convertedTimeStr += " $remainingSeconds " + context.resources.getString(
            R.string.test_setup_seconds
        )
        return convertedTimeStr
    }
}