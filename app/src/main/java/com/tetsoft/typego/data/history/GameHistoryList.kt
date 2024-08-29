package com.tetsoft.typego.data.history

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.game.GameSettings
import com.tetsoft.typego.data.game.OwnText
import com.tetsoft.typego.data.game.RandomWords

abstract class GameHistoryList<T : GameResult> : ArrayList<T> {
    constructor()
    constructor(list: List<T>) {
        this.addAll(list)
    }
}

class GameOnTimeHistoryList : GameHistoryList<RandomWords> {
    constructor()
    constructor(list: List<RandomWords>) : super(list)
}

class OwnTextGameHistoryList : GameHistoryList<OwnText> {
    constructor()
    constructor(list: List<OwnText>) : super(list)
}

interface GameHistory {
    fun getAllResults() : List<GameResult>
    fun getResultsWithLanguage() : List<GameResultWithLanguage>
    fun getResultsWithWordsInformation() : List<GameResultWithWordsInformation>
    fun getTimeLimitedResults() : List<GameResult.TimeLimited>
    fun getBestResult() : GameResult
    fun getMostRecentResult() : GameResult

    class GameResultWithWordsInformation(
        private val wpm: Double, private val cpm: Int, private val timeSpent: Int, private val charsWritten: Int,
        private val completionDateTime: Long, private val wordsWritten: Int, private val correctWords: Int, private val incorrectWords: Int, private val gameSettings: GameSettings) : GameResult, GameResult.WithWordsInformation {
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

    class GameResultWithLanguage(
        private val wpm: Double, private val cpm: Int, private val timeSpent: Int, private val charsWritten: Int,
        private val completionDateTime: Long, private val languageCode: String, private val dictionaryType: DictionaryType, private val gameSettings: GameSettings) : GameResult, GameResult.WithLanguage {
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

    open class Standard(randomWordsHistoryList: List<RandomWords>, ownTextHistoryList: List<OwnText>) : GameHistory {

        private val allResults = ArrayList<GameResult>()

        init {
            allResults.addAll(randomWordsHistoryList)
            allResults.addAll(ownTextHistoryList)
            allResults.sortBy { it.getCompletionDateTime() }
        }

        override fun getAllResults(): List<GameResult> {
            return allResults
        }

        override fun getResultsWithLanguage(): List<GameResultWithLanguage> {
            val filteredList = ArrayList<GameResultWithLanguage>()
            allResults.forEach {
                if (it is RandomWords) {
                    filteredList.add(GameResultWithLanguage(it))
                }
            }
            return filteredList
        }

        override fun getResultsWithWordsInformation(): List<GameResultWithWordsInformation> {
            val filteredList = ArrayList<GameResultWithWordsInformation>()
            allResults.forEach {
                if (it is RandomWords) {
                    filteredList.add(GameResultWithWordsInformation(it))
                } else if (it is OwnText) {
                    filteredList.add(GameResultWithWordsInformation(it))
                }
            }
            return filteredList
        }

        override fun getTimeLimitedResults(): List<GameResult.TimeLimited> {
            val filteredList = ArrayList<GameResult.TimeLimited>()
            allResults.forEach {
                if (it is GameResult.TimeLimited) {filteredList.add(it)}
            }
            return filteredList
        }

        override fun getBestResult(): GameResult {
            return allResults.maxByOrNull { result -> result.getWpm() } ?: GameResult.Empty()
        }

        override fun getMostRecentResult(): GameResult {
            return allResults.maxByOrNull { result -> result.getCompletionDateTime() } ?: GameResult.Empty()
        }
    }
}