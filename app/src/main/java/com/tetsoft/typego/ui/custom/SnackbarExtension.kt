package com.tetsoft.typego.ui.custom

import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar

fun Snackbar.withColor(@ColorRes backgroundColorId: Int, @ColorRes textColorId: Int) : Snackbar {
    this.view.setBackgroundColor(context.getColor(backgroundColorId))
    this.setTextColor(context.getColor(textColorId))
    return this
}