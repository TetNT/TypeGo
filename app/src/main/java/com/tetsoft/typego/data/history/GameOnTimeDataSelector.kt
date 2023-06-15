package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnTime

class GameOnTimeDataSelector(private val gameOnTimeHistoryList: GameHistoryList<GameOnTime>) {

    fun getBestResult() : GameOnTime {
        if (gameOnTimeHistoryList.isEmpty()) return GameOnTime.Empty()
        return gameOnTimeHistoryList.maxBy { result -> result.getWpm() }
    }

    fun getSecondToLastResult() : GameOnTime {
        if (gameOnTimeHistoryList.size < 2) return GameOnTime.Empty()
        return gameOnTimeHistoryList[gameOnTimeHistoryList.size - 2]
    }

    fun getMostRecentResult() : GameOnTime {
        if (gameOnTimeHistoryList.isEmpty()) return GameOnTime.Empty()
        return gameOnTimeHistoryList.maxBy { result -> result.getCompletionDateTime() }
    }

    fun getResultsByTimeMode(timeInSeconds: Int) : List<GameOnTime> {
        return gameOnTimeHistoryList.filter { it.getTimeSpent() == timeInSeconds }
    }
}