package com.tetsoft.typego.data.history

import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.data.OwnTextMock
import com.tetsoft.typego.data.RandomWordsMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class GameHistoryStandardTest {

    @Test
    fun getAllResults_randomWords3ResultsOwnText2Results_sizeEquals5() {
        val randomWordsList = listOf<RandomWords>(
            RandomWordsMock(),
            RandomWordsMock(),
            RandomWordsMock()
        )
        val ownTextList = listOf<OwnText>(
            OwnTextMock(),
            OwnTextMock(),
        )
        val gameHistory = GameHistoryImpl(randomWordsList, ownTextList)
        assertEquals(5, gameHistory.getAllResults().size)
    }

    @Test
    fun getAllResults_randomWordsEmptyResultsOwnText2Results_sizeEquals2() {
        val randomWordsList = emptyList<RandomWords>()
        val ownTextList = listOf<OwnText>(
            OwnTextMock(),
            OwnTextMock(),
        )
        val gameHistory = GameHistoryImpl(randomWordsList, ownTextList)
        assertEquals(2, gameHistory.getAllResults().size)
    }

    @Test
    fun getAllResults_randomWords3ResultsOwnTextEmptyResults_sizeEquals3() {
        val randomWordsList = listOf<RandomWords>(
            RandomWordsMock(),
            RandomWordsMock(),
            RandomWordsMock()
        )
        val ownTextList = emptyList<OwnText>()
        val gameHistory = GameHistoryImpl(randomWordsList, ownTextList)
        assertEquals(3, gameHistory.getAllResults().size)
    }

    @Test
    fun getResultsWithLanguage_randomWords3ResultsOwnText2Results_sizeEquals3() {
        val randomWordsList = listOf<RandomWords>(
            RandomWordsMock(),
            RandomWordsMock(),
            RandomWordsMock()
        )
        val ownTextList = listOf<OwnText>(
            OwnTextMock(),
            OwnTextMock(),
        )
        val gameHistory = GameHistoryImpl(randomWordsList, ownTextList)
        assertEquals(3, gameHistory.getResultsWithLanguage().size)
    }

    @Test
    fun getResultsWithWordsInformation_randomWords3ResultsOwnText2Results_sizeEquals5() {
        val randomWordsList = listOf<RandomWords>(
            RandomWordsMock(),
            RandomWordsMock(),
            RandomWordsMock()
        )
        val ownTextList = listOf<OwnText>(
            OwnTextMock(),
            OwnTextMock(),
        )
        val gameHistory = GameHistoryImpl(randomWordsList, ownTextList)
        assertEquals(5, gameHistory.getResultsWithWordsInformation().size)
    }
}