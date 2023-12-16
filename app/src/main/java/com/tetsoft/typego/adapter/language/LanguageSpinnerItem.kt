package com.tetsoft.typego.adapter.language

import androidx.annotation.DrawableRes
import com.tetsoft.typego.data.language.Language

data class LanguageSpinnerItem(
    val language: Language,
    val languageTranslation: String,  // localized string to show in a spinner
    @DrawableRes val flag: Int
)