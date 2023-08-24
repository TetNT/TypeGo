package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeSpentCalculationTest {

    @Test
    fun provide_differentResults_equals11() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock( 600),
            GameOnTimeMock( 60),
            GameOnTimeMock( 15),
            GameOnTimeMock( 30),
        ), emptyList())
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 11)
    }

    @Test
    fun provide_differentResults_equals0() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock( 15),
            GameOnTimeMock( 30),
        ), emptyList())
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = ClassicGameModesHistoryList()
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    private class GameOnTimeMock(timeSpent: Int) :
        GameOnTime(0.0, 0, 0, timeSpent, "", "", "", false, 0, 0, 0L)

}