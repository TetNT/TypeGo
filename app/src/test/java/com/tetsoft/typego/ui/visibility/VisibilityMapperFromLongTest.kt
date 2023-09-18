package com.tetsoft.typego.ui.visibility

import android.view.View
import org.junit.Assert.*
import org.junit.Test

class VisibilityMapperFromLongTest {

    @Test
    fun get_longIs20_equalsViewVisible() {
        val long = 20L
        assertEquals(VisibilityMapper.FromLong(long).get(), View.VISIBLE)
    }

    @Test
    fun get_longIs0_equalsViewGone() {
        val long = 0L
        assertEquals(VisibilityMapper.FromLong(long).get(), View.INVISIBLE)
    }
}