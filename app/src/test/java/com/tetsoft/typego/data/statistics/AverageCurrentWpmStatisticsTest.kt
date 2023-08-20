package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.statistics.calculation.AverageCurrentWpmCalculation
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AverageCurrentWpmStatisticsTest {

    @Test
    fun getVisibility_resultsLessThanDoubledPoolSize_equalsGone() {
        val results = ClassicGameModesHistoryList(
            listOf(
                GameOnTimeMock(0.0),
                GameOnTimeMock(0.0),
                GameOnTimeMock(0.0),
                GameOnTimeMock(0.0),
                GameOnTimeMock(0.0),
                GameOnTimeMock(0.0),
            ), emptyList()
        )
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Gone().get(), avgWpmStatistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
        val results = ClassicGameModesHistoryList(
            listOf(
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
                GameOnTimeMock(30.0),
            ), emptyList()
        )
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Visible().get(), avgWpmStatistics.getVisibility().get())
    }

    private class GameOnTimeMock(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)

}