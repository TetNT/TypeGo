package com.tetsoft.typego.history.domain

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.history.data.GameResultWithLanguage
import com.tetsoft.typego.history.data.GameResultWithWordsInformation

interface GameHistory {
    fun getAllResults() : List<GameResult>

    fun getResultsWithLanguage() : List<GameResultWithLanguage>

    fun getResultsWithWordsInformation() : List<GameResultWithWordsInformation>

    fun getTimeLimitedResults() : List<GameResult.TimeLimited>

    fun getBestResult() : GameResult

    fun getMostRecentResult() : GameResult
}