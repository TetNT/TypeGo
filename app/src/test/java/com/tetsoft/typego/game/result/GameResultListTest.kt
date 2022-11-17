package com.tetsoft.typego.game.result

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.mock.GameResultGameOnTimeListMock
import org.junit.Test
import org.junit.Assert.*

class GameResultListTest {

    @Test
    fun getResultsByLanguage_singleLanguage_sizeEqualsToOriginal() {
        val results = GameResultGameOnTimeListMock().getSingleLanguageList("FR")
        val filtered = results.getResultsByLanguage(Language("FR"))
        assertEquals(results.size, filtered.size)
    }

    @Test
    fun getResultsByLanguage_diverseLanguagesFilteredByFr_sizeNotEqualsToOriginal() {
        val results = GameResultGameOnTimeListMock().getMultipleLanguagesList()
        val filtered = results.getResultsByLanguage(Language("FR"))
        assertNotEquals(results.size, filtered.size)
    }

    @Test
    fun getResultsByLanguage_diverseLanguagesFilteredByFr_allLanguagesEqualsFr() {
        val results = GameResultGameOnTimeListMock().getMultipleLanguagesList()
        val filtered = results.getResultsByLanguage(Language("FR"))
        filtered.forEach {
            assertEquals("FR", (it.gameMode as PrebuiltTextGameMode).getLanguage().identifier)
        }
    }
}