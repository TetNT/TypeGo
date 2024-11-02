package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords

class GameResultWithWordsInformation(
    private val wpm: Double, private val cpm: Int, private val timeSpent: Int, private val charsWritten: Int,
    private val completionDateTime: Long, private val wordsWritten: Int, private val correctWords: Int, private val incorrectWords: Int, private val gameSettings: GameSettings
) : GameResult, GameResult.WithWordsInformation {
    constructor(rw: RandomWords) : this(rw.getWpm(), rw.getCpm(), rw.getTimeSpent(), rw.getCharsWritten(), rw.getCompletionDateTime(), rw.getWordsWritten(), rw.getCorrectWords(), rw.getIncorrectWords(), rw.toSettings())
    constructor(ot: OwnText) : this(ot.getWpm(), ot.getCpm(), ot.getTimeSpent(), ot.getCharsWritten(), ot.getCompletionDateTime(), ot.getWordsWritten(), ot.getCorrectWords(), ot.getIncorrectWords(), ot.toSettings())

    override fun getWpm() = wpm
    override fun getCpm() = cpm
    override fun getTimeSpent() = timeSpent
    override fun getCharsWritten() = charsWritten
    override fun areSuggestionsActivated() = gameSettings.suggestionsActivated
    override fun getScreenOrientation() = gameSettings.screenOrientation
    override fun ignoreCase() = gameSettings.ignoreCase
    override fun getCompletionDateTime() = completionDateTime
    override fun toSettings() = gameSettings
    override fun getWordsWritten() = wordsWritten
    override fun getCorrectWords() = correctWords
    override fun getIncorrectWords() = incorrectWords

}