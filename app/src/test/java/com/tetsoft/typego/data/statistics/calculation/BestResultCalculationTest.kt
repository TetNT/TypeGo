package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class BestResultCalculationTest {

    @Test
    fun provide_withPreparedData_equals70() {
        val preparedData = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock(55.0),
            GameOnTimeMock(50.0),
            GameOnTimeMock(65.0),
            GameOnTimeMock(55.0),
            GameOnTimeMock(41.0),
            GameOnTimeMock(64.0),
            GameOnTimeMock(64.0),
            GameOnTimeMock(63.0),
            GameOnTimeMock(70.0),
            GameOnTimeMock(62.0),
            GameOnTimeMock(62.0),
        ), emptyList())
        assertEquals(70, BestResultCalculation(preparedData).provide())
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedData = ClassicGameModesHistoryList(emptyList(), emptyList())
        assertEquals(0, BestResultCalculation(preparedData).provide())
    }

    private class GameOnTimeMock(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)

}