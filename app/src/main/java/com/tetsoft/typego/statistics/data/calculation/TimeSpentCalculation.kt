package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.domain.StatisticsCalculation

class TimeSpentCalculation(private val resultList: List<GameResult>) : StatisticsCalculation {

    override fun provide(): Int {
        var total = 0
        for (result in resultList) {
            total += result.getTimeSpent()
        }
        if (total == 0) return 0
        return (total / 60)
    }
}