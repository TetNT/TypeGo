package com.tetsoft.typego.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import com.tetsoft.typego.data.language.Language

class LanguageSpinner(context: Context, attrs: AttributeSet) : AppCompatSpinner(context, attrs) {

    override fun getSelectedItem(): LanguageSpinnerItem {
        return super.getSelectedItem() as LanguageSpinnerItem
    }

    fun getSelectedLanguage() : Language {
        return selectedItem.language
    }

}