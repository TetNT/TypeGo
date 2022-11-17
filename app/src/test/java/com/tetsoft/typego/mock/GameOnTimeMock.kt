package com.tetsoft.typego.mock

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnTime

class GameOnTimeMock {
    fun getEnglishBasicPortraitWithSuggestions(timeInSeconds: Int): GameOnTime {
        return GameOnTime(
            Language("EN"),
            TimeMode(timeInSeconds),
            DictionaryType.BASIC,
            true,
            ScreenOrientation.PORTRAIT
        )
    }

    fun getBasicPortraitWithSuggestions(language: Language, timeInSeconds: Int): GameOnTime {
        return GameOnTime(
            language,
            TimeMode(timeInSeconds),
            DictionaryType.BASIC,
            true,
            ScreenOrientation.PORTRAIT
        )
    }
}