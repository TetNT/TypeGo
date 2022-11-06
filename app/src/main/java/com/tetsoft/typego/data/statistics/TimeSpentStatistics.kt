package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.game.result.GameResultList

class TimeSpentStatistics(private val resultList: GameResultList) : StatisticsProvider {

    override fun provide(): String {
        var total = 0
        for (result in resultList) {
            total += result.timeSpentInSeconds
        }
        return (total / 60).toString()
    }

    override fun visibility(): VisibilityProvider {
        return if (resultList.isEmpty())
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}