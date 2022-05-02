package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.TimeMode
import com.tetsoft.typego.data.language.LanguageSelectable
import java.io.Serializable

class GameOnTime(
    private val language: Language,
    val timeMode: TimeMode,
    val dictionaryType: DictionaryType,
    suggestionsActivated: Boolean,
    screenOrientation: ScreenOrientation
) : GameMode(
    suggestionsActivated,
    screenOrientation
), LanguageSelectable, Serializable {

    fun calculateCpm(score: Int): Int {
        return score
    }

    override fun getLanguage(): Language {
        return language
    }

}