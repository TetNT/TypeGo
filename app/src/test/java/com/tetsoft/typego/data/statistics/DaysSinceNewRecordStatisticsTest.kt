package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.mock.GameOnTimeMock
import org.junit.Assert.*

import org.junit.Test
import java.util.*

class DaysSinceNewRecordStatisticsTest {

    private var calendar = Calendar.getInstance()

    @Test
    fun visibility_resultIsEmpty_equalsGone() {
        val result = GameResult.Empty()
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.completionDateTime
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
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
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.completionDateTime
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }

    @Test
    fun visibility_completionTimeNotEmptyNotSameDay_equalsVisible() {
        calendar.set(2022, 10, 5)
        val result = GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(60),
            30,
            60,
            30,
            30,
            calendar.timeInMillis
        )
        calendar.set(2022, 10, 9)
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.completionDateTime
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Visible().get(), visibility.get())
    }

    @Test
    fun visibility_completionTimeNotEmptySameDay_equalsGone() {
        calendar.set(2022, 10, 5)
        val result = GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(60),
            30,
            60,
            30,
            30,
            calendar.timeInMillis
        )
        val visibility =
            DaysSinceNewRecordStatistics(
                DaysSinceNewRecordCalculation(
                    calendar.timeInMillis,
                    result.completionDateTime
                )
            ).getVisibility()
        assertEquals(VisibilityProvider.Gone().get(), visibility.get())
    }
}