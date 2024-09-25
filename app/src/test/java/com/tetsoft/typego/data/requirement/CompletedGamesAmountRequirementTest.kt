package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CompletedGamesAmountRequirementTest {

    @Test
    fun isReached_moreThanRequired_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsMock(),
                RandomWordsMock(),
                RandomWordsMock(),
                RandomWordsMock(),
            ), emptyList()
        )
        assertTrue(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun isReached_sameAsRequired_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsMock(),
                RandomWordsMock(),
                RandomWordsMock(),
            ), emptyList()
        )
        assertTrue(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun isReached_lessThanRequired_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsMock(),
                RandomWordsMock(),
            ), emptyList()
        )
        assertFalse(GameRequirement.CompletedGamesAmountRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_notEmptyList_assertEqualsListSize() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsMock(),
                RandomWordsMock(),
            ), emptyList()
        )
        assertEquals(list.getAllResults().size, GameRequirement.CompletedGamesAmountRequirement(3).getCurrentProgress(list))
    }
}