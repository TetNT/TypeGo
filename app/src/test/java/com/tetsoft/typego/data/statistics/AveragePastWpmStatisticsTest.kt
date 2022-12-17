package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class AveragePastWpmStatisticsTest {

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
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
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
        val poolEnhancement = PoolEnhancement.Base(results.size, 4)
        val calculation = AveragePastWpmCalculation(results, 4, poolEnhancement)
        val statistics = AveragePastWpmStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }
}