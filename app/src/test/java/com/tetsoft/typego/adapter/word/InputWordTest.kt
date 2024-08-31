package com.tetsoft.typego.adapter.word

import com.tetsoft.typego.data.Word
import org.junit.Assert.*

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