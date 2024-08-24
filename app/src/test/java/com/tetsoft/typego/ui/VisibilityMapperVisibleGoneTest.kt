package com.tetsoft.typego.ui

import android.view.View
import org.junit.Assert.*
import org.junit.Test

class VisibilityMapperVisibleGoneTest {

    @Test
    fun get_statementTrue_assertVisible() {
        val expected = View.VISIBLE
        val result = VisibilityMapper.VisibleGone(true).get()
        assertEquals(expected, result)
    }

    @Test
    fun get_statementFalse_assertGone() {
        val expected = View.GONE
        val result = VisibilityMapper.VisibleGone(false).get()
        assertEquals(expected, result)
    }
}