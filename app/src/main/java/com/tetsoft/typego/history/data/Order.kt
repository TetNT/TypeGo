package com.tetsoft.typego.history.data

import android.content.Context
import com.tetsoft.typego.core.domain.Translatable
import com.tetsoft.typego.core.utils.Translation

enum class Order : Translatable {
    ASCENDING {
        override fun getTranslation(context: Context): String {
            return Translation(context).get(this)
        }
    },
    DESCENDING {
        override fun getTranslation(context: Context): String {
            return Translation(context).get(this)
        }
    }
}