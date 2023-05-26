package com.tetsoft.typego.data.history

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.*

import org.junit.Test

class GameOnTimeHistoryFilterTest {

    @Test
    fun byLanguage_filterEnglish_assertEquals3() {
        val list = GameOnTimeHistoryList(listOf(
            GameOnTimeMock("EN"),
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR"),
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR")
        ))
        val filtered = GameOnTimeHistoryFilter(list).byLanguage(Language("EN")).get()
        assertEquals(filtered.size, 3)
    }

    @Test
    fun byLanguage_filterItemNotInList_assertEquals0() {
        val list = GameOnTimeHistoryList(listOf(
            GameOnTimeMock("EN"),
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR"),
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR")
        ))
        val filtered = GameOnTimeHistoryFilter(list).byLanguage(Language("UA")).get()
        assertEquals(filtered.size, 0)
    }

    @Test
    fun byLanguage_emptyList_assertEquals0() {
        val list = GameOnTimeHistoryList()
        val filtered = GameOnTimeHistoryFilter(list).byLanguage(Language("EN")).get()
        assertEquals(filtered.size, 0)
    }

    @Test
    fun inDescendingOrder_populatedList_assertOrderedProperly() {
        val list = GameOnTimeHistoryList(listOf(
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR"),
            GameOnTimeMock("ES")
        ))
        val descending = GameOnTimeHistoryFilter(list).inDescendingOrder().get()
        assertEquals(descending[0].getLanguageCode(), "ES")
        assertEquals(descending[1].getLanguageCode(), "FR")
        assertEquals(descending[2].getLanguageCode(), "EN")
    }

    @Test
    fun get() {
        val list = GameOnTimeHistoryList(listOf(
            GameOnTimeMock("EN"),
            GameOnTimeMock("FR"),
            GameOnTimeMock("ES")
        ))
        val filter = GameOnTimeHistoryFilter(list).get()
        assertTrue(list == filter)
    }

    private class GameOnTimeMock(language: String) : GameOnTime(0.0, 0, 0, 0, language, "", "", false, 0, 0, 0L)
}