package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.language.Language

class FavoriteLanguageCalculation(
    private val resultsList: List<GameResult.WithLanguage>,
    private val languages: List<Language>
) : StatisticsCalculation {

    override fun provide(): Language {
        var mostFrequentLanguage = Language("")
        var mostFrequency = 0
        for (language in languages) {
            val langFrequency: Int =
                resultsList.filter { it.getLanguageCode() == language.identifier }.size
            if (langFrequency > mostFrequency) {
                mostFrequency = langFrequency
                mostFrequentLanguage = language
            }
        }
        return mostFrequentLanguage
    }
}