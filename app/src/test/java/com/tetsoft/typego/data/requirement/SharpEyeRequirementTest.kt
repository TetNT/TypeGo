package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SharpEyeRequirementTest {

    @Test
    fun isReached_lastResultTimeSpentEqualsRequiredWpmMoreThan30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime(31.0, 60))
        assertTrue(GameRequirement.SharpEyeRequirement(60).isReached(list))
    }

    @Test
    fun isReached_lastResultTimeSpent120WpmMoreThan30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime(31.0, 120))
        assertTrue(GameRequirement.SharpEyeRequirement(120).isReached(list))
    }

    @Test
    fun isReached_lastResultTimeSpentNotEquals_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime(31.0, 60))
        assertFalse(GameRequirement.SharpEyeRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmLessThan30_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime(25.0, 60))
        assertFalse(GameRequirement.SharpEyeRequirement(60).isReached(list))
    }

    @Test
    fun isReached_lastResultNotGameOnTime_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(31.0, 60))
        assertFalse(GameRequirement.SharpEyeRequirement(60).isReached(list))
    }

    @Test
    fun isReached_resultListEmpty_assertFalse() {
        val list = ClassicGameModesHistoryList()
        assertFalse(GameRequirement.SharpEyeRequirement(60).isReached(list))
    }


    private class MockGameOnTime(wpm: Double, chosenTime: Int) :
        GameOnTime(wpm, 0, 0, chosenTime, "", "", "", false, 0, 0, 0)

    private class MockGameOnNumberOfWords(wpm: Double, timeSpent: Int) :
        GameOnNumberOfWords(wpm, 0, 0, timeSpent, 0, "", "", "", false, 0, 0, 0)
}