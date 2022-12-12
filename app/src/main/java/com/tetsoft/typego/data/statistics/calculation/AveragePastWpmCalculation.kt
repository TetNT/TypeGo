package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.PoolEnhancement
import com.tetsoft.typego.game.result.GameResult

class AveragePastWpmCalculation(
    private val resultList: List<GameResult>,
    private val resultsPoolSize: Int,
    private val poolEnhancement: PoolEnhancement
) : StatisticsCalculation {

    override fun provide(): Int {
        if (resultList.size < resultsPoolSize * 2) return 0
        var wpm = 0
        for (i in 0 until poolEnhancement.provide() + 1)
            wpm += resultList[i].wpm.toInt()
        return (wpm / poolEnhancement.provide())
    }
}