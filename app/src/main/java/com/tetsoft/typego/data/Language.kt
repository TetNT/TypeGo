package com.tetsoft.typego.data

import android.content.Context
import android.content.res.Resources
import java.io.Serializable

data class Language(
    val identifier: String
) : Serializable {

    // returns translated name from string resources. If resource is not found, returns identifier.
    fun getName(context: Context): String {
        return try {
            context.getString(getResourceIdByName(context))
        } catch (e: Resources.NotFoundException) {
            identifier
        }
    }

    private fun getResourceIdByName(context: Context): Int {
        return context.resources.getIdentifier(identifier, "string", context.packageName)
    }

    override fun toString(): String {
        return identifier
    }
}