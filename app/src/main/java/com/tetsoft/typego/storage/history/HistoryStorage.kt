package com.tetsoft.typego.storage.history

import com.tetsoft.typego.data.history.GameHistoryList

interface HistoryStorage<T> {
    fun store(list: GameHistoryList<T>)

    fun get() : GameHistoryList<T>

    fun add(element: T)
}