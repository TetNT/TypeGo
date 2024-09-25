package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.data.RandomWordsLanguageCodeMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DifferentLanguagesRequirementTest {

    @Test
    fun isReached_3differentLanguages_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsLanguageCodeMock("UA"),
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("IT"),
            ), emptyList()
        )
        assertTrue(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_2differentLanguages_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("PL"),
            ), emptyList()
        )
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequiredAmount_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("UA")
            ), emptyList()
        )
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_diverseList_equals4() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsLanguageCodeMock("UA"),
                RandomWordsLanguageCodeMock("EN"),
                RandomWordsLanguageCodeMock("IT"),
                RandomWordsLanguageCodeMock("ES"),
            ), emptyList()
        )
        assertEquals(4, GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list))
    }

    @Test
    fun getCurrentProgress_emptyList_equals0() {
        val list = GameHistoryImpl(emptyList(), emptyList())
        assertEquals(0, GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list))
    }

    @Test
    fun getCurrentProgress_listSizeLessThanRequiredAmount_equals2() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                RandomWordsLanguageCodeMock("UA"),
                RandomWordsLanguageCodeMock("EN"),
            ), emptyList()
        )
        assertEquals(2, GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list))
    }
}