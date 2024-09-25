package com.tetsoft.typego.history.data

import com.tetsoft.typego.RandomWordsLanguageCodeMock
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.history.domain.GameOnTimeHistoryList
import org.junit.Assert.*

import org.junit.Test

class RandomWordsHistoryFilterTest {

    @Test
    fun byLanguage_filterEnglish_assertEquals3() {
        val list = GameOnTimeHistoryList(listOf(
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR"),
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR")
        ))
        val filtered = RandomWordsHistoryFilter(list).byLanguage(Language("EN")).getList()
        assertEquals(filtered.size, 3)
    }

    @Test
    fun byLanguage_filterItemNotInList_assertEquals0() {
        val list = GameOnTimeHistoryList(listOf(
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR"),
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR")
        ))
        val filtered = RandomWordsHistoryFilter(list).byLanguage(Language("UA")).getList()
        assertEquals(filtered.size, 0)
    }

    @Test
    fun byLanguage_emptyList_assertEquals0() {
        val list = GameOnTimeHistoryList()
        val filtered = RandomWordsHistoryFilter(list).byLanguage(Language("EN")).getList()
        assertEquals(filtered.size, 0)
    }

    @Test
    fun inDescendingOrder_populatedList_assertOrderedProperly() {
        val list = GameOnTimeHistoryList(listOf(
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR"),
            RandomWordsLanguageCodeMock("ES")
        ))
        val descending = RandomWordsHistoryFilter(list).inDescendingOrder().getList()
        assertEquals(descending[0].getLanguageCode(), "ES")
        assertEquals(descending[1].getLanguageCode(), "FR")
        assertEquals(descending[2].getLanguageCode(), "EN")
    }

    @Test
    fun get() {
        val list = GameOnTimeHistoryList(listOf(
            RandomWordsLanguageCodeMock("EN"),
            RandomWordsLanguageCodeMock("FR"),
            RandomWordsLanguageCodeMock("ES")
        ))
        val filter = RandomWordsHistoryFilter(list).getList()
        assertTrue(list == filter)
    }
}