package com.tetsoft.typego.storage

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnCount
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.adapter.result.GameResultListToJsonAdapter
import com.tetsoft.typego.utils.StringKeys

class GameResultListStorage(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(KEY_RESULT_LIST_STORAGE, Context.MODE_PRIVATE)
    private val moshi =
        Moshi.Builder().add(PolymorphicJsonAdapterFactory.of(GameMode::class.java, "GameMode")
            .withSubtype(GameOnTime::class.java, "GameOnTime")
            .withSubtype(GameOnCount::class.java, "GameOnCount"))
            .add(GameResultListToJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    private val jsonAdapter: JsonAdapter<GameResultList> = moshi.adapter(GameResultList::class.java)

    fun store(gameResultList: GameResultList) {
        Log.d("MOSHI", "store JSON: " + jsonAdapter.toJson(gameResultList))

        with(sharedPreferences.edit()) {
            putInt(StringKeys.STORAGE_APP_VERSION, BuildConfig.VERSION_CODE)
            putString(KEY_RESULTS, jsonAdapter.toJson(gameResultList))
            apply()
        }

    }

    fun get(): GameResultList {
        val json = sharedPreferences.getString(KEY_RESULTS, "")!!
        if (json.isEmpty()) return GameResultList()
        return jsonAdapter.fromJson(json) ?: return GameResultList()
    }

    fun addResult(gameResult : GameResult) {
        val resultList = get()
        resultList.add(gameResult)
        store(resultList)
    }

    private fun getVersion(): Int {
        return sharedPreferences.getInt(StringKeys.STORAGE_APP_VERSION, EMPTY_VERSION)
    }

    fun isUpToDate(): Boolean {
        return getVersion() == BuildConfig.VERSION_CODE
    }

    fun isEmpty(): Boolean {
        return getVersion() == EMPTY_VERSION
    }

    companion object {
        private const val KEY_RESULT_LIST_STORAGE = "result_list_storage"
        private const val KEY_RESULTS = "results"
        private const val EMPTY_VERSION = 0
    }
}