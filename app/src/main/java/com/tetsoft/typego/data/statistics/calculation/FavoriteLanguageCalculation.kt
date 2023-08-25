package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameHistoryDataSelector
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.language.Language

class FavoriteLanguageCalculation(
    private val resultsList: ClassicGameModesHistoryList,
    private val languages: List<Language>
) : StatisticsCalculation {

    override fun provide(): Language {
        var mostFrequentLanguage = Language("")
        var mostFrequency = 0
        for (language in languages) {
            val langFrequency: Int =
                ClassicGameHistoryDataSelector(resultsList).getResultsByLanguage(language.identifier).size
            if (langFrequency > mostFrequency) {
                mostFrequency = langFrequency
                mostFrequentLanguage = language
            }
        }
        return mostFrequentLanguage
    }
}