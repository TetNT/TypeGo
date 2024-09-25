package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.data.RandomWordsWpmMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WpmRequirementTest {

    @Test
    fun getCurrentProgress_multipleResults_assertEquals15() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(13.0),
                RandomWordsWpmMock(10.0),
                RandomWordsWpmMock(15.0),
            ), emptyList()
        )
        assertEquals(GameRequirement.WpmRequirement(30).getCurrentProgress(list), 15)
    }

    @Test
    fun getCurrentProgress_emptyResults_assertEquals0() {
        val list = GameHistoryImpl(emptyList(), emptyList())
        assertEquals(GameRequirement.WpmRequirement(30).getCurrentProgress(list), 0)
    }

    @Test
    fun isReached_wpmMoreThan30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(20.0),
                RandomWordsWpmMock(25.0),
                RandomWordsWpmMock(32.0),
            ), emptyList()
        )
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_oneItemWpmMoreThan30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(32.0)
            ), emptyList()
        )
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmEquals30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(20.0),
                RandomWordsWpmMock(25.0),
                RandomWordsWpmMock(30.0),
            ), emptyList()
        )
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmRoundedTo30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(20.0),
                RandomWordsWpmMock(25.0),
                RandomWordsWpmMock(29.85),
            ), emptyList()
        )
        assertTrue(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmLessThan30_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsWpmMock(20.0),
                RandomWordsWpmMock(25.0),
                RandomWordsWpmMock(26.0),
            ), emptyList()
        )
        assertFalse(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun isReached_emptyList_assertFalse() {
        val list = GameHistoryImpl(emptyList(), emptyList())
        assertFalse(GameRequirement.WpmRequirement(30).isReached(list))
    }

    @Test
    fun provideRequiredAmount_assertEqualsRequiredAmount() {
        val requiredAmount = 30
        val gameRequirement = GameRequirement.WpmRequirement(requiredAmount)
        assertEquals(gameRequirement.provideRequiredAmount(), requiredAmount)
    }
}