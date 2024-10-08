package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.domain.StatisticsCalculation

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