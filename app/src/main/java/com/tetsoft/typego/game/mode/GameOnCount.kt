package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.Language
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.LanguageSelectable

class GameOnCount(
    private val language: Language,
    //val wordsCount: WordsCount,
    val dictionaryType: DictionaryType,
    override val suggestionsActivated: Boolean,
    override val screenOrientation: ScreenOrientation
) : GameMode(suggestionsActivated, screenOrientation), LanguageSelectable {

    override fun getLanguage(): Language {
        return language
    }
}