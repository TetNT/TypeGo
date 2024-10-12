package com.tetsoft.typego.core.utils

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.TimeMode

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
            Language.EN -> context.getString(R.string.EN)
            Language.RU -> context.getString(R.string.RU)
            Language.FR -> context.getString(R.string.FR)
            Language.IT -> context.getString(R.string.IT)
            Language.ES -> context.getString(R.string.ES)
            Language.DE -> context.getString(R.string.DE)
            Language.BG -> context.getString(R.string.BG)
            Language.UA -> context.getString(R.string.UA)
            Language.CZ -> context.getString(R.string.CZ)
            Language.PL -> context.getString(R.string.PL)
            Language.PT -> context.getString(R.string.PT)
            Language.TR -> context.getString(R.string.TR)
            Language.ID -> context.getString(R.string.ID)
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