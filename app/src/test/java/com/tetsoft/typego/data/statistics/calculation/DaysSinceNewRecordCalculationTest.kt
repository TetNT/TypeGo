package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.statistics.data.calculation.DaysSinceNewRecordCalculation
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DaysSinceNewRecordCalculationTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun provide_sameDay_equals0() {
        calendar.set(2021, 3, 10)
        val currentTime = calendar.timeInMillis
        val completionTime = calendar.timeInMillis
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(0, days)
    }

    @Test
    fun provide_completionTimeIsEmpty_equals0() {
        calendar.set(2021, 3, 10)
        val currentTime = calendar.timeInMillis
        val completionTime = 0L
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(0, days)
    }

    @Test
    fun provide_currentTimeIsEmpty_equals0() {
        val currentTime = 0L
        calendar.set(2021, 3, 7)
        val completionTime = calendar.timeInMillis
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(0, days)
    }

    @Test
    fun provide_3daysDifference_equals3() {
        calendar.set(2021, 3, 10)
        val currentTime = calendar.timeInMillis
        calendar.set(2021, 3, 7)
        val completionTime = calendar.timeInMillis
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(3, days)
    }

    @Test
    fun provide_30daysDifference_equals30() {
        calendar.set(2021, 5, 31)
        val currentTime = calendar.timeInMillis
        calendar.set(2021, 5, 1)
        val completionTime = calendar.timeInMillis
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(30, days)
    }

    @Test
    fun provide_negativeDifference_equals0() {
        calendar.set(2021, 5, 1)
        val currentTime = calendar.timeInMillis
        calendar.set(2021, 5, 31)
        val completionTime = calendar.timeInMillis
        val days = DaysSinceNewRecordCalculation(currentTime, completionTime).provide()
        assertEquals(0, days)
    }

}