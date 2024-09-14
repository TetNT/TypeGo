package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.TimeSpentCalculation

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