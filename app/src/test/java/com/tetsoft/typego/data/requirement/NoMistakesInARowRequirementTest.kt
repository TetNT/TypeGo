package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import org.junit.Assert.*

import org.junit.Test

class NoMistakesInARowRequirementTest {

    @Test
    fun isReached_listSizeMoreThanRequiredNoIncorrectWords_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_incorrectWordsOutsideOfTheRow_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(3))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listContainsIncorrectWordsButRequirementIsMet_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(3))
        list.add(MockGameOnNumberOfWords(0))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listContainsIncorrectWords_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(2))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(2))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(5).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequired_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        assertFalse(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_emptyList_assertFalse() {
        val list = ClassicGameModesHistoryList()
        assertFalse(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_listSizeMoreThanRequiredNoIncorrectWords_assert4() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        assertEquals(GameRequirement.NoMistakesInARowRequirement(3).getCurrentProgress(list), 4)
    }

    @Test
    fun getCurrentProgress_listSizeLessThanRequiredNoIncorrectWords_assert2() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        assertEquals(GameRequirement.NoMistakesInARowRequirement(3).getCurrentProgress(list), 2)
    }

    @Test
    fun getCurrentProgress_listContainsIncorrectWords_assert5() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(2))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(0))
        list.add(MockGameOnNumberOfWords(2))
        assertEquals(GameRequirement.NoMistakesInARowRequirement(3).getCurrentProgress(list), 5)
    }

    @Test
    fun getCurrentProgress_oneEntryNoMistakes_assert1() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnNumberOfWords(0))
        assertEquals(GameRequirement.NoMistakesInARowRequirement(3).getCurrentProgress(list), 1)
    }

    private class MockGameOnNumberOfWords(private val incorrectWords: Int) :
        GameOnNumberOfWords(0.0, 0, 0, 0, 0, "", "", "", false, 0, 0, 0) {
        override fun getIncorrectWords(): Int {
            return incorrectWords
        }
    }
}