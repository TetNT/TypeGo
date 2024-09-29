package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.history.domain.GameHistoryList
import com.tetsoft.typego.history.domain.RandomWordsHistoryList
import java.util.*
// TODO: Rename and use it once the GameHistoryFragment refactoring is completed
class RandomWordsHistoryFilter(historyList: GameHistoryList<RandomWords>) {
    private val filteredList : GameHistoryList<RandomWords> = historyList

    fun byLanguage(language: Language): RandomWordsHistoryFilter {
        if (language.identifier == Language.ALL) return RandomWordsHistoryFilter(filteredList)
        val selectedResults = RandomWordsHistoryList()
        for (historyElement in filteredList) {
            val rowLanguageId = historyElement.getLanguageCode()
            if (rowLanguageId.equals(
                    language.identifier,
                    ignoreCase = true
                )
            ) selectedResults.add(historyElement)
        }
        return RandomWordsHistoryFilter(selectedResults)
    }

    fun inDescendingOrder(): RandomWordsHistoryFilter {
        val copy = filteredList.clone() as RandomWordsHistoryList
        Collections.reverse(copy)
        return RandomWordsHistoryFilter(copy)
    }

    fun getList() : GameHistoryList<RandomWords> {
        return filteredList
    }
}