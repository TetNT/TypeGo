package com.tetsoft.typego.data

import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.game.GameOnTime

class OldResultsToNewMigration(private val oldResults: List<com.tetsoft.typego.game.result.GameResult>) {
    fun getNewResultsList() : GameOnTimeHistoryList {
        val newHistory = GameOnTimeHistoryList()
        for (oldResult in oldResults) {
            val gameMode = oldResult.gameMode as com.tetsoft.typego.game.mode.GameOnTime
            newHistory.add(
                GameOnTime(
                    oldResult.wpm,
                    oldResult.cpm,
                    oldResult.score,
                    gameMode.timeMode.timeInSeconds,
                    gameMode.getLanguage().identifier,
                    gameMode.getDictionary().name,
                    gameMode.screenOrientation.name,
                    gameMode.suggestionsActivated,
                    oldResult.wordsWritten,
                    oldResult.correctWords,
                    oldResult.completionDateTime
                )
            )
        }
        return newHistory
    }
}