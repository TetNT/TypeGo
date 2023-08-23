package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class TotalWordsWrittenCalculationTest {

    @Test
    fun provide_variousResults_equals130() {
        val results = ClassicGameModesHistoryList(listOf(
            GameOnTimeMock( 40),
            GameOnTimeMock( 50),
            GameOnTimeMock( 30),
            GameOnTimeMock( 10),
        ), emptyList())
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 130)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = ClassicGameModesHistoryList(emptyList(), emptyList())
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    private class GameOnTimeMock(wordsWritten: Int) :
        GameOnTime(0.0, 0, 0, 0, "", "", "", false, wordsWritten, 0, 0L)


}