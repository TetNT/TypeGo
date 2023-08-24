package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class GameOnTimeDataSelectorTest {

    @Test
    fun getBestResult_severalResults_wpmEquals30() {
        val historyList = GameOnTimeHistoryList()
        historyList.add(GameOnTimeMock(13.0))
        historyList.add(GameOnTimeMock(24.0))
        historyList.add(GameOnTimeMock(30.0))
        historyList.add(GameOnTimeMock(10.0))
        assertEquals(GameOnTimeDataSelector(historyList).getBestResult().getWpm(), 30.0, 0.001)
    }

    @Test
    fun getBestResult_emptyList_wpmEquals30() {
        val historyList = GameOnTimeHistoryList()
        assertEquals(GameOnTimeDataSelector(historyList).getBestResult().getWpm(), 0.0, 0.001)
    }

    @Test
    fun getSecondToLastResult_moreThan1Result_wpmEquals24() {
        val historyList = GameOnTimeHistoryList()
        historyList.add(GameOnTimeMock(13.0))
        historyList.add(GameOnTimeMock(30.0))
        historyList.add(GameOnTimeMock(24.0))
        historyList.add(GameOnTimeMock(10.0))
        assertEquals(GameOnTimeDataSelector(historyList).getSecondToLastResult().getWpm(), 24.0, 0.001)
    }

    @Test
    fun getSecondToLastResult_1result_wpmEquals0() {
        val historyList = GameOnTimeHistoryList()
        historyList.add(GameOnTimeMock(13.0))
        assertEquals(GameOnTimeDataSelector(historyList).getSecondToLastResult().getWpm(), 0.0, 0.001)
    }

    @Test
    fun getSecondToLastResult_emptyList_wpmEquals0() {
        val historyList = GameOnTimeHistoryList()
        assertEquals(GameOnTimeDataSelector(historyList).getSecondToLastResult().getWpm(), 0.0, 0.001)
    }

    @Test
    fun getMostRecentResult_severalResults_completionTimeEquals50000() {
        val historyList = GameOnTimeHistoryList()
        historyList.add(GameOnTimeMock(0.0, 10000L))
        historyList.add(GameOnTimeMock(0.0, 50000L))
        historyList.add(GameOnTimeMock(0.0, 40000L))
        historyList.add(GameOnTimeMock(0.0, 30000L))
        assertEquals(GameOnTimeDataSelector(historyList).getMostRecentResult().getCompletionDateTime(), 50000L)
    }

    @Test
    fun getMostRecentResult_emptyList_completionTimeEquals0() {
        val historyList = GameOnTimeHistoryList()
        assertEquals(GameOnTimeDataSelector(historyList).getMostRecentResult().getCompletionDateTime(), 0L)
    }

    private class GameOnTimeMock(wpm: Double, completionDate: Long = 0L) :
        GameOnTime(wpm, 0, 0, 0, "", "", "", false, 0, 0, completionDate)
}