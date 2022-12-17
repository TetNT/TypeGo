package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteTimeModeCalculationTest {

    @Test
    fun provide_15secondsMajority_equalsTimeMode15() {
        val results = GameResultList(listOf(
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 15, 1, 0),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 15, 1, 0),
            GameOnTimeResultMock().getResultGameOnTime(Language("FR"), 30, 1, 0),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 15, 1, 0),
        ))
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(15))
    }

    @Test
    fun provide_60SecondsMajority_equalsTimeMode60() {
        val results = GameResultList(listOf(
            GameOnTimeResultMock().getResultGameOnTime(Language("IT"), 60, 1, 0),
            GameOnTimeResultMock().getResultGameOnTime(Language("IT"), 15, 1, 0),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 60, 1, 0),
        ))
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(60))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = GameResultList()
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(0))
    }
}