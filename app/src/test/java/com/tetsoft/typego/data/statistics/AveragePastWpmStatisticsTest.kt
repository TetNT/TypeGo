package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.statistics.data.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.statistics.data.statistics.AveragePastWpmStatistics
import com.tetsoft.typego.statistics.domain.PoolEnhancement
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class AveragePastWpmStatisticsTest {

    @Test
    fun getVisibility_resultsLessThanDoubledPoolSize_equalsGone() {
        val results = listOf(
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
        )
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
        val results = listOf(
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(30.0),
        )
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }
}