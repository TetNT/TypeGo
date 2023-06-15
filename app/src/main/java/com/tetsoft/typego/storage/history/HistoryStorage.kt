package com.tetsoft.typego.storage.history

import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.game.GameResult

interface HistoryStorage<T : GameResult> {
    fun store(list: GameHistoryList<T>)

    fun get() : GameHistoryList<T>

    fun add(element: T)
}