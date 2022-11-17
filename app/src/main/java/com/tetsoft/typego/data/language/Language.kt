package com.tetsoft.typego.data.language

import java.io.Serializable

data class Language(
    val identifier: String
) : Serializable {

    override fun toString(): String {
        return identifier
    }

    override fun equals(other: Any?): Boolean {
        return identifier == other.toString()
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }

    companion object {
        const val ALL = "ALL"
    }
}