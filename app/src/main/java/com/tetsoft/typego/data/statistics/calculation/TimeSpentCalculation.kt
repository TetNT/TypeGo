package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResult

class TimeSpentCalculation(private val resultList: List<GameResult>) : StatisticsCalculation {

    override fun provide(): Int {
        var total = 0
        for (result in resultList) {
            total += result.timeSpentInSeconds
        }
        if (total == 0) return 0
        return (total / 60)
    }
}