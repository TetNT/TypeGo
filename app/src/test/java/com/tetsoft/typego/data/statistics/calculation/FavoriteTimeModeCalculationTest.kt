package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.history.data.GameOnTimeHistoryList
import com.tetsoft.typego.core.domain.TimeMode
import com.tetsoft.typego.statistics.data.calculation.FavoriteTimeModeCalculation
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteTimeModeCalculationTest {

    @Test
    fun provide_15secondsMajority_equalsTimeMode15() {
        val results = GameOnTimeHistoryList()
        results.add(MockRandomWords(15))
        results.add(MockRandomWords(15))
        results.add(MockRandomWords(30))
        results.add(MockRandomWords(15))
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(15))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = GameOnTimeHistoryList()
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(0))
    }

    private class MockRandomWords(private val timeInSeconds: Int) : RandomWordsMock() {
        override fun getChosenTimeInSeconds() = timeInSeconds
    }
}