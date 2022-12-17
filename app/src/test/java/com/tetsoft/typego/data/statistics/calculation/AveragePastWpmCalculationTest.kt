package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.PoolEnhancement
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameResultGameOnTimeListMock
import org.junit.Assert.assertEquals
import org.junit.Test

class AveragePastWpmCalculationTest {

    @Test
    fun provide_withPreparedDataPoolInitialSize4_equals53() {
        val preparedList = GameResultGameOnTimeListMock().getPreparedTestSet()
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 53)
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize5_equals55() {
        val preparedList = GameResultGameOnTimeListMock().getPreparedTestSet()
        val calc = AveragePastWpmCalculation(preparedList, 5, PoolEnhancement.Base(preparedList.size, 5))
        assertEquals(calc.provide(), 55)
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedList = GameResultList()
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 0)
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize0_equals0() {
        val preparedList = GameResultList()
        val calc = AveragePastWpmCalculation(preparedList, 0, PoolEnhancement.Base(preparedList.size, 0))
        assertEquals(calc.provide(), 0)
    }


}