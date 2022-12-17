package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.result.GameResult
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class DaysSinceFirstTestCalculationTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun provide_currentDay6FirstDay1_equals5() {
        calendar.set(2022, 10, 6)
        val currentTime = calendar.timeInMillis
        val results = ArrayList<GameResult>()
        calendar.set(2022, 10, 1)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 2)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 3)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        val calculation = DaysSinceFirstTestCalculation(results, currentTime)
        assertEquals(calculation.provide(), 5)
    }

    @Test
    fun provide_currentDayLessThanCompletionDay_equals0() {
        calendar.set(2022, 10, 1)
        val currentTime = calendar.timeInMillis
        val results = ArrayList<GameResult>()
        calendar.set(2022, 10, 10)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 20)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
        calendar.set(2022, 10, 30)
        results.add(GameResult(GameMode.Empty(), 1, 1, 1, 1, calendar.timeInMillis))
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