package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DaysSinceFirstTestCalculationTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun provide_currentDay6FirstDay1_equals5() {
        calendar.set(2022, 10, 6)
        val currentTime = calendar.timeInMillis
        calendar.set(2022, 10, 1)
        val results = ClassicGameModesHistoryList()
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 3)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 5)
    }

    @Test
    fun provide_currentDayLessThanCompletionDay_equals0() {
        calendar.set(2022, 10, 1)
        val currentTime = calendar.timeInMillis
        val results = ClassicGameModesHistoryList()
        calendar.set(2022, 10, 10)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        calendar.set(2022, 10, 30)
        results.add(GameOnTimeMock(calendar.timeInMillis))
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 0)
    }

    @Test
    fun provide_emptyResults_equals0() {
        calendar.set(2022, 10, 1)
        val currentTime = calendar.timeInMillis
        val results = ClassicGameModesHistoryList()
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 0)
    }

    private class GameOnTimeMock(dateTime: Long) :
        GameOnTime(0.0, 0, 0, 0, "", "", "", false, 0, 0, dateTime)

}