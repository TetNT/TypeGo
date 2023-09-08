package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.statistics.PoolEnhancement
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AveragePastWpmCalculationTest {

    @Test
    fun provide_withPreparedDataPoolInitialSize4_equals53() {
        val preparedList = ClassicGameModesHistoryList(listOf(
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
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 53)
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize5_equals55() {
        val preparedList = ClassicGameModesHistoryList(listOf(
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
        val calc = AveragePastWpmCalculation(preparedList, 5, PoolEnhancement.Base(preparedList.size, 5))
        assertEquals(calc.provide(), 55)
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedList = ClassicGameModesHistoryList()
        val calc = AveragePastWpmCalculation(preparedList, 4, PoolEnhancement.Base(preparedList.size, 4))
        assertEquals(calc.provide(), 0)
    }

    @Test
    fun provide_withPreparedDataPoolInitialSize0_equals0() {
        val preparedList = ClassicGameModesHistoryList()
        val calc = AveragePastWpmCalculation(preparedList, 0, PoolEnhancement.Base(preparedList.size, 0))
        assertEquals(calc.provide(), 0)
    }
    
    private class MockGameOnTime(wpm: Double) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, 0L)


}