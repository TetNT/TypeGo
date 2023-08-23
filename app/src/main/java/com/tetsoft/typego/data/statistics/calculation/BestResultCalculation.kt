package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import kotlin.math.roundToInt

class BestResultCalculation(private val gameResultList: ClassicGameModesHistoryList) : StatisticsCalculation {

    override fun provide(): Int {
        var currentBest = 0
        for (result in gameResultList) {
            if (result.getWpm() > currentBest) currentBest = result.getWpm().roundToInt()
        }
        return currentBest
    }

}