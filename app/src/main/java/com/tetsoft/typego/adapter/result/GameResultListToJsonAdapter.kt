package com.tetsoft.typego.adapter.result

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import kotlin.collections.toList

@Deprecated("Must be deleted once the migration from GameResult will be done")
class GameResultListToJsonAdapter {

    @ToJson
    fun arrayListToJson(list: GameResultList): List<GameResult?> {
        return list.toList()
    }

    @FromJson
    fun arrayListFromJson(list: List<GameResult>): GameResultList = GameResultList(list)

}