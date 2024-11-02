package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
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