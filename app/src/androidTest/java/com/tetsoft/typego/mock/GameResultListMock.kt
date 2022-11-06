package com.tetsoft.typego.mock

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import java.util.*
import kotlin.collections.ArrayList

class GameResultListMock {

    fun getSingleLanguageList() : GameResultList {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        val mock = GameResultMock()
        val games = ArrayList<GameResult>()
        games.add(mock.getResultGameOnTime(60, 311, calendar))
        games.add(mock.getResultGameOnTime(15, 89, calendar))
        games.add(mock.getResultGameOnTime(15, 115, calendar))
        games.add(mock.getResultGameOnTime(60, 233, calendar))
        games.add(mock.getResultGameOnTime(30, 135, calendar))
        games.add(mock.getResultGameOnTime(120, 512, calendar))
        return GameResultList(games)
    }

    fun getMultipleLanguagesList() : GameResultList {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        val mock = GameResultMock()
        val games = ArrayList<GameResult>()
        games.add(mock.getResultGameOnTime(Language("EN"),60, 311, calendar))
        games.add(mock.getResultGameOnTime(Language("EN"),15, 89, calendar))
        games.add(mock.getResultGameOnTime(Language("EN"),15, 115, calendar))
        games.add(mock.getResultGameOnTime(Language("FR"),60, 233, calendar))
        games.add(mock.getResultGameOnTime(Language("EN"),30, 135, calendar))
        games.add(mock.getResultGameOnTime(Language("FR"),120, 512, calendar))
        return GameResultList(games)
    }
}