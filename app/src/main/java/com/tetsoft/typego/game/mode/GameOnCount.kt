package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.PrebuiltTextGameMode

@Deprecated("Replaced with the new GameOnNumberOfWords")
class GameOnCount(
    private val language: Language,
    //val wordsCount: WordsCount,
    val dictionaryType: DictionaryType,
    override val suggestionsActivated: Boolean,
    override val screenOrientation: ScreenOrientation
) : GameMode(suggestionsActivated, screenOrientation), PrebuiltTextGameMode {

    override fun getLanguage(): Language {
        return language
    }

    override fun getDictionary(): DictionaryType {
        return dictionaryType
    }
}