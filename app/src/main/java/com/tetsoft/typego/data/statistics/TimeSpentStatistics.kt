package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.TimeSpentCalculation

class TimeSpentStatistics(timeSpentCalculation: TimeSpentCalculation) : Statistics {
    private val timeSpent = timeSpentCalculation.provide()
    override fun provide(): Int {
        return timeSpent
    }

    override fun getVisibility(): VisibilityProvider {
        return if (timeSpent == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }

}