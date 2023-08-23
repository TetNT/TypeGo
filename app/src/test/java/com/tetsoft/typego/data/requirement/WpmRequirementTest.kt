package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import org.junit.Assert.*
import org.junit.Test

class WpmRequirementTest {

    @Test
    fun getCurrentProgress_multipleResults_assertEquals15() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(13.0))
        list.add(Result(10.0))
        list.add(Result(15.0))
        assertEquals(GameRequirement.WpmRequirement(30).getCurrentProgress(list), 15)
    }

    @Test
    fun getCurrentProgress_emptyResults_assertEquals0() {
        val list = ClassicGameModesHistoryList()
        assertEquals(GameRequirement.WpmRequirement(30).getCurrentProgress(list), 0)
    }

    @Test
    fun isReached_wpmMoreThan30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(20.0))
        list.add(Result(25.0))
        list.add(Result(32.0))
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_oneItemWpmMoreThan30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(32.0))
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmEquals30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(20.0))
        list.add(Result(25.0))
        list.add(Result(30.0))
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmRoundedTo30_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(20.0))
        list.add(Result(25.0))
        list.add(Result(29.85))
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmLessThan30_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(Result(20.0))
        list.add(Result(25.0))
        list.add(Result(26.0))
        assertFalse(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_emptyList_assertFalse() {
        val list = ClassicGameModesHistoryList()
        assertFalse(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun provideRequiredAmount_assertEqualsRequiredAmount() {
        val requiredAmount = 30
        val gameRequirement = GameRequirement.WpmRequirement(requiredAmount)
        assertEquals(gameRequirement.provideRequiredAmount(), requiredAmount)
    }

    private class Result(wpm: Double) :
        GameOnNumberOfWords(wpm, 0, 0, 0, 0, "", "", "", false, 0, 0, 0L) {
    }
}