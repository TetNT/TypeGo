package com.tetsoft.typego.storage

import android.content.Context

interface AdsCounterStorage {
    fun store(value: Float)

    fun getFloat(): Float

    class Standard (context: Context) : AdsCounterStorage {
        private val sharedPreferences =
            context.getSharedPreferences(KEY_ADS_COUNTER_STORAGE, Context.MODE_PRIVATE)
        override fun store(value: Float) {
            with(sharedPreferences.edit()) {
                putFloat(ADS_COUNTER_VALUE, value)
                apply()
            }
        }

        override fun getFloat(): Float {
            return sharedPreferences.getFloat(ADS_COUNTER_VALUE, 0f)
        }

        private companion object {
            const val KEY_ADS_COUNTER_STORAGE = "ads_counter_storage"
            const val ADS_COUNTER_VALUE = "ads_counter_value"
        }

    }
}