package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AccuracyCalculationTest {

    @Test
    fun provide_resultsEmpty_equals0() {
        val results = ClassicGameModesHistoryList()
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsCorrect_equals100() {
        val results = ClassicGameModesHistoryList()
        results.add(MockGameOnTime(30, 30))
        results.add(MockGameOnTime(15, 15))
        results.add(MockGameOnTime(196, 196))
        assertEquals(100, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsWrong_equals0() {
        val results = ClassicGameModesHistoryList()
        results.add(MockGameOnTime(30, 0))
        results.add(MockGameOnTime(15, 0))
        results.add(MockGameOnTime(196, 0))
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_someWordsWrong_equals80() {
        val results = ClassicGameModesHistoryList()
        results.add(MockGameOnTime(30, 26))
        results.add(MockGameOnTime(40, 34))
        results.add(MockGameOnTime(50, 36))
        assertEquals(80, AccuracyCalculation(results).provide())
    }

    private class MockGameOnTime(wordsWritten: Int, correctWords: Int) :
        GameOnTime(0.0, 0, 30, 0, "", "", "", false, wordsWritten, correctWords, 0L)
}