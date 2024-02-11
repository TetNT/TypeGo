package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.statistics.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AveragePastWpmStatisticsTest {

    @Test
    fun getVisibility_resultsLessThanDoubledPoolSize_equalsGone() {
        val results = ClassicGameModesHistoryList(
            listOf(
                MockGameOnTime(0.0),
                MockGameOnTime(0.0),
                MockGameOnTime(0.0),
                MockGameOnTime(0.0),
                MockGameOnTime(0.0),
                MockGameOnTime(0.0),
            ), emptyList()
        )
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
        val results = ClassicGameModesHistoryList(
            listOf(
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
                MockGameOnTime(30.0),
            ), emptyList()
        )
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }

    private class MockGameOnTime(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)

}