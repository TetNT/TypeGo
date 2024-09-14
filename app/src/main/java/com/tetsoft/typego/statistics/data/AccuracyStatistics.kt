package com.tetsoft.typego.statistics.data

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