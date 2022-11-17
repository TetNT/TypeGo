package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResult

class BestResultCalculation(private val gameResultList: List<GameResult>) : StatisticsCalculation {

    override fun provide(): Int {
        var currentBest = 0
        for (result in gameResultList) {
            if (result.wpm > currentBest) currentBest = result.wpm.toInt()
        }
        return currentBest
    }

}