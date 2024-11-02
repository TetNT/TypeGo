package com.tetsoft.typego.keynotes.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface KeyNotesStateStorage {
    fun store(key: String, value: Boolean)

    fun getBoolean(key: String) : Boolean

    class Standard @Inject constructor(@ApplicationContext context: Context) : KeyNotesStateStorage {
        private val sharedPreferences =
            context.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE)
        override fun store(key: String, value: Boolean) {
            with(sharedPreferences.edit()) {
                putBoolean(key, value)
                apply()
            }
        }

        override fun getBoolean(key: String): Boolean {
            return sharedPreferences.getBoolean(key, false)
        }

        private companion object {
            const val STORAGE_FILE = "key_notes_state_storage"
        }
    }

    class Mock @Inject constructor() : KeyNotesStateStorage {

        val list = mutableMapOf<String, Boolean>()

        override fun store(key: String, value: Boolean) {
            list[key] = value
        }

        override fun getBoolean(key: String): Boolean {
            return list[key] ?: return false
        }

    }
}