package com.tetsoft.typego.data.history

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.game.RandomWords
import java.util.*

class GameOnTimeHistoryFilter(historyList: GameHistoryList<RandomWords>) {
    private val filteredList : GameHistoryList<RandomWords> = historyList

    fun byLanguage(language: Language): GameOnTimeHistoryFilter {
        if (language.identifier == Language.ALL) return GameOnTimeHistoryFilter(filteredList)
        val selectedResults = GameOnTimeHistoryList()
        for (historyElement in filteredList) {
            val rowLanguageId = historyElement.getLanguageCode()
            if (rowLanguageId.equals(
                    language.identifier,
                    ignoreCase = true
                )
            ) selectedResults.add(historyElement)
        }
        return GameOnTimeHistoryFilter(selectedResults)
    }

    fun inDescendingOrder(): GameOnTimeHistoryFilter {
        val copy = filteredList.clone() as GameOnTimeHistoryList
        Collections.reverse(copy)
        return GameOnTimeHistoryFilter(copy)
    }

    fun getList() : GameHistoryList<RandomWords> {
        return filteredList
    }
}