package com.tetsoft.typego.ui.fragment.setup

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.storage.GameResultListStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameOnTimeSetupViewModel @Inject constructor(gameResultListStorage: GameResultListStorage) :
    ViewModel() {

    private val gameResults = gameResultListStorage.get()

    private var lastResult = gameResults.getLastResultByGameType(GameOnTime::class.java)

    fun updateLastResult() {
        lastResult = gameResults.getLastResultByGameType(GameOnTime::class.java)
    }

    fun getLastUsedLanguageOrDefault() : Language {
        if (lastResult != null) return (lastResult!!.gameMode as GameOnTime).getLanguage()
        return DEFAULT_LANGUAGE
    }

    fun getLastUsedTimeModeOrDefault() : TimeMode {
        if (lastResult != null) return (lastResult!!.gameMode as GameOnTime).timeMode
        return DEFAULT_TIME_MODE
    }

    fun getLastUsedDictionaryTypeOrDefault() : DictionaryType {
        if (lastResult != null) return (lastResult!!.gameMode as GameOnTime).dictionaryType
        return DEFAULT_DICTIONARY
    }

    fun getLastUsedOrientationOrDefault() : ScreenOrientation {
        if (lastResult != null) return (lastResult!!.gameMode as GameOnTime).screenOrientation
        return DEFAULT_SCREEN_ORIENTATION
    }

    fun areSuggestionsUsedInLastResultOrDefault() : Boolean {
        if (lastResult != null) return (lastResult!!.gameMode as GameOnTime).suggestionsActivated
        return DEFAULT_SUGGESTIONS_ACTIVATION
    }

    companion object {
        val DEFAULT_LANGUAGE = Language(LanguageList.EN)
        val DEFAULT_TIME_MODE = TimeMode(60)
        val DEFAULT_DICTIONARY = DictionaryType.BASIC
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
        const val DEFAULT_SUGGESTIONS_ACTIVATION = true
    }
}