package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteTimeModeCalculationTest {

    @Test
    fun provide_15secondsMajority_equalsTimeMode15() {
        val results = GameOnTimeHistoryList()
        results.add(GameOnTimeMock(15))
        results.add(GameOnTimeMock(15))
        results.add(GameOnTimeMock(30))
        results.add(GameOnTimeMock(15))
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(15))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = GameOnTimeHistoryList()
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(0))
    }

    private class GameOnTimeMock(timeInSeconds: Int) :
        GameOnTime(0.0, 0, 0, timeInSeconds, "", "", "", false, 0, 0, 0L)

}