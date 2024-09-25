package com.tetsoft.typego.wordslog.data

import com.tetsoft.typego.core.data.Word
import org.junit.Assert.assertEquals
import org.junit.Test

class InputWordTest {

    @Test
    fun getInput_inputLongerThanOriginal() {
        val word = Word("The", "T", true)
        assertEquals("The", InputWord(word).getInput())
    }

    @Test
    fun getInput_inputShorterThanOriginal() {
        val word = Word("T", "The", true)
        assertEquals("The", InputWord(word).getInput())
    }

    @Test
    fun getInput_inputLongerThanOriginalWithMistakes() {
        val word = Word("Te", "The", true)
        assertEquals("Tee", InputWord(word).getInput())
    }
}