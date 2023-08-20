package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import org.junit.Assert.*

import org.junit.Test

class NoMistakesInARowRequirementTest {

    @Test
    fun isReached_listSizeMoreThanRequiredNoIncorrectWords_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_incorrectWordsOutsideOfTheRow_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnNumberOfWords(3))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        assertTrue(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listContainsIncorrectWords_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(3))
        assertFalse(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequired_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnNumberOfWords(0))
        list.add(FakeGameOnNumberOfWords(0))
        assertFalse(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun isReached_emptyList_assertFalse() {
        val list = ClassicGameModesHistoryList()
        assertFalse(GameRequirement.NoMistakesInARowRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_listSizeMoreThanRequiredNoIncorrectWords_assert3() {
        // To be done after the method fix
    }

    private class FakeGameOnNumberOfWords(private val incorrectWords: Int) :
        GameOnNumberOfWords(0.0, 0, 0, 0, 0, "", "", "", false, 0, 0, 0) {
        override fun getIncorrectWords(): Int {
            return incorrectWords
        }
    }
}