package com.tetsoft.typego.ui.custom

import androidx.annotation.IdRes

interface Navigation {
    fun navigate(@IdRes actionId : Int)
}