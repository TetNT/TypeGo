package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.ProgressionCalculation

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