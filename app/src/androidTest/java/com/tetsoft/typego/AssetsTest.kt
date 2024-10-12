package com.tetsoft.typego

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.domain.LanguageList
import org.junit.Test

class AssetsTest {

    private val context : Context = ApplicationProvider.getApplicationContext()

    @Test
    fun assets_allAvailableLanguagesForAllDictionaryTypes_assertAllExist() {
        for (language in LanguageList().getPlayableLanguages()) {
            DictionaryType.values().forEach { dictionaryType ->
                context.assets.open("words/${dictionaryType.name.lowercase()}/${language.identifier}.txt")
            }
        }
    }
}