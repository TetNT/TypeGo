package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.statistics.PoolEnhancement
import org.junit.Assert.assertEquals
import org.junit.Test

class AveragePastWpmCalculationTest {

    @Test
    fun provide_withPreparedDataPoolInitialSize4_equals53() {
        val preparedList = listOf(
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
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 53)
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize5_equals55() {
        val preparedList = listOf(
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
        val calc = AveragePastWpmCalculation(preparedList, 5, PoolEnhancement.Base(preparedList.size, 5))
        assertEquals(calc.provide(), 55)
    }

    @Test
    fun provide_emptyListDataPoolInitialSize4_equals0() {
        val preparedList = emptyList<GameResult>()
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 0)
    }

    @Test
    fun provide_emptyListDataPoolInitialSize0_equals0() {
        val preparedList = emptyList<GameResult>()
        val calc = AveragePastWpmCalculation(preparedList, 0, PoolEnhancement.Base(preparedList.size, 0))
        assertEquals(calc.provide(), 0)
    }

}