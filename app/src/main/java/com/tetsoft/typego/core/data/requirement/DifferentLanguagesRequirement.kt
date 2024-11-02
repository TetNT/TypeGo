package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.core.domain.LanguageList
import com.tetsoft.typego.history.domain.GameHistory

class DifferentLanguagesRequirement(private val uniqueLanguageEntries: Int) :
    GameRequirement.WithProgress(uniqueLanguageEntries) {

    override fun getCurrentProgress(history: GameHistory): Int {
        val resultsWithLanguage = history.getResultsWithLanguage()
        var entries = 0
        for (language in LanguageList().getPlayableLanguages()) {
            if (resultsWithLanguage.any { it.getLanguageCode() == language.identifier })
                entries = entries.inc()
        }
        return entries
    }

    override fun isReached(history: GameHistory): Boolean {
        val resultsWithLanguage = history.getResultsWithLanguage()
        var entries = 0
        if (history.getResultsWithLanguage().size < uniqueLanguageEntries)
            return false
        for (language in LanguageList().getPlayableLanguages()) {
            if (resultsWithLanguage.any { it.getLanguageCode() == language.identifier })
                entries = entries.inc()
            if (entries >= uniqueLanguageEntries)
                return true
        }
        return false
    }

}