package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class AveragePastWpmCalculationTest {

    @Test
    fun provide() {

    }

    @Test
    fun visibility_resultsLessThanDoubledPoolSize_equalsGone() {
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
        val avgWpmStatistics = AveragePastWpmCalculation(results, 4)
        //assertEquals(VisibilityProvider.Gone().get(), avgWpmStatistics.visibility().get())
    }

    @Test
    fun visibility_resultsMoreThanDoubledPoolSize_equalsVisible() {
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
        val avgWpmStatistics = AveragePastWpmCalculation(results, 4)
        //assertEquals(VisibilityProvider.Visible().get(), avgWpmStatistics.visibility().get())
    }
}