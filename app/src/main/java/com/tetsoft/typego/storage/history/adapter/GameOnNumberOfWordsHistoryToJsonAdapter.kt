package com.tetsoft.typego.storage.history.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.data.history.GameOnNumberOfWordsHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameResult

class GameOnNumberOfWordsHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameOnNumberOfWordsHistoryList): List<GameOnNumberOfWords> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<GameOnNumberOfWords>) =
        GameOnNumberOfWordsHistoryList(list)
}