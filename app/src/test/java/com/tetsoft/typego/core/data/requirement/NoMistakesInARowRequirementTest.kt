package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.RandomWordsMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NoMistakesInARowRequirementTest {

    @Test
    fun isReached_listSizeMoreThanRequiredNoIncorrectWords_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
            ), emptyList()
        )
        assertTrue(NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_incorrectWordsOutsideOfTheRow_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(3),
                Mock(0),
                Mock(0),
                Mock(0),
            ), emptyList()
        )
        assertTrue(NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listContainsIncorrectWordsButRequirementIsMet_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(3),
                Mock(0),
            ), emptyList()
        )
        assertTrue(NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listContainsIncorrectWords_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(2),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(2),
            ), emptyList()
        )
        assertTrue(NoMistakesInARowRequirement(5).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequired_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(0),
            ), emptyList()
        )
        assertFalse(NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_emptyList_assertFalse() {
        val list = GameHistoryImpl(emptyList(), emptyList())
        assertFalse(NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_listSizeMoreThanRequiredNoIncorrectWords_assert4() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
            ), emptyList()
        )
        assertEquals(4, NoMistakesInARowRequirement(3).getCurrentProgress(list))
    }

    @Test
    fun getCurrentProgress_listSizeLessThanRequiredNoIncorrectWords_assert2() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(0),
            ), emptyList()
        )
        assertEquals(2, NoMistakesInARowRequirement(3).getCurrentProgress(list))
    }

    @Test
    fun getCurrentProgress_listContainsIncorrectWords_assert5() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
                Mock(2),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(0),
                Mock(2),
            ), emptyList()
        )
        assertEquals(5, NoMistakesInARowRequirement(3).getCurrentProgress(list))
    }

    @Test
    fun getCurrentProgress_oneEntryNoMistakes_assert1() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                Mock(0),
            ), emptyList()
        )
        assertEquals(1, NoMistakesInARowRequirement(3).getCurrentProgress(list))
    }

    private class Mock(private val incorrectWords: Int) : RandomWordsMock() {
        override fun getIncorrectWords() = incorrectWords
    }
}