package com.tetsoft.typego.game

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation

interface GameResult {
    fun getWpm(): Double

    fun getCpm(): Int

    fun getTimeSpent(): Int

    fun getCharsWritten(): Int

    fun areSuggestionsActivated() : Boolean

    fun getScreenOrientation() : ScreenOrientation

    fun getCompletionDateTime(): Long

    interface WithLanguage {
        fun getLanguageCode(): String

        fun getDictionaryType() : DictionaryType
    }

    interface WithWordsInformation {
        fun getWordsWritten(): Int

        fun getCorrectWords(): Int

        fun getIncorrectWords(): Int
    }

    interface Classic : GameResult, WithLanguage, WithWordsInformation

}

open class GameOnTime(
    private val wpm: Double,
    private val cpm: Int,
    private val charsWritten: Int,
    private val chosenTime: Int,
    private val language: String,
    private val dictionary: String,
    private val screenOrientation: String,
    private val suggestionsActivated: Boolean,
    private val wordsWritten: Int,
    private val correctWords: Int,
    private val completionDateTime: Long
) : GameResult.Classic {

    override fun getWpm(): Double {
        return wpm
    }

    override fun getCpm(): Int {
        return cpm
    }

    override fun getTimeSpent(): Int {
        return chosenTime
    }

    override fun getCharsWritten(): Int {
        return charsWritten
    }

    override fun getCompletionDateTime(): Long {
        return completionDateTime
    }

    override fun getLanguageCode(): String {
        return language
    }

    override fun getDictionaryType(): DictionaryType {
        return enumValueOf(dictionary)
    }

    override fun getScreenOrientation(): ScreenOrientation {
        return enumValueOf(screenOrientation)
    }

    override fun areSuggestionsActivated(): Boolean {
        return suggestionsActivated
    }

    override fun getWordsWritten(): Int {
        return wordsWritten
    }

    override fun getCorrectWords(): Int {
        return correctWords
    }

    override fun getIncorrectWords(): Int {
        return wordsWritten - correctWords
    }

    class Empty : GameOnTime(0.0, 0, 0, 0, "", "", "", false, 0, 0, 0L)
}

open class GameOnNumberOfWords(
    private val wpm: Double,
    private val cpm: Int,
    private val charsWritten: Int,
    private val timeSpentInSeconds: Int,
    private val amountOfWords: Int,
    private val language: String,
    private val dictionary: String,
    private val screenOrientation: String,
    private val suggestionsActivated: Boolean,
    private val wordsWritten: Int,
    private val correctWords: Int,
    private val completionDateTime: Long
) : GameResult.Classic {
    override fun getWpm(): Double {
        return wpm
    }

    override fun getCpm(): Int {
        return cpm
    }

    override fun getTimeSpent(): Int {
        return timeSpentInSeconds
    }

    override fun getCharsWritten(): Int {
        return charsWritten
    }

    override fun getCompletionDateTime(): Long {
        return completionDateTime
    }

    override fun getLanguageCode(): String {
        return language
    }

    fun getAmountOfWords(): Int {
        return amountOfWords
    }

    override fun getDictionaryType(): DictionaryType {
        return enumValueOf(dictionary)
    }

    override fun getScreenOrientation(): ScreenOrientation {
        return enumValueOf(screenOrientation)
    }

    override fun areSuggestionsActivated(): Boolean {
        return suggestionsActivated
    }

    override fun getWordsWritten(): Int {
        return wordsWritten
    }

    override fun getCorrectWords(): Int {
        return correctWords
    }

    override fun getIncorrectWords(): Int {
        return wordsWritten - correctWords
    }
}