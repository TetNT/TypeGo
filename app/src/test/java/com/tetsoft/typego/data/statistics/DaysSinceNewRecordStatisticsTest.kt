package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DaysSinceNewRecordStatisticsTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun visibility_resultIsEmpty_equalsGone() {
        val result = GameOnTime.Empty()
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.getCompletionDateTime()
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    @Test
    fun visibility_completionDateIsEmpty_equalsGone() {
        val result = MockGameOnTime(0)
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.getCompletionDateTime()
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    @Test
    fun visibility_completionTimeNotEmptyNotSameDay_equalsVisible() {
        calendar.set(2022, 10, 5)
        val result = MockGameOnTime(calendar.timeInMillis)
        calendar.set(2022, 10, 9)
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.getCompletionDateTime()
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Visible().get(), visibility.get())
    }

    @Test
    fun visibility_completionTimeNotEmptySameDay_equalsGone() {
        calendar.set(2022, 10, 5)
        val result = MockGameOnTime(calendar.timeInMillis)
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.getCompletionDateTime()
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    private class MockGameOnTime(completionDateTime: Long) :
        GameOnTime(0.0, 0, 0, 0, "", "", "", false, 0, 0, completionDateTime)
}