package com.tetsoft.typego.core.domain

import android.content.Context

interface Translatable {
    fun getTranslation(context: Context) : String
}