package com.tetsoft.typego.gamesetup.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class UserTextSetupEditText : AppCompatEditText {

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    init {
        setOnTouchListener { v, _ ->
            if (v.canScrollVertically(1) || v.canScrollVertically(-1)) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.performClick()
            }
            false
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

}