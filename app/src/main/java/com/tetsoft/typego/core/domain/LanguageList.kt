package com.tetsoft.typego.core.domain

import android.content.Context
import com.tetsoft.typego.core.data.LanguageSpinnerItem
import com.tetsoft.typego.core.ui.LanguageFlagMapper
import com.tetsoft.typego.core.utils.Translation
import kotlin.collections.ArrayList

class LanguageList : ArrayList<LanguageSpinnerItem>() {

    /**
     * @return Localized list of languages sorted alphabetically.
     */
    fun getLocalized(context: Context): ArrayList<LanguageSpinnerItem> {
        val languageItems = ArrayList<LanguageSpinnerItem>()
        val translation = Translation(context)
        val languageFlag = LanguageFlagMapper()
        for(language in Language.LANGUAGE_LIST) {
            languageItems.add(
                LanguageSpinnerItem(
                language,
                translation.get(language),
                languageFlag.get(language))
            )
        }
        val sorted : List<LanguageSpinnerItem> = languageItems.sortedBy { it.languageTranslation }
        return ArrayList(sorted)
    }

}