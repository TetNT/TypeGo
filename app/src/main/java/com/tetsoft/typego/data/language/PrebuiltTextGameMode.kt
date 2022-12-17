package com.tetsoft.typego.data.language

import com.tetsoft.typego.data.DictionaryType

interface PrebuiltTextGameMode {
    fun getLanguage() : Language

    fun getDictionary() : DictionaryType
}