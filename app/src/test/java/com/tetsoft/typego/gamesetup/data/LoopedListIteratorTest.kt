package com.tetsoft.typego.gamesetup.data

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class LoopedListIteratorTest {

    val iterator = LoopedListIterator<String>()

    @Before
    fun prepare() {
        iterator.init(listOf("1", "2", "3"))
    }

    @Test
    fun canGoBack_default_true() {
        assertTrue(LoopedListIterator<String>().canGoBack())
    }

    @Test
    fun canGoNext_default_true() {
        assertTrue(LoopedListIterator<String>().canGoNext())
    }

    @Test
    fun nextElement_variousCases_returnsNextAndLoops() {
        assertEquals("1", iterator.currentElement())
        assertEquals("2", iterator.nextElement())
        assertEquals("3", iterator.nextElement())
        assertEquals("1", iterator.nextElement())
    }

    @Test
    fun previousElement_variousCases_returnsPreviousAndLoops() {
        assertEquals("1", iterator.currentElement())
        assertEquals("3", iterator.previousElement())
        assertEquals("2", iterator.previousElement())
        assertEquals("1", iterator.previousElement())
    }
}