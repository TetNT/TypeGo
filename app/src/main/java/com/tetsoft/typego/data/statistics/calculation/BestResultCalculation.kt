package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult
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