package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResult

class TotalWordsWrittenCalculation(private val resultsList : List<GameResult>) :
    StatisticsCalculation {
    override fun provide(): Int {
        var total = 0
        for (result in resultsList) {
            total += result.wordsWritten
        }
        return total
    }
}