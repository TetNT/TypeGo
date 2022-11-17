package com.tetsoft.typego.data.statistics.calculation

import org.junit.Assert.*

import org.junit.Test

class ProgressionCalculationTest {

    @Test
    fun provide_pastWpm60CurrentWpm80_equals25() {
        assertEquals(25, ProgressionCalculation(60, 80).provide())
    }

    @Test
    fun provide_pastWpm80CurrentWpm60_equalsNegative25() {
        assertEquals(-25, ProgressionCalculation(80, 60).provide())
    }

    @Test
    fun provide_pastWpmEqualsCurrentWpm_equals0() {
        assertEquals(0, ProgressionCalculation(63, 63).provide())
    }

    @Test
    fun provide_pastWpmEquals0_equalsEmptyProgression() {
        assertEquals(
            ProgressionCalculation.EMPTY_PROGRESSION,
            ProgressionCalculation(0, 63).provide()
        )
    }

    @Test
    fun provide_currentWpmEquals0_equalsEmptyProgression() {
        assertEquals(
            ProgressionCalculation.EMPTY_PROGRESSION,
            ProgressionCalculation(63, 0).provide()
        )
    }
}