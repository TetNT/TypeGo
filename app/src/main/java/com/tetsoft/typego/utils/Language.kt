package com.tetsoft.typego.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.Log
import java.io.Serializable
import java.util.ArrayList

class Language : Serializable {
    // identifier is a unique two uppercase letters combination
    val identifier: String
    private val name: String

    constructor(identifier: String, context: Context) {
        this.identifier = identifier
        name = context.getString(getResourceIdByName(context))
    }

    constructor(identifier: String, name: String) {
        this.identifier = identifier
        this.name = name
    }

    // returns translated name from string resources. If resource is not found, returns identifier.
    fun getName(context: Context): String {
        return try {
            context.getString(getResourceIdByName(context))
        } catch (e: NotFoundException) {
            Log.i("EX", "Failed to get resource: " + e.message)
            identifier
        }
    }

    private fun getResourceIdByName(context: Context): Int {
        return context.resources.getIdentifier(identifier, "string", context.packageName)
    }

    override fun toString(): String {
        return name
    }

    companion object {
        /*Returns list of languages available for testing.
      New languages are adding here.
    */
        @JvmStatic
        fun getAvailableLanguages(context: Context): ArrayList<Language> {
            val languages = ArrayList<Language>()
            languages.add(Language(StringKeys.LANGUAGE_EN, context))
            languages.add(Language(StringKeys.LANGUAGE_FR, context))
            languages.add(Language(StringKeys.LANGUAGE_DE, context))
            languages.add(Language(StringKeys.LANGUAGE_IT, context))
            languages.add(Language(StringKeys.LANGUAGE_KR, context))
            languages.add(Language(StringKeys.LANGUAGE_RU, context))
            languages.add(Language(StringKeys.LANGUAGE_ES, context))
            languages.add(Language(StringKeys.LANGUAGE_BG, context))
            languages.add(Language(StringKeys.LANGUAGE_UA, context))
            return languages
        }
    }
}