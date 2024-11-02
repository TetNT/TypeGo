package com.tetsoft.typego.core.utils.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyToClipboard(clipboardLabel: String,text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(clipboardLabel,text)
    clipboard.setPrimaryClip(clip)
}