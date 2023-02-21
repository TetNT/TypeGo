package com.tetsoft.typego.ui.visibility

import android.view.View
import org.junit.Assert.*
import org.junit.Test

class VisibilityMapperTest {
    @Test
    fun get_booleanTrue_assertVisible() {
        val mapper = VisibilityMapper.FromBoolean(true)
        assertEquals(mapper.get(), View.VISIBLE)
    }

    @Test
    fun get_booleanFalse_assertGone() {
        val mapper = VisibilityMapper.FromBoolean(false)
        assertEquals(mapper.get(), View.GONE)
    }
}