package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.data.game.GameResult
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeSpentCalculationTest {

    @Test
    fun provide_differentResults_equals11() {
        val results = listOf(
            MockRandomWords( 600),
            MockRandomWords( 60),
            MockRandomWords( 15),
            MockRandomWords( 30),
        )
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 11)
    }

    @Test
    fun provide_differentResults_equals0() {
        val results = listOf(
            MockRandomWords( 15),
            MockRandomWords( 30),
        )
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = emptyList<GameResult>()
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    private class MockRandomWords(private val timeInSeconds: Int) : RandomWordsMock() {
        override fun getTimeSpent() = timeInSeconds
    }
}