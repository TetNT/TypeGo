package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.TotalWordsWrittenCalculation

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