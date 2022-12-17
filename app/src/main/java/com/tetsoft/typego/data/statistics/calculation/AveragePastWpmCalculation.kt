package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.PoolEnhancement
import com.tetsoft.typego.game.result.GameResult
import kotlin.math.roundToInt

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
            wpm += resultList[i].wpm
        if (wpm == 0.0) return 0
        return (wpm / poolEnhancement.provide()).roundToInt()
    }
}