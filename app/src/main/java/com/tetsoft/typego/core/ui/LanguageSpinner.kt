package com.tetsoft.typego.core.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import com.tetsoft.typego.core.data.LanguageSpinnerItem
import com.tetsoft.typego.core.domain.Language

class LanguageSpinner(context: Context, attrs: AttributeSet) : AppCompatSpinner(context, attrs) {

    override fun getSelectedItem(): LanguageSpinnerItem {
        return super.getSelectedItem() as LanguageSpinnerItem
    }

    fun getSelectedLanguage() : Language {
        return selectedItem.language
    }
}