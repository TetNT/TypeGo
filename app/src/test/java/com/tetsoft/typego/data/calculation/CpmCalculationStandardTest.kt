package com.tetsoft.typego.data.calculation

import org.junit.Assert.*

import org.junit.Test

class CpmCalculationStandardTest {

    @Test
    fun calculate_15secs0chars_equals0() {
        assertEquals(CpmCalculation.Standard(15, 0).calculate(), 0)
    }

    @Test
    fun calculate_0secs15chars_equals0() {
        assertEquals(CpmCalculation.Standard(0, 15).calculate(), 0)
    }

    @Test
    fun calculate_0secs0chars_equals0() {
        assertEquals(CpmCalculation.Standard(0, 0).calculate(), 0)
    }

    @Test
    fun calculate_60secs240chars_equals240() {
        assertEquals(CpmCalculation.Standard(60, 240).calculate(), 240)
    }

    @Test
    fun calculate_15secs60chars_equals240() {
        assertEquals(CpmCalculation.Standard(15, 60).calculate(), 240)
    }
}