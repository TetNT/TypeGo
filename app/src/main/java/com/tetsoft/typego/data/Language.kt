package com.tetsoft.typego.data

import java.io.Serializable

data class Language(
    val identifier: String
) : Serializable {

    override fun toString(): String {
        return identifier
    }
}