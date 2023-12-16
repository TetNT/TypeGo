package com.tetsoft.typego.adapter.language

import com.tetsoft.typego.R
import com.tetsoft.typego.data.language.Language

class LanguageFlagMapper {
    fun get(language: Language) : Int {
        return when (language.identifier) {
            "ALL" -> R.drawable.ic_flag_global
            "EN" -> R.drawable.ic_flag_english
            "RU" -> R.drawable.ic_flag_russian
            "FR" -> R.drawable.ic_flag_french
            "IT" -> R.drawable.ic_flag_italian
            "ES" -> R.drawable.ic_flag_spanish
            "DE" -> R.drawable.ic_flag_german
            "KR" -> R.drawable.ic_flag_korean
            "BG" -> R.drawable.ic_flag_bulgarian
            "UA" -> R.drawable.ic_flag_ukrainian
            "CZ" -> R.drawable.ic_flag_czech
            "PL" -> R.drawable.ic_flag_polish
            else -> R.drawable.ic_language
        }
    }
}