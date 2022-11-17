package com.tetsoft.typego.mock

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import java.util.*
import kotlin.collections.ArrayList

class GameResultGameOnTimeListMock {

    fun getSingleLanguageList() : GameResultList {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        val mock = GameOnTimeResultMock()
        val games = ArrayList<GameResult>()
        games.add(mock.getResultGameOnTime(Language("EN"),60, 311, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language("EN"),15, 89, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language("EN"),15, 115, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language("EN"),60, 233, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language("EN"),30, 135, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language("EN"),120, 512, calendar.timeInMillis))
        return GameResultList(games)
    }

    fun getSingleLanguageList(languageId: String) : GameResultList {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        val mock = GameOnTimeResultMock()
        val games = ArrayList<GameResult>()
        games.add(mock.getResultGameOnTime(Language(languageId),60, 311, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language(languageId),15, 89, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language(languageId),15, 115, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language(languageId),60, 233, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language(languageId),30, 135, calendar.timeInMillis))
        games.add(mock.getResultGameOnTime(Language(languageId),120, 512, calendar.timeInMillis))
        return GameResultList(games)
    }

    fun getMultipleLanguagesList() : GameResultList {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        val mock = GameOnTimeResultMock()
        return GameResultList(arrayListOf(
            mock.getResultGameOnTime(Language("EN"),60, 311, calendar.timeInMillis),
            mock.getResultGameOnTime(Language("EN"),15, 89, calendar.timeInMillis),
            mock.getResultGameOnTime(Language("EN"),15, 115, calendar.timeInMillis),
            mock.getResultGameOnTime(Language("FR"),60, 233, calendar.timeInMillis),
            mock.getResultGameOnTime(Language("EN"),30, 135, calendar.timeInMillis),
            mock.getResultGameOnTime(Language("FR"),120, 512, calendar.timeInMillis),
        ))
    }

}