package com.tetsoft.typego.history.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.history.domain.RandomWordsHistoryList

class RandomWordsHistoryToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: RandomWordsHistoryList) : List<RandomWords> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<RandomWords>) = RandomWordsHistoryList(list)
}