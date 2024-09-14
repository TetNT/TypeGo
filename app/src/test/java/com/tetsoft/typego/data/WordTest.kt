package com.tetsoft.typego.data

import com.tetsoft.typego.core.domain.CharacterStatus
import com.tetsoft.typego.core.domain.Word
import org.junit.Assert.*

import org.junit.Test

class WordTest {

    @Test
    fun getCharacterStatuses_unfinishedWord_containsNotFilled() {
        val inputted = "Wo"
        val original = "Word"
        val wordData = Word(inputted, original, true)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.NOT_FILLED)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.NOT_FILLED)
    }

    @Test
    fun getCharacterStatuses_misspelledWord_containsWrong() {
        val inputted = "Wotd"
        val original = "Word"
        val wordData = Word(inputted, original, true)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.WRONG)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.CORRECT)
    }

    @Test
    fun getCharacterStatuses_inputLongerThanOriginal_containsWrong() {
        val input = "thee"
        val original = "The"
        val wordData = Word(input, original, true)
        assertEquals(wordData.characterStatuses.size, 4)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.WRONG)
    }

    @Test
    fun getCharacterStatuses_differentCasesIgnoreCaseFalse_containsWrong() {
        val inputted = "word"
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.WRONG)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.CORRECT)
    }

    @Test
    fun getCharacterStatuses_sameInputIgnoreCaseFalse_containsCorrect() {
        val inputted = "Word"
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.CORRECT)
    }

    @Test
    fun getCharacterStatuses_sameInputDifferentCaseIgnoreCaseTrue_containsCorrect() {
        val inputted = "word"
        val original = "Word"
        val wordData = Word(inputted, original, true)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.CORRECT)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.CORRECT)
    }

    @Test
    fun getCharacterStatuses_emptyString_containsNotFilled() {
        val inputted = ""
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertEquals(wordData.characterStatuses[0], CharacterStatus.NOT_FILLED)
        assertEquals(wordData.characterStatuses[1], CharacterStatus.NOT_FILLED)
        assertEquals(wordData.characterStatuses[2], CharacterStatus.NOT_FILLED)
        assertEquals(wordData.characterStatuses[3], CharacterStatus.NOT_FILLED)
        assertEquals(wordData.characterStatuses.size, 4)
    }

    @Test
    fun isCorrect_sameWordIgnoreCaseFalse_correct() {
        val inputted = "Word"
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertTrue(wordData.isCorrect())
    }

    @Test
    fun isCorrect_sameWordIgnoreCaseTrue_correct() {
        val inputted = "Word"
        val original = "word"
        val wordData = Word(inputted, original, true)
        assertTrue(wordData.isCorrect())
    }

    @Test
    fun isCorrect_sameWordDifferentCaseIgnoreCaseFalse_notCorrect() {
        val inputted = "word"
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertFalse(wordData.isCorrect())
    }

    @Test
    fun isCorrect_longerWord_notCorrect() {
        val inputted = "Words"
        val original = "Word"
        val wordData = Word(inputted, original, false)
        assertFalse(wordData.isCorrect())
    }

    @Test
    fun isCorrect_shorterWord_notCorrect() {
        val inputted = "Word"
        val original = "Words"
        val wordData = Word(inputted, original, false)
        assertFalse(wordData.isCorrect())
    }
}