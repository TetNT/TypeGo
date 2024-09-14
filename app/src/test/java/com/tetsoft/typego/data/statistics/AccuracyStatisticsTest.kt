package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.statistics.data.calculation.AccuracyCalculation
import com.tetsoft.typego.statistics.data.AccuracyStatistics
import com.tetsoft.typego.statistics.data.VisibilityProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class AccuracyStatisticsTest {

    @Test
    fun getVisibility_resultsEmpty_equalsGone() {
        val results = emptyList<GameResult.WithWordsInformation>()
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmptyWithCorrectWords_equalsVisible() {
        val results = listOf(MockRandomWords(30, 29))
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmptyWordsAllWrong_equalsGone() {
        val results = listOf(MockRandomWords(5, 0))
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    private class MockRandomWords(private val wordsWritten: Int, private val correctWords: Int) : RandomWordsMock() {
        override fun getWordsWritten() = wordsWritten
        override fun getCorrectWords() = correctWords
    }
}