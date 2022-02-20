package com.tetsoft.typego.testing

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.Language
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.TimeMode
import java.lang.ArithmeticException
import java.util.*

class TypingResult(
    val correctWords: Int,
    val correctWordsWeight: Int,
    val totalWords: Int,
    dictionaryType: DictionaryType,
    screenOrientation: ScreenOrientation,
    language: Language,
    timeMode: TimeMode,
    textSuggestions: Boolean,
    completionDateTime: Date
) {
    val wpm: Double
    var dictionaryType = 0
    var screenOrientation = 0
    val language: Language
    val timeInSeconds: Int
    val completionDateTime: Date
    val isTextSuggestions: Boolean
    val incorrectWords: Int
        get() = totalWords - correctWords


    companion object {
        const val DIVISION_POWER = 4.0
    }

    private fun calculateWpm(): Double {

        val wpm: Double = try {
            60.0 / timeInSeconds * (correctWordsWeight / DIVISION_POWER)
        } catch (ae: ArithmeticException) {
            0.0
        }
        return wpm
    }

    init {
        if (dictionaryType === DictionaryType.BASIC) this.dictionaryType =
            0 else this.dictionaryType = 1
        if (screenOrientation === ScreenOrientation.PORTRAIT) this.screenOrientation =
            0 else this.screenOrientation = 1
        this.language = language
        timeInSeconds = timeMode.timeInSeconds
        isTextSuggestions = textSuggestions
        this.completionDateTime = completionDateTime
        wpm = calculateWpm()
    }
}