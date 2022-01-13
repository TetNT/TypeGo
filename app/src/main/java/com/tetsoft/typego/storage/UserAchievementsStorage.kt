package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.utils.StringKeys

class UserAchievementsStorage(context: Context) : StringValueStorage {
    private val sharedPreferences =
        context.getSharedPreferences(StringKeys.USER_STORAGE, Context.MODE_PRIVATE)

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