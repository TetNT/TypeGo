package com.tetsoft.typego.history.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.history.domain.GameHistoryList
import com.tetsoft.typego.history.domain.RandomWordsHistoryList
import java.sql.Date

interface RandomWordsHistoryStorage : HistoryStorage<RandomWords> {

    class SharedPreferences(context: Context) : RandomWordsHistoryStorage {

        private val sharedPreferences =
            context.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE)

        private val moshi = Moshi.Builder().add(RandomWordsHistoryToJsonAdapter())
            .add(KotlinJsonAdapterFactory()).build()
        private val jsonAdapter: JsonAdapter<GameHistoryList<RandomWords>> =
            moshi.adapter(RandomWordsHistoryList().javaClass)


        override fun store(list: GameHistoryList<RandomWords>) {
            with(sharedPreferences.edit()) {
                putString(KEY_HISTORY, jsonAdapter.toJson(list))
                apply()
            }
        }

        override fun get(): GameHistoryList<RandomWords> {
            val json = sharedPreferences.getString(KEY_HISTORY, "")!!
            if (json.isEmpty()) return RandomWordsHistoryList()
            return jsonAdapter.fromJson(json) ?: return RandomWordsHistoryList()
        }

        override fun add(element: RandomWords) {
            val history = get()
            history.add(element)
            store(history)
        }

        private companion object {
            const val STORAGE_FILE = "history_game_on_time"
            const val KEY_HISTORY = "history"
        }

    }

    class Mock : RandomWordsHistoryStorage {

        private val list = RandomWordsHistoryList()

        init {
            list.add(RandomWords(50.0, 200, 150, 15, "RU", "BASIC", "PORTRAIT", true, true, 50, 50, Date.valueOf("2023-06-27").time, ""))
            list.add(RandomWords(50.0, 200, 150, 15, "RU", "BASIC", "PORTRAIT", true, true, 50, 50, Date.valueOf("2023-06-27").time, ""))
            list.add(RandomWords(50.0, 200, 150, 15, "RU", "BASIC", "PORTRAIT", true, true, 50, 50, Date.valueOf("2023-06-27").time, ""))
            list.add(RandomWords(30.0, 200, 150, 15, "EN", "ENHANCED", "LANDSCAPE", false, true, 50, 50, Date.valueOf("2023-06-28").time, ""))
            list.add(RandomWords(30.0, 200, 150, 15, "FR", "ENHANCED", "LANDSCAPE", false, true, 50, 50, Date.valueOf("2023-06-29").time, ""))
        }

        override fun get(): GameHistoryList<RandomWords> {
            return list
        }

        override fun add(element: RandomWords) {
            list.add(element)
        }

        override fun store(list: GameHistoryList<RandomWords>) {
            list.clear()
            list.addAll(list)
        }
    }
}