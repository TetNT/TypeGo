package com.tetsoft.typego.core.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup

abstract class RadioGroupBase<T> : RadioGroup {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var selectedIndex = -1

    abstract val options : ArrayList<T>

    fun getSelectedValue() : T {
        if (options.isEmpty()) throw IllegalStateException("Options are empty")
        return options[selectedIndex]
    }

    fun selectIndex(index: Int) {
        val radioButton = (getChildAt(index) as RadioButton)
        selectedIndex = indexOfChild(radioButton)
        radioButton.isChecked = true
    }

    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        super.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedIndex = indexOfChild(radioButton)
            listener?.onCheckedChanged(group, checkedId)
        }
    }

}