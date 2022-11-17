package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.statistics.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.mock.GameOnTimeMock
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

    @Test
    fun visibility_resultIsEmpty_equalsGone() {
        val result = GameResult.Empty()
        //val visibility =
       //     DaysSinceNewRecordCalculation(
       //         calendar.timeInMillis,
        //        result.completionDateTime
        //    ).visibility()
        //assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    @Test
    fun visibility_completionDateIsEmpty_equalsGone() {
        val result = GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(60),
            30,
            60,
            30,
            30,
            0L
        )
        //val visibility =
        //   DaysSinceNewRecordCalculation(calendar.timeInMillis, result.completionDateTime).visibility()
        //assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    @Test
    fun visibility_completionTimeNotEmpty_equalsVisible() {
        val result = GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(60),
            30,
            60,
            30,
            30,
            calendar.timeInMillis
        )
        //val visibility =
        //    DaysSinceNewRecordCalculation(
        //        calendar.timeInMillis,
        //        result.completionDateTime
        //    ).visibility()
        //assertEquals(VisibilityProvider.Visible().get(), visibility.get())
    }
}