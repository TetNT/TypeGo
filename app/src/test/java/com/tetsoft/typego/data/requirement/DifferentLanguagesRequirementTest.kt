package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.*

import org.junit.Test

class DifferentLanguagesRequirementTest {

    @Test
    fun isReached_3differentLanguages_assertTrue() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnTime("UA"))
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("IT"))
        assertTrue(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_2differentLanguages_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("PL"))
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun isReached_listSizeLessThanRequiredAmount_assertFalse() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("UA"))
        assertFalse(GameRequirement.DifferentLanguagesRequirement(3).isReached(list))
    }

    @Test
    fun getCurrentProgress_diverseList_equals4() {
        val list = ClassicGameModesHistoryList()
        list.add(FakeGameOnTime("UA"))
        list.add(FakeGameOnTime("EN"))
        list.add(FakeGameOnTime("IT"))
        list.add(FakeGameOnTime("ES"))
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
        list.add(FakeGameOnTime("UA"))
        list.add(FakeGameOnTime("EN"))
        assertEquals(GameRequirement.DifferentLanguagesRequirement(3).getCurrentProgress(list), 2)
    }

    private class FakeGameOnTime(language: String) :
        GameOnTime(0.0, 0, 0, 0, language, "", "", false, 0, 0, 0)
}