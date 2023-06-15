package com.tetsoft.typego.data.language

import com.tetsoft.typego.data.DictionaryType

@Deprecated("Replaced with the new GameResult interface")
interface PrebuiltTextGameMode {
    fun getLanguage() : Language

    fun getDictionary() : DictionaryType
}