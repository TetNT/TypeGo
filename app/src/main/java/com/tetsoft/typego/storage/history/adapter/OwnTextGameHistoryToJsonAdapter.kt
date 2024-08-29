package com.tetsoft.typego.storage.history.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.data.history.OwnTextGameHistoryList
import com.tetsoft.typego.data.game.OwnText

class OwnTextGameHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: OwnTextGameHistoryList): List<OwnText> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<OwnText>): OwnTextGameHistoryList =
        OwnTextGameHistoryList(list)
}