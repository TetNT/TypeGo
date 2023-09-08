package com.tetsoft.typego.data

import org.junit.Assert.*

import org.junit.Test

class AssetPathResolverDictionaryTest {

    @Test
    fun getPath_dictionaryBasicLanguageEn_correctPath() {
        val dictionary = DictionaryType.BASIC
        val language = "EN"
        val pathResolver = AssetPathResolver.Dictionary(dictionary, language).getPath()
        assertEquals(pathResolver, "words/basic/EN.txt")
    }

    @Test
    fun getPath_dictionaryEnhancedLanguageFr_correctPath() {
        val dictionary = DictionaryType.ENHANCED
        val language = "FR"
        val pathResolver = AssetPathResolver.Dictionary(dictionary, language).getPath()
        assertEquals(pathResolver, "words/enhanced/FR.txt")
    }

    @Test
    fun getPath_dictionaryBasicLanguageEmpty_correctPath() {
        val dictionary = DictionaryType.BASIC
        val language = ""
        assertThrows(IllegalArgumentException::class.java) {
            AssetPathResolver.Dictionary(dictionary, language).getPath()
        }
    }
}