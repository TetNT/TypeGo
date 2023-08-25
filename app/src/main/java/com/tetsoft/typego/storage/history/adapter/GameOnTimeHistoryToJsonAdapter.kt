package com.tetsoft.typego.storage.history.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.game.GameResult

class GameOnTimeHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameOnTimeHistoryList) : List<GameOnTime> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<GameOnTime>) = GameOnTimeHistoryList(list)
}