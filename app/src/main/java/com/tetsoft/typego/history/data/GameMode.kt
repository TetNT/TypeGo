package com.tetsoft.typego.history.data

import android.content.Context
import com.tetsoft.typego.core.domain.Translatable
import com.tetsoft.typego.core.utils.Translation

enum class GameMode : Translatable {
    RANDOM_WORDS {
        override fun getTranslation(context: Context): String {
            return Translation(context).get(this)
        }
    },
    OWN_TEXT {
        override fun getTranslation(context: Context): String {
            return Translation(context).get(this)
        }
    }
}