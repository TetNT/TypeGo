package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResult

class AccuracyCalculation(private val resultsList : List<GameResult>) : StatisticsCalculation {

    override fun provide(): Int {
        if (resultsList.isEmpty()) return 0
        var wordsInTotal = 0
        var incorrectWordsCount = 0
        for (result in resultsList) {
            wordsInTotal += result.wordsWritten
            incorrectWordsCount += result.wordsWritten - result.correctWords
        }
        if (wordsInTotal == 0 && incorrectWordsCount > 0)
            return 0
        return if (incorrectWordsCount == 0 && wordsInTotal > 0)
            100
        else
            (100 - 100 * incorrectWordsCount / wordsInTotal)
    }
}