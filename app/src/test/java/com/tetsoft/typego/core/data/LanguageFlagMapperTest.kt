package com.tetsoft.typego.core.data

import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.LanguageList
import org.junit.Assert.*

import org.junit.Test

class LanguageFlagMapperTest {

    @Test
    fun get_allPlayableLanguages_assertFlagsExist() {
        LanguageList().getPlayableLanguages().forEach { language ->
            val flagId = LanguageFlagMapper().get(language)
            if (R.drawable.ic_language == flagId) throw AssertionError("Missing ${language.identifier} flag.")
        }
    }

    @Test
    fun get_englishLanguage_assertEqualsEnglishFlagIcon() {
        val language = Language(Language.EN)
        val flagId = LanguageFlagMapper().get(language)
        assertEquals(R.drawable.ic_flag_english, flagId)
    }

    @Test
    fun get_unknownLanguage_assertEqualsDefaultFlagIcon() {
        val language = Language("UNKNOWN")
        val flagId = LanguageFlagMapper().get(language)
        assertEquals(R.drawable.ic_language, flagId)
    }
}