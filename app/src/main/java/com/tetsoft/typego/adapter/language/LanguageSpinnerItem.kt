package com.tetsoft.typego.adapter.language

import androidx.annotation.DrawableRes
import com.tetsoft.typego.data.Language

data class LanguageSpinnerItem (
    val language: Language,
    val languageTranslation: String,  // localized string to show in a spinner
    @DrawableRes val flagResId: Int
    )