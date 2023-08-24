package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AverageCurrentWpmCalculationTest {

    @Test
    fun provide_emptyResults_equals0() {
        val results = ClassicGameModesHistoryList()
        assertEquals(0, AverageCurrentWpmCalculation(results, 4).provide())
    }

    @Test
    fun provide_resultsWithEmptyWpm_equals0() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
        ), emptyList())
        assertEquals(0, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmSize4PoolSize2_equals45() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(60.0),
            GameOnTimeMock(30.0),
        ), emptyList())
        assertEquals(45, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize3_equals50() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(60.0),
            GameOnTimeMock(30.0),
            GameOnTimeMock(60.0),
        ), emptyList())
        assertEquals(50, AverageCurrentWpmCalculation(results, 3).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize2_equals60() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock(0.0),
            GameOnTimeMock(0.0),
            GameOnTimeMock(60.0),
            GameOnTimeMock(30.0),
            GameOnTimeMock(90.0),
        ), emptyList())
        assertEquals(60, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize4_equals64() {
        val results = ClassicGameModesHistoryList(listOf(
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
        val calculation = AverageCurrentWpmCalculation(results, 4)
        assertEquals(64, calculation.provide())
    }

    private class GameOnTimeMock(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)

}