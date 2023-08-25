package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList

class TimeSpentCalculation(private val resultList: ClassicGameModesHistoryList) : StatisticsCalculation {

    override fun provide(): Int {
        var total = 0
        for (result in resultList) {
            total += result.getTimeSpent()
        }
        if (total == 0) return 0
        return (total / 60)
    }
}