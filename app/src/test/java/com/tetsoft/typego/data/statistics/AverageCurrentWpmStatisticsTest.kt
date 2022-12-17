package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.AverageCurrentWpmCalculation
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class AverageCurrentWpmStatisticsTest {

    @Test
    fun getVisibility_resultsLessThanDoubledPoolSize_equalsGone() {
        val results = GameResultList(
            arrayListOf(
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
            )
        )
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Gone().get(), avgWpmStatistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
        val results = GameResultList(
            arrayListOf(
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
                GameOnTimeResultMock().getSimpleGameResult(30),
            )
        )
        val avgWpmStatistics = AverageCurrentWpmStatistics(AverageCurrentWpmCalculation(results, 4))
        assertEquals(VisibilityProvider.Visible().get(), avgWpmStatistics.getVisibility().get())
    }
}