package com.tetsoft.typego.testing

import com.tetsoft.typego.data.*
import java.util.*

data class TestResult (
    val correctWords: Int,
    val correctWordsWeight: Int,
    val totalWords: Int,
    val language: Language,
    val completionDateTime: Date,
    val dictionaryType: DictionaryType,
    val screenOrientation: ScreenOrientation,
    val timeMode: TimeMode,
    val textSuggestions: Boolean,
    val wordList : ArrayList<Word> = ArrayList()
) {
    val incorrectWords = totalWords - correctWords

    val wpm : Double = try {
        60.0 / timeMode.timeInSeconds * (correctWordsWeight / DIVISION)
    } catch (e: ArithmeticException) {
        0.0
    }

    companion object {
        // division is a common divider to calculate WPM despite words length.
        const val DIVISION = 4.0
    }
}