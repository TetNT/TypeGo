package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.AccuracyCalculation
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class AccuracyStatisticsTest {

    @Test
    fun getVisibility_resultsEmpty_equalsGone() {
        val results = GameResultList()
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmpty_equalsVisible() {
        val results = GameResultList(listOf(GameOnTimeResultMock().getSimpleGameResult(30)))
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmptyWordsAllWrong_equalsGone() {
        val results = GameResultList()
        results.add(GameResult(GameMode.Empty(), 300, 60, 42, 0, 0L))
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }
}