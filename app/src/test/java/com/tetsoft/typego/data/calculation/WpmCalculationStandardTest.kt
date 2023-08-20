package com.tetsoft.typego.data.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class WpmCalculationStandardTest {

    @Test
    fun calculate_15Secs0Chars_equals0() {
        assertEquals(WpmCalculation.Standard(15, 0).calculate(), 0.0, 0.001)
    }

    @Test
    fun calculate_0Secs15Chars_equals0() {
        assertEquals(WpmCalculation.Standard(0, 15).calculate(), 0.0, 0.001)
    }

    @Test
    fun calculate_0Secs0Chars_equals0() {
        assertEquals(WpmCalculation.Standard(0, 0).calculate(), 0.0, 0.001)
    }

    @Test
    fun calculate_60Secs240Chars_equals60() {
        assertEquals(WpmCalculation.Standard(60, 240).calculate(), 60.0, 0.001)
    }

    @Test
    fun calculate_15Secs60Chars_equals60() {
        assertEquals(WpmCalculation.Standard(15, 60).calculate(), 60.0, 0.001)
    }

}