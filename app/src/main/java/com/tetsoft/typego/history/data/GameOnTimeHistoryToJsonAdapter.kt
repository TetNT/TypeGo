package com.tetsoft.typego.history.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.core.domain.RandomWords

class GameOnTimeHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameOnTimeHistoryList) : List<RandomWords> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<RandomWords>) = GameOnTimeHistoryList(list)
}