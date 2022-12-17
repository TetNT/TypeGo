package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.statistics.VisibilityProvider
import com.tetsoft.typego.game.result.GameResultList

class FavoriteLanguageCalculation(
    private val resultsList: GameResultList,
    private val languages: List<Language>
) : StatisticsCalculation {

    override fun provide(): Language {
        var mostFrequentLanguage = Language("")
        var mostFrequency = 0
        for (language in languages) {
            val langFrequency: Int = resultsList.getResultsByLanguage(language).size
            if (langFrequency > mostFrequency) {
                mostFrequency = langFrequency
                mostFrequentLanguage = language
            }
        }
        return mostFrequentLanguage
    }
}