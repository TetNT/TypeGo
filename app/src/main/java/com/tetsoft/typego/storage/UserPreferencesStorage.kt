package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.utils.StringKeys

class UserPreferencesStorage(context: Context) : Storage {
    private val sharedPreferences =
        context.getSharedPreferences(StringKeys.USER_PREFERENCES_STORAGE, Context.MODE_PRIVATE)

    override fun getString(key: String?): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun store(key: String?, value: String?) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

}