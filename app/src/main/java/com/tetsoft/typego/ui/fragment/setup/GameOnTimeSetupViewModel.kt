package com.tetsoft.typego.ui.fragment.setup

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.storage.GameResultListStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameOnTimeSetupViewModel @Inject constructor(private val gameResultListStorage: GameResultListStorage) :
    ViewModel() {

    private val gameResults get() = gameResultListStorage.get()

    private val lastResult get() = gameResults.getLastResultByGameType(GameOnTime::class.java)

    fun getLastUsedLanguageOrDefault() : Language {
        if (lastResult.gameMode is GameMode.Empty)
            return DEFAULT_LANGUAGE
        if (lastResult.gameMode is GameOnTime) {
            return (lastResult.gameMode as GameOnTime).getLanguage()
        }
        return DEFAULT_LANGUAGE
    }

    fun getLastUsedTimeModeOrDefault() : TimeMode {
        if (lastResult.gameMode is GameOnTime) {
            return (lastResult.gameMode as GameOnTime).timeMode
        }
        return DEFAULT_TIME_MODE
    }

    fun getLastUsedDictionaryTypeOrDefault() : DictionaryType {
        if (lastResult.gameMode is GameOnTime) {
            return (lastResult.gameMode as GameOnTime).dictionaryType
        }
        return DEFAULT_DICTIONARY
    }

    fun getLastUsedOrientationOrDefault() : ScreenOrientation {
        return (lastResult.gameMode).screenOrientation
    }

    fun areSuggestionsUsedInLastResultOrDefault() : Boolean {
        return lastResult.gameMode.suggestionsActivated
    }

    companion object {
        val DEFAULT_LANGUAGE = Language(LanguageList.EN)
        val DEFAULT_TIME_MODE = TimeMode(60)
        val DEFAULT_DICTIONARY = DictionaryType.BASIC
    }
}