package com.tetsoft.typego.keynotes.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class KeyNote(
    val identifier: String, // identifier for storing
    @DrawableRes val imageId: Int,
    @StringRes val featureHeader: Int,
    @StringRes val featureDescription: Int
)
