package com.tetsoft.typego.storage.history

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.data.history.GameOnNumberOfWordsHistoryList
import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.storage.history.adapter.GameOnNumberOfWordsHistoryToJsonAdapter

interface GameOnNumberOfWordsHistoryStorage : HistoryStorage<GameOnNumberOfWords> {

    class SharedPreferences(context: Context) : GameOnNumberOfWordsHistoryStorage {
        private val sharedPreferences =
            context.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE)

        private val moshi = Moshi.Builder()
            .add(GameOnNumberOfWordsHistoryToJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        private val jsonAdapter: JsonAdapter<GameHistoryList<GameOnNumberOfWords>> =
            moshi.adapter(GameOnNumberOfWordsHistoryList().javaClass)

        override fun store(list: GameHistoryList<GameOnNumberOfWords>) {
            with(sharedPreferences.edit()) {
                putString(KEY_HISTORY, jsonAdapter.toJson(list))
                apply()
            }
        }


        override fun get(): GameHistoryList<GameOnNumberOfWords> {
            val json = sharedPreferences.getString(KEY_HISTORY, "")!!
            if (json.isEmpty()) return GameOnNumberOfWordsHistoryList()
            return jsonAdapter.fromJson(json) ?: return GameOnNumberOfWordsHistoryList()
        }

        override fun add(element: GameOnNumberOfWords) {
            val history = get()
            history.add(element)
            store(history)
        }

        private companion object {
            const val STORAGE_FILE = "history_game_on_number_of_words"
            const val KEY_HISTORY = "history"
        }
    }

    class Mock : GameOnNumberOfWordsHistoryStorage {
        private val list = GameOnNumberOfWordsHistoryList()

        override fun get(): GameHistoryList<GameOnNumberOfWords> {
            return list
        }

        override fun add(element: GameOnNumberOfWords) {
            list.add(element)
        }

        override fun store(list: GameHistoryList<GameOnNumberOfWords>) {
            list.clear()
            list.addAll(list)
        }
    }
}

