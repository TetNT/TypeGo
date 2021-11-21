package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.utils.StringKeys

class UserStorage(context: Context) : Storage {
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

    fun delete(key: String?) {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }
}