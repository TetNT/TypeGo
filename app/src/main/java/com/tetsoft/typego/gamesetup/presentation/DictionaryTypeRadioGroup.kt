package com.tetsoft.typego.gamesetup.presentation

import android.content.Context
import android.util.AttributeSet
import com.tetsoft.typego.core.ui.RadioGroupBase
import com.tetsoft.typego.core.domain.DictionaryType

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