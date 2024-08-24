package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult
import kotlin.math.roundToInt

class AverageCurrentWpmCalculation(
    private val resultsList: List<GameResult>,
    private val resultsPoolSize: Int
) :
    StatisticsCalculation {
    override fun provide(): Int {
        if (resultsList.size < resultsPoolSize * 2) return 0
        var wpm = 0.0
        for (i in resultsList.size - 1 downTo resultsList.size - resultsPoolSize)
            wpm += resultsList[i].getWpm()
        if (wpm == 0.0) return 0
        return (wpm / resultsPoolSize).roundToInt()
    }

}