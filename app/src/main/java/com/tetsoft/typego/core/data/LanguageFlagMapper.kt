package com.tetsoft.typego.core.data

import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.Language

class LanguageFlagMapper {
    fun get(language: Language) : Int {
        return when (language.identifier) {
            Language.ALL -> R.drawable.ic_flag_global
            Language.EN -> R.drawable.ic_flag_english
            Language.RU -> R.drawable.ic_flag_russian
            Language.FR -> R.drawable.ic_flag_french
            Language.IT -> R.drawable.ic_flag_italian
            Language.ES -> R.drawable.ic_flag_spanish
            Language.DE -> R.drawable.ic_flag_german
            Language.BG -> R.drawable.ic_flag_bulgarian
            Language.UA -> R.drawable.ic_flag_ukrainian
            Language.CZ -> R.drawable.ic_flag_czech
            Language.PL -> R.drawable.ic_flag_polish
            Language.PT -> R.drawable.ic_flag_portuguese
            Language.TR -> R.drawable.ic_flag_turkish
            Language.ID -> R.drawable.ic_flag_indonesian
            Language.AR -> R.drawable.ic_flag_arabic
            else -> R.drawable.ic_language
        }
    }
}