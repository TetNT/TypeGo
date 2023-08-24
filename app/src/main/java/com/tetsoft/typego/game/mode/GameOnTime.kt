package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import java.io.Serializable

@Deprecated("Replaced with interface-based GameOnTime")
class GameOnTime(
    private val language: Language,
    val timeMode: TimeMode,
    val dictionaryType: DictionaryType,
    suggestionsActivated: Boolean,
    screenOrientation: ScreenOrientation
) : GameMode(
    suggestionsActivated,
    screenOrientation
), PrebuiltTextGameMode, Serializable {

    override fun getLanguage(): Language {
        return language
    }

    override fun getDictionary(): DictionaryType {
        return dictionaryType
    }

}