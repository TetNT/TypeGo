package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class BestResultCalculationTest {

    @Test
    fun provide_withPreparedData_equals70() {
        val preparedData = ClassicGameModesHistoryList(listOf(
            MockGameOnTime(55.0),
            MockGameOnTime(50.0),
            MockGameOnTime(65.0),
            MockGameOnTime(55.0),
            MockGameOnTime(41.0),
            MockGameOnTime(64.0),
            MockGameOnTime(64.0),
            MockGameOnTime(63.0),
            MockGameOnTime(70.0),
            MockGameOnTime(62.0),
            MockGameOnTime(62.0),
        ), emptyList())
        assertEquals(70, BestResultCalculation(preparedData).provide())
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedData = ClassicGameModesHistoryList(emptyList(), emptyList())
        assertEquals(0, BestResultCalculation(preparedData).provide())
    }

    private class MockGameOnTime(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)

}