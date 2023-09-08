package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.*

import org.junit.Test

class DifferentLanguagesRequirementTest {

    @Test
    fun isReached_3differentLanguages_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime("UA"))
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("IT"))
        assertTrue(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_2differentLanguages_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("PL"))
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequiredAmount_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("UA"))
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_diverseList_equals4() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime("UA"))
        list.add(MockGameOnTime("EN"))
        list.add(MockGameOnTime("IT"))
        list.add(MockGameOnTime("ES"))
        assertEquals(GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list), 4)
    }

    @Test
    fun getCurrentProgress_emptyList_equals0() {
        val list = ClassicGameModesHistoryList()
        assertEquals(GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list), 0)
    }

    @Test
    fun getCurrentProgress_listSizeLessThanRequiredAmount_equals2() {
        val list = ClassicGameModesHistoryList()
        list.add(MockGameOnTime("UA"))
        list.add(MockGameOnTime("EN"))
        assertEquals(GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list), 2)
    }

    private class MockGameOnTime(language: String) :
        GameOnTime(0.0, 0, 0, 0, language, "", "", false, 0, 0, 0)
}