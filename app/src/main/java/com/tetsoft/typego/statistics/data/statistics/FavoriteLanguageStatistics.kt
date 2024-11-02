package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.FavoriteLanguageCalculation

class FavoriteLanguageStatistics(favoriteLanguageCalculation: FavoriteLanguageCalculation) : Statistics {

    private val favoriteLanguage = favoriteLanguageCalculation.provide()

    override fun provide(): Language {
        return favoriteLanguage
    }

    override fun getVisibility(): VisibilityProvider {
        return if (favoriteLanguage.identifier.isEmpty())
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}