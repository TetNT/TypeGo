package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.RandomWords

class GameResultWithLanguage(
    private val wpm: Double, private val cpm: Int, private val timeSpent: Int, private val charsWritten: Int,
    private val completionDateTime: Long, private val languageCode: String, private val dictionaryType: DictionaryType, private val gameSettings: GameSettings
) : GameResult, GameResult.WithLanguage {
    constructor(rw: RandomWords) : this(rw.getWpm(), rw.getCpm(), rw.getTimeSpent(), rw.getCharsWritten(), rw.getCompletionDateTime(), rw.getLanguageCode(), rw.getDictionaryType(), rw.toSettings())

    override fun getWpm() = wpm
    override fun getCpm() = cpm
    override fun getTimeSpent() = timeSpent
    override fun getCharsWritten() = charsWritten
    override fun areSuggestionsActivated() = gameSettings.suggestionsActivated
    override fun getScreenOrientation() = gameSettings.screenOrientation
    override fun ignoreCase() = gameSettings.ignoreCase
    override fun getCompletionDateTime() = completionDateTime
    override fun toSettings() = gameSettings
    override fun getLanguageCode() = languageCode
    override fun getDictionaryType() = dictionaryType

}