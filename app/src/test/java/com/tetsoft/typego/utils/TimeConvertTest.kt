package com.tetsoft.typego.utils

import com.tetsoft.typego.core.utils.TimeConvert
import org.junit.Assert.*

import org.junit.Test

class TimeConvertTest {

    @Test
    fun convertSecondsToStamp_0seconds_equals0_00() {
        assertEquals(TimeConvert.convertSecondsToStamp(0), "0:00")
    }

    @Test
    fun convertSecondsToStamp_15seconds_equals0_15() {
        assertEquals(TimeConvert.convertSecondsToStamp(15), "0:15")
    }

    @Test
    fun convertSecondsToStamp_60seconds_equals1_00() {
        assertEquals(TimeConvert.convertSecondsToStamp(60), "1:00")
    }

    @Test
    fun convertSecondsToStamp_215seconds_equals3_45() {
        assertEquals(TimeConvert.convertSecondsToStamp(185), "3:05")
    }

    @Test
    fun convertSecondsToStamp_600seconds_equals10_00() {
        assertEquals(TimeConvert.convertSecondsToStamp(600), "10:00")
    }

    @Test
    fun convertSecondsToStamp_negativeSeconds_equals0() {
        assertEquals(TimeConvert.convertSecondsToStamp(-20), "0:00")
    }

}