package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.*

import org.junit.Test

class CompletedGamesAmountRequirementTest {

    @Test
    fun isReached_moreThanRequired_assertTrue() {
        val list = ClassicGameModesHistoryList(
            listOf<GameOnTime>(
                MockGameOnTime(),
                MockGameOnTime(),
                MockGameOnTime(),
                MockGameOnTime(),
            ), emptyList()
        )
        assertTrue(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun isReached_sameAsRequired_assertTrue() {
        val list = ClassicGameModesHistoryList(
            listOf<GameOnTime>(
                MockGameOnTime(),
                MockGameOnTime(),
                MockGameOnTime(),
            ), emptyList()
        )
        assertTrue(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun isReached_lessThanRequired_assertFalse() {
        val list = ClassicGameModesHistoryList(
            listOf<GameOnTime>(
                MockGameOnTime(),
                MockGameOnTime(),
            ), emptyList()
        )
        assertFalse(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_notEmptyList_assertEqualsListSize() {
        val list = ClassicGameModesHistoryList(
            listOf<GameOnTime>(
                MockGameOnTime(),
                MockGameOnTime(),
            ), emptyList()
        )
        assertEquals(GameRequirement.CompletedGamesAmountRequirement(3).getCurrentProgress(list), list.size)
    }

    private class MockGameOnTime : GameOnTime(0.0, 0, 0, 0, "", "", "", false, 0, 0, 0)
}