package com.tetsoft.typego.storage

import android.content.Context

class AdsCounterStorage (context: Context) : FloatValueStorage {
    private val sharedPreferences =
        context.getSharedPreferences(KEY_ADS_COUNTER_STORAGE, Context.MODE_PRIVATE)
    override fun store(key: String?, value: Float) {
        with(sharedPreferences.edit()) {
            putFloat(key, value)
            apply()
        }
    }

    override fun getFloat(key: String?): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    companion object {
        private const val KEY_ADS_COUNTER_STORAGE = "ads_counter_storage"
    }

}