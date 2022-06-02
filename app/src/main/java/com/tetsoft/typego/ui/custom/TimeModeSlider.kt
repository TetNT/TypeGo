package com.tetsoft.typego.ui.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.slider.Slider
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.timemode.TimeModeList

class TimeModeSlider : Slider {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private var timeModes: TimeModeList? = null

    override fun getValueTo(): Float {
        if (timeModes == null) return 0f
        return timeModes!!.size.toFloat()
    }

    fun setTimeModes(timeModes: TimeModeList) {
        this.timeModes = timeModes
        valueTo = (timeModes.size - 1).toFloat()
    }

    fun getSelectedTimeMode(): TimeMode {
        if (timeModes == null) return TimeMode(60)
        return timeModes!![value.toInt()]
    }

    fun selectTimeMode(timeMode: TimeMode) {
        if (timeModes == null) {
            value = 0f
        }
        value = timeModes!!.getIndexByTimeMode(timeMode).toFloat()
    }
}