package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult

class AccuracyCalculation(private val resultsList : List<GameResult.WithWordsInformation>) : StatisticsCalculation {

    override fun provide(): Int {
        if (resultsList.isEmpty()) return 0
        var wordsInTotal = 0
        var incorrectWordsCount = 0
        for (result in resultsList) {
            wordsInTotal += result.getWordsWritten()
            incorrectWordsCount += result.getWordsWritten() - result.getCorrectWords()
        }
        if (wordsInTotal == 0 && incorrectWordsCount > 0)
            return 0
        if (incorrectWordsCount == 0 && wordsInTotal > 0) return 100
        return (100 - 100 * incorrectWordsCount / wordsInTotal)
    }
}