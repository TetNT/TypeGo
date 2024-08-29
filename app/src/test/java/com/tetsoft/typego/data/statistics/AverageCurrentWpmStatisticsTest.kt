package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.data.statistics.calculation.AverageCurrentWpmCalculation
import org.junit.Assert.assertEquals
import org.junit.Test

class AverageCurrentWpmStatisticsTest {

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
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Gone().get(), avgWpmStatistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
        val results =
            listOf(
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
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Visible().get(), avgWpmStatistics.getVisibility().get())
    }
}