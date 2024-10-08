package com.tetsoft.typego.core.utils

import com.tetsoft.typego.core.domain.DictionaryType
import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryTypeConverterTest {

    @Test
    fun convertToString_dictionaryTypesProvided_equalsMatchingString() {
        assertEquals("basic", DictionaryTypeConverter().convertToString(DictionaryType.BASIC))
        assertEquals("enhanced", DictionaryTypeConverter().convertToString(DictionaryType.ENHANCED))
    }

    @Test
    fun convertToEnum_validStringProvided_equalsMatchingEnum() {
        assertEquals(DictionaryType.BASIC, DictionaryTypeConverter().convertToEnum("BASIC"))
        assertEquals(DictionaryType.ENHANCED, DictionaryTypeConverter().convertToEnum("enhanced"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun convertToEnum_invalidString_illegalArgumentExceptionThrown() {
        DictionaryTypeConverter().convertToEnum("")
    }
}