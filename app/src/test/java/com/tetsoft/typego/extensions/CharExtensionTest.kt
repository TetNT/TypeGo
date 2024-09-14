package com.tetsoft.typego.extensions

import com.tetsoft.typego.core.utils.extensions.equalsTo
import org.junit.Assert.*

import org.junit.Test

class CharExtensionTest {

    @Test
    fun equalsTo_ignoreCaseTrueDifferentCases_equalsTrue() {
        val char1 = 'A'
        val char2 = 'a'
        assertTrue(char1.equalsTo(char2, true))
    }

    @Test
    fun equalsTo_ignoreCaseFalseDifferentCases_equalsFalse() {
        val char1 = 'A'
        val char2 = 'a'
        assertFalse(char1.equalsTo(char2, false))
    }

    @Test
    fun equalsTo_differentCharacters_equalsFalse() {
        val char1 = 'A'
        val char2 = 'B'
        assertFalse(char1.equalsTo(char2, true))
        assertFalse(char1.equalsTo(char2, false))
    }

    @Test
    fun equalsTo_numerical_equalsTrue() {
        val char1 = '1'
        val char2 = '1'
        assertTrue(char1.equalsTo(char2, true))
        assertTrue(char1.equalsTo(char2, false))
    }
}