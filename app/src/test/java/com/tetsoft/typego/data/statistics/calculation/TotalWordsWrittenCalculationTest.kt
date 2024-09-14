package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.data.calculation.TotalWordsWrittenCalculation
import org.junit.Assert.assertEquals
import org.junit.Test

class TotalWordsWrittenCalculationTest {

    @Test
    fun provide_variousResults_equals130() {
        val results = listOf(
            MockRandomWords( 40),
            MockRandomWords( 50),
            MockRandomWords( 30),
            MockRandomWords( 10),
        )
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 130)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = emptyList<GameResult.WithWordsInformation>()
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    private class MockRandomWords(private val wordsWritten: Int) : RandomWordsMock() {
        override fun getWordsWritten() = wordsWritten
    }

}