package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.AverageCurrentWpmCalculation

class AverageCurrentWpmStatistics(averageCurrentWpmCalculation: AverageCurrentWpmCalculation) :
    Statistics {

    private val averageCurrentWpm = averageCurrentWpmCalculation.provide()

    override fun provide(): Int {
        return averageCurrentWpm
    }

    override fun getVisibility(): VisibilityProvider {
        return if (averageCurrentWpm == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }

}