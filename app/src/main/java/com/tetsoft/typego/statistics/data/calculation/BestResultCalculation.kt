package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.domain.StatisticsCalculation
import kotlin.math.roundToInt

class BestResultCalculation(private val gameResultList: List<GameResult>) : StatisticsCalculation {

    override fun provide(): Int {
        var currentBest = 0
        for (result in gameResultList) {
            if (result.getWpm() > currentBest) currentBest = result.getWpm().roundToInt()
        }
        return currentBest
    }

}