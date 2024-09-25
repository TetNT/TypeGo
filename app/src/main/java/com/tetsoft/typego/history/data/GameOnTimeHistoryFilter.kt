package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.history.domain.GameHistoryList
import com.tetsoft.typego.history.domain.GameOnTimeHistoryList
import java.util.*
// TODO: Rename and use it once the GameHistoryFragment refactoring is completed
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