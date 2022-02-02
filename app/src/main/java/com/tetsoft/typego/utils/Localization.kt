package com.tetsoft.typego.utils

import android.content.Context
import android.content.res.Resources
import com.tetsoft.typego.data.Language

object Localization {
    @JvmStatic
    fun getLanguageNameOrId(language: Language, context: Context): String {
        return try {
            context.getString(getResourceIdByName(language.identifier, context))
        } catch (e: Resources.NotFoundException) {
            language.identifier
        }
    }

    private fun getResourceIdByName(languageIdentifier: String, context: Context): Int {
        return context.resources.getIdentifier(languageIdentifier, "string", context.packageName)
    }
}