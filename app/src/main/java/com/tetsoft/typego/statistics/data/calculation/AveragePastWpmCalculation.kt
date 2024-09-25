package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.domain.PoolEnhancement
import com.tetsoft.typego.statistics.domain.StatisticsCalculation
import kotlin.math.roundToInt

/**
 * @param resultList all results that must be counted
 * @param resultsPoolSize base value to determine how many items to calculate
 * @param poolEnhancement an offset to take more items into account
 */
class AveragePastWpmCalculation(
    private val resultList: List<GameResult>,
    private val resultsPoolSize: Int,
    private val poolEnhancement: PoolEnhancement
) : StatisticsCalculation {

    override fun provide(): Int {
        if (resultsPoolSize == 0) return 0
        if (resultList.size < resultsPoolSize * 2) return 0
        var wpm = 0.0
        for (i in 0 until poolEnhancement.provide())
            wpm += resultList[i].getWpm()
        if (wpm == 0.0) return 0
        return (wpm / poolEnhancement.provide()).roundToInt()
    }
}