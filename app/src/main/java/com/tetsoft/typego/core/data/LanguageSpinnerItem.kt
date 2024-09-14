package com.tetsoft.typego.core.data

import androidx.annotation.DrawableRes
import com.tetsoft.typego.core.domain.Language

data class LanguageSpinnerItem(
    val language: Language,
    val languageTranslation: String,  // localized string to show in a spinner
    @DrawableRes val flag: Int
)