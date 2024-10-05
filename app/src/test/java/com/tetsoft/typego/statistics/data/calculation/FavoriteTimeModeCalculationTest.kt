package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.RandomWordsMock
import com.tetsoft.typego.history.domain.RandomWordsHistoryList
import com.tetsoft.typego.core.domain.TimeMode
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteTimeModeCalculationTest {

    @Test
    fun provide_15secondsMajority_equalsTimeMode15() {
        val results = RandomWordsHistoryList()
        results.add(MockRandomWords(15))
        results.add(MockRandomWords(15))
        results.add(MockRandomWords(30))
        results.add(MockRandomWords(15))
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(15))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = RandomWordsHistoryList()
        val calculation = FavoriteTimeModeCalculation(results)
        assertEquals(calculation.provide(), TimeMode(0))
    }

    private class MockRandomWords(private val timeInSeconds: Int) : RandomWordsMock() {
        override fun getChosenTimeInSeconds() = timeInSeconds
    }
}