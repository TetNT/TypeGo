package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.DaysSinceFirstTestCalculation

class DaysSinceFirstTestStatistics(daysSinceFirstTestCalculation: DaysSinceFirstTestCalculation) : Statistics {

    private val days = daysSinceFirstTestCalculation.provide()

    override fun provide(): Int {
        return days
    }

    override fun getVisibility(): VisibilityProvider {
        return if (days == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }


}