package com.tetsoft.typego.storage.history

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.history.adapter.GameOnTimeHistoryToJsonAdapter

open class GameOnTimeHistoryStorage(context: Context) : HistoryStorage<GameOnTime> {

    private val sharedPreferences =
        context.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE)

    private val moshi = Moshi.Builder().add(GameOnTimeHistoryToJsonAdapter())
        .add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<GameHistoryList<GameOnTime>> =
        moshi.adapter(GameOnTimeHistoryList().javaClass)


    override fun store(list: GameHistoryList<GameOnTime>) {
        with(sharedPreferences.edit()) {
            putString(KEY_HISTORY, jsonAdapter.toJson(list))
            apply()
        }
    }

    override fun get(): GameHistoryList<GameOnTime> {
        val json = sharedPreferences.getString(KEY_HISTORY, "")!!
        if (json.isEmpty()) return GameOnTimeHistoryList()
        return jsonAdapter.fromJson(json) ?: return GameOnTimeHistoryList()
    }

    override fun add(element: GameOnTime) {
        val history = get()
        history.add(element)
        store(history)
    }

    private companion object {
        const val STORAGE_FILE = "history_game_on_time"
        const val KEY_HISTORY = "history"
    }

    class Mock(context : Context) : GameOnTimeHistoryStorage(context) {

        private val list = GameOnTimeHistoryList()

        override fun get(): GameHistoryList<GameOnTime> {
            return list
        }

        override fun add(element: GameOnTime) {
            list.add(element)
        }

        override fun store(list: GameHistoryList<GameOnTime>) {
            list.clear()
            list.addAll(list)
        }
    }
}