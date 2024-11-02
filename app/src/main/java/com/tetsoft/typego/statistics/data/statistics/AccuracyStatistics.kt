package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.AccuracyCalculation

class AccuracyStatistics(accuracyCalculation: AccuracyCalculation) : Statistics {

    private val accuracy = accuracyCalculation.provide()

    override fun provide(): Int {
        return accuracy
    }

    override fun getVisibility(): VisibilityProvider {
        return if (accuracy == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}