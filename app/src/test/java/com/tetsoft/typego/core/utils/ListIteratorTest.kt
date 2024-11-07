package com.tetsoft.typego.core.utils

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ListIteratorTest {

    val iterator = ListIterator.Standard<String>()

    @Before
    fun prepare() {
        iterator.init(listOf("1", "2", "3"))
    }

    @Test
    fun canGoBack_secondElement_true() {
        iterator.nextElement()
        assertTrue(iterator.canGoBack())
    }

    @Test
    fun canGoBack_firstElement_false() {
        assertEquals("1", iterator.currentElement())
        assertFalse(iterator.canGoBack())
    }

    @Test
    fun canGoNext_firstElement_True() {
        assertEquals("1", iterator.currentElement())
        assertTrue(iterator.canGoNext())
    }

    @Test
    fun canGoNext_lastElement_false() {
        iterator.nextElement()
        iterator.nextElement()
        assertFalse(iterator.canGoNext())
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun nextElement_iterationOnLastElement_throwsOutOfBounds() {
        iterator.nextElement()
        iterator.nextElement()
        assertFalse(iterator.canGoNext())
        iterator.nextElement()
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun previousElement_iterationOnFirstElement_throwsOutOfBounds() {
        assertFalse(iterator.canGoBack())
        iterator.previousElement()
    }
}