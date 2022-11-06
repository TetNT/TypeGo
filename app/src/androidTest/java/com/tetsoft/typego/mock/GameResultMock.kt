package com.tetsoft.typego.mock

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.result.GameResult
import java.util.*

class GameResultMock {

    fun getResultGameOnTime(timeInSeconds: Int, score: Int, calendar: Calendar) : GameResult {
        return GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(timeInSeconds),
            score, timeInSeconds, 30, 29, calendar.timeInMillis
        )
    }

    fun getResultGameOnTime(language: Language, timeInSeconds: Int, score: Int, calendar: Calendar) : GameResult {
        return GameResult(
            GameOnTimeMock().getBasicPortraitWithSuggestions(language, timeInSeconds),
            score, timeInSeconds, 30, 29, calendar.timeInMillis
        )
    }

    fun getSimpleGameResult(score: Int) : GameResult {
        val calendarDate = Calendar.getInstance()
        calendarDate.set(2020, 1, 1)
        return GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(15),
            score, 15, 30, 29, calendarDate.timeInMillis
        )
    }

    fun getSimpleGameResult(timeInSeconds: Int, score: Int) : GameResult {
        val calendarDate = Calendar.getInstance()
        calendarDate.set(2020, 1, 1)
        return GameResult(
            GameOnTimeMock().getEnglishBasicPortraitWithSuggestions(timeInSeconds),
            score, timeInSeconds, 30, 29, calendarDate.timeInMillis
        )
    }
}