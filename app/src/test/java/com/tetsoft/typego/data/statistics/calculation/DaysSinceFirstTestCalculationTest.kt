package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.TimeGameCompletionDateTimeMock
import com.tetsoft.typego.data.game.GameResult
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class DaysSinceFirstTestCalculationTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun provide_currentDay6FirstDay1_equals5() {
        calendar.set(2022, 10, 6)
        val currentTime = calendar.timeInMillis
        calendar.set(2022, 10, 1)
        val results = ArrayList<GameResult>()
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 3)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 5)
    }

    @Test
    fun provide_currentDayLessThanCompletionDay_equals0() {
        calendar.set(2022, 10, 1)
        val currentTime = calendar.timeInMillis
        val results = ArrayList<GameResult>()
        calendar.set(2022, 10, 10)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 30)
        results.add(TimeGameCompletionDateTimeMock(calendar.timeInMillis))
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 0)
    }

    @Test
    fun provide_emptyResults_equals0() {
        calendar.set(2022, 10, 1)
        val currentTime = calendar.timeInMillis
        val results = ArrayList<GameResult>()
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 0)
    }
}