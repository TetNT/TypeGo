package com.tetsoft.typego.core.data.visibilitymapper

import android.view.View
import com.tetsoft.typego.core.domain.VisibilityMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class VisibilityMapperVisibleInvisibleTest {

    @Test
    fun get_statementTrue_assertVisible() {
        val expected = View.VISIBLE
        val result = VisibilityMapper.VisibleInvisible(true).get()
        assertEquals(expected, result)
    }

    @Test
    fun get_statementFalse_assertInvisible() {
        val expected = View.INVISIBLE
        val result = VisibilityMapper.VisibleInvisible(false).get()
        assertEquals(expected, result)
    }

}