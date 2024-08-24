package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.data.game.GameResult
import org.junit.Assert.assertEquals
import org.junit.Test

class BestResultCalculationTest {

    @Test
    fun provide_withPreparedData_equals70() {
        val preparedData = listOf(
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
        assertEquals(70, BestResultCalculation(preparedData).provide())
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedData = emptyList<GameResult>()
        assertEquals(0, BestResultCalculation(preparedData).provide())
    }
}