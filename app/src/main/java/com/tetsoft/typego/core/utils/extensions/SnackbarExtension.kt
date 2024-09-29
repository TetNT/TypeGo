package com.tetsoft.typego.core.utils.extensions

import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.R

fun Snackbar.withAppColors() : Snackbar {
    this.view.setBackgroundColor(
        MaterialColors.getColor(context, R.attr.colorPrimary, context.getColor(R.color.lilac))
    )
    this.setTextColor(MaterialColors.getColor(context, R.attr.colorOnPrimary, context.getColor(R.color.background_main)))
    return this
}