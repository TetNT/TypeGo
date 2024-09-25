package com.tetsoft.typego.core.domain

import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.game.domain.TextSource

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
    ) : GameSettings(suggestionsActivated, screenOrientation, ignoreCase) {
        class Empty : TimeBased(0, false, ScreenOrientation.PORTRAIT, true) {
            override fun generateText(textSource: TextSource): String {
                return ""
            }
        }
    }

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