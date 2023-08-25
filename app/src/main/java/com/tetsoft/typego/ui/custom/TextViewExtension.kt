package com.tetsoft.typego.ui.custom

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.setDrawableTint(@ColorRes color: Int) {
    for (compoundDrawable in compoundDrawablesRelative) {
        if (compoundDrawable != null) {
            compoundDrawable.colorFilter = PorterDuffColorFilter(
                (ContextCompat.getColor(context, color)),
                PorterDuff.Mode.SRC_IN
            )
        }
    }
}