package com.tetsoft.typego.ui.custom

import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.AppCompatEditText
import android.text.style.ForegroundColorSpan
import android.text.style.BackgroundColorSpan
import android.text.Spannable
import android.util.AttributeSet

class SpannableEditText : AppCompatEditText {
    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    fun paintForeground(charIndex: Int, foregroundColor: ForegroundColorSpan?) {
        if (this.text == null) return
        if (charIndex + 1 > this.text!!.length) return
        this.text!!.setSpan(foregroundColor, charIndex, charIndex + 1, EXCLUSIVE)
    }

    fun paintForeground(charIndex: Int, endIndex: Int, foregroundColor: ForegroundColorSpan?) {
        if (this.text == null) return
        if (charIndex + 1 > this.text!!.length || endIndex + 1 > this.text!!.length) return
        this.text!!.setSpan(foregroundColor, charIndex, endIndex, EXCLUSIVE)
    }

    fun paintBackground(startIndex: Int, endIndex: Int, backgroundColor: BackgroundColorSpan?) {
        if (this.text == null) return
        if (startIndex + 1 > this.text!!.length || endIndex + 1 > this.text!!.length) return
        this.text!!.setSpan(backgroundColor, startIndex, endIndex, EXCLUSIVE)
    }

    fun clearForeground(startIndex: Int, endIndex: Int) {
        if (this.text == null) return
        val removableSpans = this.text!!
            .getSpans(startIndex, endIndex, Any::class.java)
        for (span in removableSpans) {
            if (span is ForegroundColorSpan) {
                this.text!!.removeSpan(span)
            }
        }
    }

    fun clearBackground(startIndex: Int, endIndex: Int) {
        if (this.text == null) return
        val removableSpans = this.text!!
            .getSpans(startIndex, endIndex, Any::class.java)
        for (span in removableSpans) {
            if (span is BackgroundColorSpan) {
                this.text!!.removeSpan(span)
            }
        }
    }

    companion object {
        private val GREEN_COLOR = Color.rgb(0, 128, 0)
        private val RED_COLOR = Color.rgb(192, 0, 0)
        private const val EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        @JvmStatic
        val greenForeground: ForegroundColorSpan
            get() = ForegroundColorSpan(GREEN_COLOR)
        @JvmStatic
        val redForeground: ForegroundColorSpan
            get() = ForegroundColorSpan(RED_COLOR)
    }
}