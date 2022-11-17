package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.calculation.AverageCurrentWpmCalculation
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeMock
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class AverageCurrentWpmCalculationTest {

    @Test
    fun provide_emptyResults_equals0() {
        val results = GameResultList()
        assertEquals(0, AverageCurrentWpmCalculation(results, 4).provide())
    }

    @Test
    fun provide_resultsWithEmptyWpm_equals0() {
        val results = GameResultList(arrayListOf(
            GameResult.Empty(),
            GameResult.Empty()
        ))
        assertEquals(0, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize2_equals45() {
        val results = GameResultList(arrayListOf(
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                240,
                60,
                12,
                12,
                0L
            ),
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                120,
                60,
                12,
                12,
                0L
            )
        ))
        assertEquals(45, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize3_equals50() {
        val results = GameResultList(arrayListOf(
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                240,
                60,
                12,
                12,
                0L
            ),
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                120,
                60,
                12,
                12,
                0L
            ),
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                240,
                60,
                12,
                12,
                0L
            )
        ))
        assertEquals(50, AverageCurrentWpmCalculation(results, 3).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize2_equals60() {
        val results = GameResultList(arrayListOf(
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                240,
                60,
                12,
                12,
                0L
            ),
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                120,
                60,
                12,
                12,
                0L
            ),
            GameResult(
                GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(30),
                360,
                60,
                12,
                12,
                0L
            )
        ))
        assertEquals(60, AverageCurrentWpmCalculation(results, 2).provide())
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
        val avgWpmStatistics = AverageCurrentWpmCalculation(results, 4)
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
        val avgWpmStatistics = AverageCurrentWpmCalculation(results, 4)
        //assertEquals(VisibilityProvider.Visible().get(), avgWpmStatistics.visibility().get())
    }
}