package com.tetsoft.typego.history.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.core.domain.OwnText

class OwnTextGameHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: OwnTextGameHistoryList): List<OwnText> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<OwnText>): OwnTextGameHistoryList =
        OwnTextGameHistoryList(list)
}