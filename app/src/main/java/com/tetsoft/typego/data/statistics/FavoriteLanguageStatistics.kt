package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.statistics.calculation.FavoriteLanguageCalculation

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