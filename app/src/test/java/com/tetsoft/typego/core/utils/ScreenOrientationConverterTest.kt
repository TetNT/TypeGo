package com.tetsoft.typego.core.utils

import com.tetsoft.typego.core.data.ScreenOrientation
import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenOrientationConverterTest {

    @Test
    fun convertToString_screenOrientationsProvided_equalsMatchingString() {
        assertEquals("portrait", ScreenOrientationConverter().convertToString(ScreenOrientation.PORTRAIT))
        assertEquals("landscape", ScreenOrientationConverter().convertToString(ScreenOrientation.LANDSCAPE))
    }

    @Test
    fun convertToEnum_validStringProvided_equalsMatchingEnum() {
        assertEquals(ScreenOrientation.PORTRAIT, ScreenOrientationConverter().convertToEnum("PORTRAIT"))
        assertEquals(ScreenOrientation.LANDSCAPE, ScreenOrientationConverter().convertToEnum("Landscape"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun convertToEnum_invalidString_illegalArgumentExceptionThrown() {
        ScreenOrientationConverter().convertToEnum("")
    }
}