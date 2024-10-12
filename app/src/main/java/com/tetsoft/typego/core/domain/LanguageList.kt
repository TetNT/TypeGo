package com.tetsoft.typego.core.domain

import android.content.Context
import com.tetsoft.typego.core.data.LanguageSpinnerItem
import com.tetsoft.typego.core.data.LanguageFlagMapper
import com.tetsoft.typego.core.utils.Translation

class LanguageList : ArrayList<LanguageSpinnerItem>() {

    /**
     * @return Localized list of languages sorted alphabetically.
     */
    fun getLocalized(context: Context): ArrayList<LanguageSpinnerItem> {
        val languageItems = ArrayList<LanguageSpinnerItem>()
        val translation = Translation(context)
        val languageFlag = LanguageFlagMapper()
        for(language in getPlayableLanguages()) {
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

    fun getPlayableLanguages() : List<Language> {
        return arrayListOf(
            Language(Language.EN),
            Language(Language.FR),
            Language(Language.DE),
            Language(Language.IT),
            Language(Language.RU),
            Language(Language.ES),
            Language(Language.BG),
            Language(Language.UA),
            Language(Language.CZ),
            Language(Language.PL),
            Language(Language.PT),
            Language(Language.TR),
            Language(Language.ID)
        )
    }

}