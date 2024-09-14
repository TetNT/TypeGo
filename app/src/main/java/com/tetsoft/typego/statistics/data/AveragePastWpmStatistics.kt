package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.AveragePastWpmCalculation

class AveragePastWpmStatistics(averagePastWpmCalculation: AveragePastWpmCalculation) : Statistics {

    private val averageWpm = averagePastWpmCalculation.provide()

    override fun provide(): Int {
        return averageWpm
    }

    override fun getVisibility() : VisibilityProvider {
        if (averageWpm == 0) {
            return VisibilityProvider.Gone()
        }
        return VisibilityProvider.Visible()
    }
}