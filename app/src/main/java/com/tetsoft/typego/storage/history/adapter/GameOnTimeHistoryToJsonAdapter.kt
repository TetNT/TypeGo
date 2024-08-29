package com.tetsoft.typego.storage.history.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.data.game.RandomWords

class GameOnTimeHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameOnTimeHistoryList) : List<RandomWords> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<RandomWords>) = GameOnTimeHistoryList(list)
}