package com.tetsoft.typego.ui.custom

import android.content.Context
import android.util.AttributeSet

class ValRadioButton : androidx.appcompat.widget.AppCompatRadioButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    var assignedValue : Any? = null
}