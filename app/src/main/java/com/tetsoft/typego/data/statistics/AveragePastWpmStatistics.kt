package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.game.result.GameResultList

class AveragePastWpmStatistics(
    private val resultList: GameResultList,
    private val resultsPoolSize: Int
) : StatisticsProvider {

    override fun provide(): String {
        val poolEnhancement: Int = resultsPoolSize + resultList.size / 5
        var wpm = 0
        for (i in resultList.size - 1 downTo resultList.size - poolEnhancement + 1)
            wpm += resultList[i].wpm.toInt()
        return (wpm / poolEnhancement).toString()
    }

    override fun visibility() : VisibilityProvider {
        if (resultList.size < resultsPoolSize * 2) {
            return VisibilityProvider.Gone()
        }
        return VisibilityProvider.Visible()
    }
}