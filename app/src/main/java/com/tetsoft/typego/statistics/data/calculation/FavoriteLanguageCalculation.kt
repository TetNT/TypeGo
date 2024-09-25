package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.statistics.domain.StatisticsCalculation

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