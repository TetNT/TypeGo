package com.tetsoft.typego.core.domain

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

        // Language identifiers (ISO 639-1 code) in upper case.
        const val RU = "RU"
        const val EN = "EN"
        const val FR = "FR"
        const val DE = "DE"
        const val ES = "ES"
        const val IT = "IT"
        const val UA = "UA"
        const val BG = "BG"
        const val CZ = "CZ"
        const val PL = "PL"
        const val PT = "PT"
        const val TR = "TR"
        const val ID = "ID"
        const val AR = "AR"
    }
}