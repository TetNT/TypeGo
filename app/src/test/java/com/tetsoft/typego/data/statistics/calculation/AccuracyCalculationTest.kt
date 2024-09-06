package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult
import org.junit.Assert.assertEquals
import org.junit.Test

class AccuracyCalculationTest {

    @Test
    fun provide_resultsEmpty_equals0() {
        val results = emptyList<GameResult.WithWordsInformation>()
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsCorrect_equals100() {
        val results = ArrayList<GameResult.WithWordsInformation>()
        results.add(MockResultWithWordsInformation(30, 30))
        results.add(MockResultWithWordsInformation(15, 15))
        results.add(MockResultWithWordsInformation(196, 196))
        assertEquals(100, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsWrong_equals0() {
        val results = ArrayList<GameResult.WithWordsInformation>()
        results.add(MockResultWithWordsInformation(30, 0))
        results.add(MockResultWithWordsInformation(15, 0))
        results.add(MockResultWithWordsInformation(196, 0))
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_someWordsWrong_equals80() {
        val results = ArrayList<GameResult.WithWordsInformation>()
        results.add(MockResultWithWordsInformation(30, 26))
        results.add(MockResultWithWordsInformation(40, 34))
        results.add(MockResultWithWordsInformation(50, 36))
        assertEquals(80, AccuracyCalculation(results).provide())
    }


    private class MockResultWithWordsInformation(private val wordsWritten: Int, private val correctWords: Int) : GameResult.WithWordsInformation {
        override fun getWordsWritten() = wordsWritten

        override fun getCorrectWords() = correctWords

        override fun getIncorrectWords() = 0

    }
}