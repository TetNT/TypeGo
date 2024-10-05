package com.tetsoft.typego.game.data

import com.tetsoft.typego.core.domain.DictionaryType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class DictionaryAssetPathResolverTest {

    @Test
    fun getPath_dictionaryBasicLanguageEn_correctPath() {
        val dictionary = DictionaryType.BASIC
        val language = "EN"
        val pathResolver = DictionaryAssetPathResolver(dictionary, language).getPath()
        assertEquals(pathResolver, "words/basic/EN.txt")
    }

    @Test
    fun getPath_dictionaryEnhancedLanguageFr_correctPath() {
        val dictionary = DictionaryType.ENHANCED
        val language = "FR"
        val pathResolver = DictionaryAssetPathResolver(dictionary, language).getPath()
        assertEquals(pathResolver, "words/enhanced/FR.txt")
    }

    @Test
    fun getPath_dictionaryBasicLanguageEmpty_correctPath() {
        val dictionary = DictionaryType.BASIC
        val language = ""
        assertThrows(IllegalArgumentException::class.java) {
            DictionaryAssetPathResolver(dictionary, language).getPath()
        }
    }
}