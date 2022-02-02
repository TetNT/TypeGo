package com.tetsoft.typego.data

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import com.tetsoft.typego.utils.StringKeys
import kotlin.collections.ArrayList

class LanguageList : ArrayList<LanguageSpinnerItem>() {

    /**
     * Returns a list of languages that holds translations to represent in a different locales.
     */
    fun getTranslatableList(context: Context): ArrayList<LanguageSpinnerItem> {
        val languageItems = ArrayList<LanguageSpinnerItem>()
        for(language in getList()) {
            languageItems.add(LanguageSpinnerItem(
                language,
                getLanguageNameOrId(language, context),
                R.drawable.ic_language))
        }
        return languageItems
    }

    /**
     * Returns a list of languages available for testing.
     * New languages are adding here.
     */
    fun getList() : ArrayList<Language> {
        return arrayListOf(
            Language(StringKeys.LANGUAGE_EN),
            Language(StringKeys.LANGUAGE_FR),
            Language(StringKeys.LANGUAGE_DE),
            Language(StringKeys.LANGUAGE_IT),
            Language(StringKeys.LANGUAGE_KR),
            Language(StringKeys.LANGUAGE_RU),
            Language(StringKeys.LANGUAGE_ES),
            Language(StringKeys.LANGUAGE_BG),
            Language(StringKeys.LANGUAGE_UA)
            )
    }

    private fun getLanguageNameOrId(language : Language, context: Context) : String {
        return language.getName(context)
    }
}