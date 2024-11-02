package com.tetsoft.typego.history.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.history.domain.GameHistoryList
import com.tetsoft.typego.history.domain.OwnTextGameHistoryList

interface OwnTextGameHistoryStorage : HistoryStorage<OwnText> {
    class SharedPreferences(context: Context) : OwnTextGameHistoryStorage {
        private val sharedPreferences =
            context.getSharedPreferences(
                STORAGE_FILE,
                Context.MODE_PRIVATE
            )

        private val moshi = Moshi.Builder().add(OwnTextGameHistoryToJsonAdapter())
            .add(KotlinJsonAdapterFactory()).build()
        private val jsonAdapter: JsonAdapter<GameHistoryList<OwnText>> =
            moshi.adapter(OwnTextGameHistoryList().javaClass)

        override fun store(list: GameHistoryList<OwnText>) {
            with(sharedPreferences.edit()) {
                putString(KEY_HISTORY, jsonAdapter.toJson(list))
                apply()
            }
        }

        override fun get(): GameHistoryList<OwnText> {
            val json = sharedPreferences.getString(KEY_HISTORY, "")!!
            if (json.isEmpty()) return OwnTextGameHistoryList()
            return jsonAdapter.fromJson(json) ?: return OwnTextGameHistoryList()
        }

        override fun add(element: OwnText) {
            val history = get()
            history.add(element)
            store(history)
        }

        private companion object {
            const val STORAGE_FILE = "history_own_text"
            const val KEY_HISTORY = "history"
        }
    }
}