package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.BestResultCalculation

class BestResultStatistics(bestResultCalculation: BestResultCalculation) : Statistics {

    private val bestResult = bestResultCalculation.provide()

    override fun provide(): Int {
        return bestResult
    }

    override fun getVisibility(): VisibilityProvider {
        return if (bestResult == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }

}