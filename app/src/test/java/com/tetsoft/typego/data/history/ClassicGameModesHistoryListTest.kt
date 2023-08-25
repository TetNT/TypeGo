package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.*
import org.junit.Test

class ClassicGameModesHistoryListTest {

    @Test
    fun init_twoLists_assertMergedProperly() {
        val gameOnTimeList = GameOnTimeHistoryList(
            listOf(
                GameOnTimeMock(100L),
                GameOnTimeMock(400L),
                GameOnTimeMock(150L),
            )
        )
        val gameOnNumberOfWordsList = GameOnNumberOfWordsHistoryList(
            listOf(
                GameOnNumberOfWordsMock(200L),
                GameOnNumberOfWordsMock(500L)
            )
        )

        val combined = ClassicGameModesHistoryList(gameOnTimeList, gameOnNumberOfWordsList)
        assertEquals(combined[0].getCompletionDateTime(), 100L)
        assertEquals(combined[1].getCompletionDateTime(), 150L)
        assertEquals(combined[2].getCompletionDateTime(), 200L)
        assertEquals(combined[3].getCompletionDateTime(), 400L)
        assertEquals(combined[4].getCompletionDateTime(), 400L)
    }

    private class GameOnTimeMock(dateTime: Long) :
        GameOnTime(0.0, 0, 0, 0, "", "", "", false, 0, 0, dateTime)

    private class GameOnNumberOfWordsMock(dateTime: Long) :
        GameOnNumberOfWords(0.0, 0, 0, 0, 0, "", "", "", false, 0, 0, dateTime)
}