package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult

class TotalWordsWrittenCalculation(private val resultsList : List<GameResult.WithWordsInformation>) :
    StatisticsCalculation {
    override fun provide(): Int {
        var total = 0
        for (result in resultsList) {
            total += result.getWordsWritten()
        }
        return total
    }
}