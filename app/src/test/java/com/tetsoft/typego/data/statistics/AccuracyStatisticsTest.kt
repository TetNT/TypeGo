package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.statistics.calculation.AccuracyCalculation
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AccuracyStatisticsTest {

    @Test
    fun getVisibility_resultsEmpty_equalsGone() {
        val results = ClassicGameModesHistoryList(emptyList(), emptyList())
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmptyWithCorrectWords_equalsVisible() {
        val results = ClassicGameModesHistoryList(listOf(GameOnTimeMock(30, 29)), emptyList())
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Visible().get(), statistics.getVisibility().get())
    }

    @Test
    fun getVisibility_resultsNotEmptyWordsAllWrong_equalsGone() {
        val results = ClassicGameModesHistoryList(listOf(GameOnTimeMock(5, 0)), emptyList())
        val calculation = AccuracyCalculation(results)
        val statistics = AccuracyStatistics(calculation)
        assertEquals(VisibilityProvider.Gone().get(), statistics.getVisibility().get())
    }

    private class GameOnTimeMock(wordsWritten: Int, correctWords: Int) :
        GameOnTime(0.0, 0, 30, 0, "", "", "", false, wordsWritten, correctWords, 0L)
}