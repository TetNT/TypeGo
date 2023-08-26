package com.tetsoft.typego.ui.custom.radiogroup

import android.content.Context
import android.util.AttributeSet
import com.tetsoft.typego.data.ScreenOrientation

class ScreenOrientationRadioGroup : RadioGroupBase<ScreenOrientation> {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val options: ArrayList<ScreenOrientation>
        get() = arrayListOf(
            ScreenOrientation.PORTRAIT,
            ScreenOrientation.LANDSCAPE
        )

    init {
        setOnCheckedChangeListener { _, _ -> }
    }
}