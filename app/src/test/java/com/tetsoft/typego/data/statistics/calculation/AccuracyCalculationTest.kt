package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.calculation.AccuracyCalculation
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*
import org.junit.Ignore

import org.junit.Test

class AccuracyCalculationTest {

    @Test
    fun provide_resultsEmpty_equals0() {
        val results = GameResultList()
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsCorrect_equals100() {
        val results = GameResultList()
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(30, 30))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(15, 15))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(600, 600))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(34, 34))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(196, 196))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(6, 6))
        assertEquals(100, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_allWordsWrong_equals0() {
        val results = GameResultList()
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(30, 0))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(15, 0))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(600, 0))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(34, 0))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(196, 0))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(6, 0))
        assertEquals(0, AccuracyCalculation(results).provide())
    }

    @Test
    fun provide_someWordsWrong_equals80() {
        val results = GameResultList()
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(30, 26))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(40, 34))
        results.add(GameOnTimeResultMock().getSampleByWordsWritten(50, 36))
        assertEquals(80, AccuracyCalculation(results).provide())
    }

}