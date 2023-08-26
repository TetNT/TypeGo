package com.tetsoft.typego.ui.custom.radiogroup

import android.content.Context
import android.util.AttributeSet
import com.tetsoft.typego.data.DictionaryType

class DictionaryTypeRadioGroup : RadioGroupBase<DictionaryType> {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val options: ArrayList<DictionaryType>
        get() = arrayListOf(
            DictionaryType.BASIC,
            DictionaryType.ENHANCED
        )

    init {
        setOnCheckedChangeListener { _, _ -> }
    }
}