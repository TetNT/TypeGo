package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.TimeGameCompletionDateTimeMock
import com.tetsoft.typego.statistics.data.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DaysSinceNewRecordStatisticsTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun visibility_resultIsEmpty_equalsGone() {
        val result = RandomWords.Empty()
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
        val result = TimeGameCompletionDateTimeMock(0)
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
        val result = TimeGameCompletionDateTimeMock(calendar.timeInMillis)
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
        val result = TimeGameCompletionDateTimeMock(calendar.timeInMillis)
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.getCompletionDateTime()
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

}