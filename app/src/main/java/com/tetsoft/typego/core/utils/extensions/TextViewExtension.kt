package com.tetsoft.typego.core.utils.extensions

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
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

fun TextView.setDrawableStart(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}