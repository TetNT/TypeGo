package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList

class TotalWordsWrittenCalculation(private val resultsList : ClassicGameModesHistoryList) :
    StatisticsCalculation {
    override fun provide(): Int {
        var total = 0
        for (result in resultsList) {
            total += result.getWordsWritten()
        }
        return total
    }
}