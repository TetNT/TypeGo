package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.RandomWordsWpmMock
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.data.PoolEnhancementImpl
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
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancementImpl(preparedList.size, 4))
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
        val calc = AveragePastWpmCalculation(preparedList, 5, PoolEnhancementImpl(preparedList.size, 5))
        assertEquals(calc.provide(), 55)
    }

    @Test
    fun provide_emptyListDataPoolInitialSize4_equals0() {
        val preparedList = emptyList<GameResult>()
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancementImpl(preparedList.size, 4))
        assertEquals(calc.provide(), 0)
    }

    @Test
    fun provide_emptyListDataPoolInitialSize0_equals0() {
        val preparedList = emptyList<GameResult>()
        val calc = AveragePastWpmCalculation(preparedList, 0, PoolEnhancementImpl(preparedList.size, 0))
        assertEquals(calc.provide(), 0)
    }

}