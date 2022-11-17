package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.ProgressionCalculation

class ProgressionStatistics(progressionCalculation: ProgressionCalculation) : Statistics {

    private val progression = progressionCalculation.provide()

    override fun provide(): Int {
        return progression
    }

    override fun getVisibility(): VisibilityProvider {
        return if (progression == ProgressionCalculation.EMPTY_PROGRESSION)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}