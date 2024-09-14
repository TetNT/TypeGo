package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.data.calculation.AverageCurrentWpmCalculation
import org.junit.Assert.assertEquals
import org.junit.Test

class AverageCurrentWpmCalculationTest {

    @Test
    fun provide_emptyResults_equals0() {
        val results = emptyList<GameResult>()
        assertEquals(0, AverageCurrentWpmCalculation(results, 4).provide())
    }

    @Test
    fun provide_resultsWithEmptyWpm_equals0() {
        val results = listOf(
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
        )
        assertEquals(0, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmSize4PoolSize2_equals45() {
        val results = listOf(
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(60.0),
            RandomWordsWpmMock(30.0),
        )
        assertEquals(45, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize3_equals50() {
        val results = listOf(
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(60.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(60.0),
        )
        assertEquals(50, AverageCurrentWpmCalculation(results, 3).provide())
    }

    @Test
    fun provide_resultsWithWpmPoolSize2_equals60() {
        val results = listOf(
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(0.0),
            RandomWordsWpmMock(60.0),
            RandomWordsWpmMock(30.0),
            RandomWordsWpmMock(90.0),
        )
        assertEquals(60, AverageCurrentWpmCalculation(results, 2).provide())
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize4_equals64() {
        val results = listOf(
            RandomWordsWpmMock(55.0),
            RandomWordsWpmMock(50.0),
            RandomWordsWpmMock(65.0),
            RandomWordsWpmMock(55.0),
            RandomWordsWpmMock(41.0),
            RandomWordsWpmMock(64.0),
            RandomWordsWpmMock(64.0),
            RandomWordsWpmMock(63.0),
            RandomWordsWpmMock(70.0),
            RandomWordsWpmMock(62.0),
            RandomWordsWpmMock(62.0),
        )
        val calculation = AverageCurrentWpmCalculation(results, 4)
        assertEquals(64, calculation.provide())
    }
}