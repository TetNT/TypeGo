package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.history.domain.GameHistory
// TODO: JUnits
open class GameHistoryImpl(randomWordsHistoryList: List<RandomWords>, ownTextHistoryList: List<OwnText>) : GameHistory {

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
            if (it is GameResult.TimeLimited) {
                filteredList.add(it)
            }
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