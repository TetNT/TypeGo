package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.domain.StatisticsCalculation
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