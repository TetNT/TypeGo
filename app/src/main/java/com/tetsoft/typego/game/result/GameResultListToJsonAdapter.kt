package com.tetsoft.typego.game.result

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlin.collections.toList

class GameResultListToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameResultList): List<GameResult?> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<GameResult>): GameResultList = GameResultList(list)

}