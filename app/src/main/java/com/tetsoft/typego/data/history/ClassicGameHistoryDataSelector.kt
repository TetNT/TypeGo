package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.game.GameResult

class ClassicGameHistoryDataSelector(private val classicGameModesHistoryList: ClassicGameModesHistoryList) {

    fun getBestResult() : GameResult.Classic {
        if (classicGameModesHistoryList.isEmpty()) return GameOnTime.Empty()
        return classicGameModesHistoryList.maxBy { result -> result.getWpm() }
    }

    fun getMostRecentResult() : GameResult.Classic {
        if (classicGameModesHistoryList.isEmpty()) return GameOnTime.Empty()
        return classicGameModesHistoryList.maxBy { result -> result.getCompletionDateTime() }
    }

    fun getResultsByLanguage(languageCode: String) : List<GameResult.Classic> {
        return classicGameModesHistoryList.filter { it.getLanguageCode() == languageCode }
    }
}