package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.utils.StringKeys

class AdsCounterStorage (context: Context) : FloatValueStorage {
    private val sharedPreferences =
        context.getSharedPreferences(StringKeys.ADS_COUNTER_STORAGE, Context.MODE_PRIVATE)
    override fun store(key: String?, value: Float) {
        with(sharedPreferences.edit()) {
            putFloat(key, value)
            apply()
        }
    }

    override fun getFloat(key: String?): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

}