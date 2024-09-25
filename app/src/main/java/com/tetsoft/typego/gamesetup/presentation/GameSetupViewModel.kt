package com.tetsoft.typego.gamesetup.presentation

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.core.domain.GameSettings


class GameSetupViewModel : ViewModel() {

    private var randomWordsGameSettings : GameSettings.ForRandomlyGeneratedWords? = null
    fun getRandomWordsGameSettings() = randomWordsGameSettings!!

    fun setRandomWordsGameSettings(gameSettings: GameSettings.ForRandomlyGeneratedWords) {
        this.randomWordsGameSettings = gameSettings

    }

    private var ownTextGameSettings : GameSettings.ForUserText? = null
    fun getOwnTextGameSettings() = ownTextGameSettings!!

    fun setOwnTextGameSettings(ownTextGameSettings: GameSettings.ForUserText) {
        this.ownTextGameSettings = ownTextGameSettings
    }

    fun settingsAreValid(gameSettings: GameSettings) : Boolean {
        var valid = false
        when(gameSettings) {
            is GameSettings.ForRandomlyGeneratedWords -> {
                valid = true
            }
            is GameSettings.ForUserText -> {
                valid = userTextIsValid(gameSettings.userText)
            }
        }
        return valid
    }

    fun userTextIsValid(text: String) : Boolean {
        if (text.isBlank()) return false
        val words = text.split(' ')
        return words.size >= OWN_TEXT_MINIMUM_WORDS && !words.any { it.isBlank() }
    }

    fun getMinimumWordsConstraint() : Int = OWN_TEXT_MINIMUM_WORDS

    private companion object {
        const val OWN_TEXT_MINIMUM_WORDS = 20
    }
}