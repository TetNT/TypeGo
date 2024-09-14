package com.tetsoft.typego.history.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.tetsoft.typego.core.domain.GameResult

interface HistoryStorage<T : GameResult> {
    fun store(list: GameHistoryList<T>)

    fun get(): GameHistoryList<T>

    fun add(element: T)

    abstract class SharedPreferences<T : GameResult>(
        context: Context,
        name: String,
        private val jsonAdapter: JsonAdapter<GameHistoryList<T>>
    ) : HistoryStorage<T> {
        private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

        override fun store(list: GameHistoryList<T>) {
            with(sharedPreferences.edit()) {
                putString(KEY_HISTORY, jsonAdapter.toJson(list))
                apply()
            }
        }

        override fun add(element: T) {
            val history = get()
            history.add(element)
            store(history)
        }

        private companion object {
            const val KEY_HISTORY = "history"
        }
    }
}