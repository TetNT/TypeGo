package com.tetsoft.typego.data.game

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.textsource.TextSource

abstract class GameSettings(
    val suggestionsActivated: Boolean,
    val screenOrientation: ScreenOrientation,
    val ignoreCase: Boolean
) {

    abstract fun generateText(textSource: TextSource) : String

    abstract class TimeBased(
        val time: Int,
        suggestionsActivated: Boolean,
        screenOrientation: ScreenOrientation,
        ignoreCase: Boolean
    ) : GameSettings(suggestionsActivated, screenOrientation, ignoreCase)

    class ForRandomlyGeneratedWords(
        val languageCode: String,
        timeSelected: Int,
        val dictionaryType: DictionaryType,
        val seed: String,
        suggestionsActivated: Boolean,
        screenOrientation: ScreenOrientation,
        ignoreCase: Boolean
    ) : TimeBased(timeSelected, suggestionsActivated, screenOrientation, ignoreCase) {
        override fun generateText(textSource: TextSource): String {
            return textSource.getString()
        }
    }

    class ForUserText(
        val userText: String,
        timeSelected: Int,
        suggestionsActivated: Boolean,
        screenOrientation: ScreenOrientation,
        ignoreCase: Boolean
    ) : TimeBased(timeSelected, suggestionsActivated, screenOrientation, ignoreCase) {
        override fun generateText(textSource: TextSource): String {
            return textSource.getString()
        }
    }
}