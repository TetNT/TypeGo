package com.tetsoft.typego.data.language

data class Language(val identifier: String)  {

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