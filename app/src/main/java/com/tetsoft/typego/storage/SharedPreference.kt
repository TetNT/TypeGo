package com.tetsoft.typego.storage

import android.content.Context
import android.content.SharedPreferences

interface SharedPreference {
    fun make(context: Context) : SharedPreferences

    abstract class Standard(private val name : String, private val mode : Int = Context.MODE_PRIVATE) : SharedPreference {
        override fun make(context: Context): SharedPreferences {
            return context.getSharedPreferences(name, mode)
        }
    }
}