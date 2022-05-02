package com.tetsoft.typego.data.language

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import kotlin.collections.ArrayList

class LanguageList : ArrayList<LanguageSpinnerItem>() {

    /**
     * Returns a list of languages that holds translations to represent in a different locales.
     */
    fun getTranslatableListInAlphabeticalOrder(context: Context): ArrayList<LanguageSpinnerItem> {
        val languageItems = ArrayList<LanguageSpinnerItem>()
        for(language in getList()) {
            languageItems.add(LanguageSpinnerItem(
                language,
                getLanguageNameOrId(language, context),
                R.drawable.ic_language))
        }
        val sorted : List<LanguageSpinnerItem> = languageItems.sortedBy { it.languageTranslation }
        return ArrayList(sorted)
    }

    /**
     * Returns a list of languages available for testing.
     * New languages are adding here.
     */
    fun getList() : ArrayList<Language> {
        return arrayListOf(
            Language(EN),
            Language(FR),
            Language(DE),
            Language(IT),
            Language(KR),
            Language(RU),
            Language(ES),
            Language(BG),
            Language(UA)
            )
    }

    private fun getLanguageNameOrId(language : Language, context: Context) : String {
        return language.getName(context)
    }

    companion object {
        // language identifiers (ISO 639-1 code) in upper case.
        const val RU = "RU"
        const val EN = "EN"
        const val FR = "FR"
        const val DE = "DE"
        const val ES = "ES"
        const val IT = "IT"
        const val KR = "KR"
        const val UA = "UA"
        const val BG = "BG"
    }
}