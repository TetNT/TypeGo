package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.TotalWordsWrittenCalculation

class TotalWordsWrittenStatistics(totalWordsWrittenCalculation: TotalWordsWrittenCalculation) :
    Statistics {

    private val totalWords = totalWordsWrittenCalculation.provide()

    override fun provide(): Int {
        return totalWords
    }

    override fun getVisibility(): VisibilityProvider {
        return if (totalWords == 0) VisibilityProvider.Gone()
        else VisibilityProvider.Visible()
    }
}