package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.utils.StringKeys

class UserPreferencesStorage(context: Context) : StringValueStorage {
    private val sharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES_STORAGE, Context.MODE_PRIVATE)

    override fun getString(key: String?): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun store(key: String?, value: String?) {
        with(sharedPreferences.edit()) {
            putInt(StringKeys.STORAGE_APP_VERSION, BuildConfig.VERSION_CODE)
            putString(key, value)
            apply()
        }
    }

    companion object {
        const val USER_PREFERENCES_STORAGE = "user_preferences_storage"
    }
}