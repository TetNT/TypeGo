package com.tetsoft.typego.data.game

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation

interface GameResult {
    fun getWpm(): Double

    fun getCpm(): Int

    fun getTimeSpent(): Int

    fun getCharsWritten(): Int

    fun areSuggestionsActivated(): Boolean

    fun getScreenOrientation(): ScreenOrientation

    fun ignoreCase() : Boolean

    fun getCompletionDateTime(): Long

    fun toSettings(): GameSettings

    interface WithLanguage {
        fun getLanguageCode(): String

        fun getDictionaryType(): DictionaryType
    }

    interface WithWordsInformation {
        fun getWordsWritten(): Int

        fun getCorrectWords(): Int

        fun getIncorrectWords(): Int
    }

    interface RandomlyGenerated {
        fun getSeed(): String
    }

    interface TimeLimited {
        fun getChosenTimeInSeconds() : Int
    }

    class Empty : GameResult {
        override fun getWpm() = 0.0
        override fun getCpm() = 0
        override fun getTimeSpent() = 0
        override fun getCharsWritten() = 0
        override fun areSuggestionsActivated() = false
        override fun getScreenOrientation() = ScreenOrientation.PORTRAIT
        override fun ignoreCase() = true
        override fun getCompletionDateTime() = 0L
        override fun toSettings(): GameSettings {
            throw IllegalStateException("No GameSettings for an empty result")
        }
    }
}

open class RandomWords(
    private val wpm: Double,
    private val cpm: Int,
    private val charsWritten: Int,
    private val chosenTime: Int,
    private val language: String,
    private val dictionary: String,
    private val screenOrientation: String,
    private val suggestionsActivated: Boolean,
    private val ignoreCase: Boolean?,
    private val wordsWritten: Int,
    private val correctWords: Int,
    private val completionDateTime: Long,
    private val seed: String?
) : GameResult, GameResult.TimeLimited, GameResult.WithLanguage, GameResult.WithWordsInformation, GameResult.RandomlyGenerated {

    constructor(
        wpm: Double,
        cpm: Int,
        charsWritten: Int,
        chosenTime: Int,
        language: String,
        dictionary: String,
        screenOrientation: String,
        suggestionsActivated: Boolean,
        ignoreCase: Boolean,
        seed: String
    ) : this(
        wpm,
        cpm,
        charsWritten,
        chosenTime,
        language,
        dictionary,
        screenOrientation,
        suggestionsActivated,
        ignoreCase,
        0,
        0,
        0L,
        seed
    )

    constructor(
        wpm: Double,
        cpm: Int,
        charsWritten: Int,
        chosenTime: Int,
        language: String,
        dictionary: String,
        screenOrientation: String,
        suggestionsActivated: Boolean,
        ignoreCase: Boolean,
        wordsWritten: Int,
        correctWords: Int,
        completionDateTime: Long,
    ) : this(
        wpm,
        cpm,
        charsWritten,
        chosenTime,
        language,
        dictionary,
        screenOrientation,
        suggestionsActivated,
        ignoreCase,
        wordsWritten,
        correctWords,
        completionDateTime,
        ""
    )

    override fun getWpm() = wpm

    override fun getCpm() = cpm

    override fun getTimeSpent() = chosenTime

    override fun getCharsWritten() = charsWritten

    override fun getCompletionDateTime() = completionDateTime

    override fun getLanguageCode() = language

    override fun getDictionaryType() = enumValueOf<DictionaryType>(dictionary)

    override fun getScreenOrientation() = enumValueOf<ScreenOrientation>(screenOrientation)

    override fun areSuggestionsActivated() =  suggestionsActivated

    override fun ignoreCase() = ignoreCase ?: true

    override fun getWordsWritten() = wordsWritten

    override fun getCorrectWords() = correctWords

    override fun getIncorrectWords() = wordsWritten - correctWords

    override fun getChosenTimeInSeconds() = chosenTime

    override fun toSettings(): GameSettings {
        return GameSettings.ForRandomlyGeneratedWords(
            language,
            chosenTime,
            getDictionaryType(),
            getSeed(),
            suggestionsActivated,
            getScreenOrientation(),
            ignoreCase()
        )
    }

    override fun getSeed() = seed ?: ""

    class Empty : RandomWords(0.0, 0, 0, 0, "", "", "", false, true, 0, 0, 0L, "")
}

open class OwnText(
    private val text: String,
    private val wpm: Double,
    private val cpm: Int,
    private val timeChosen: Int,
    private val timeSpent: Int,
    private val charsWritten: Int,
    private val suggestionsActivated: Boolean,
    private val screenOrientation: String,
    private val ignoreCase: Boolean?,
    private val completionDateTime: Long,
    private val wordsWritten: Int,
    private val correctWords: Int
) : GameResult, GameResult.TimeLimited, GameResult.WithWordsInformation {
    fun getText(): String = text

    override fun getWpm(): Double = wpm

    override fun getCpm(): Int = cpm

    override fun getTimeSpent(): Int = timeSpent

    override fun getCharsWritten(): Int = charsWritten

    override fun areSuggestionsActivated(): Boolean = suggestionsActivated

    override fun getScreenOrientation(): ScreenOrientation = enumValueOf(screenOrientation)

    override fun ignoreCase() = ignoreCase ?: true

    override fun getCompletionDateTime(): Long = completionDateTime

    override fun getWordsWritten(): Int = wordsWritten

    override fun getCorrectWords(): Int = correctWords

    override fun getIncorrectWords(): Int = wordsWritten - correctWords

    override fun getChosenTimeInSeconds() = timeChosen

    override fun toSettings(): GameSettings {
        return GameSettings.ForUserText(getText(), timeChosen, suggestionsActivated, getScreenOrientation(), ignoreCase())
    }

    class Empty : OwnText("", 0.0, 0, 0,0,  0, false, "", false, 0L, 0, 0)

}