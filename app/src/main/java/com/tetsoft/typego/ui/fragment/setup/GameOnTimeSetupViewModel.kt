package com.tetsoft.typego.ui.fragment.setup

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameOnTimeSetupViewModel @Inject constructor(private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage) : ViewModel() {

    private val lastResult get() = GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getMostRecentResult()

    fun getLastUsedLanguageOrDefault() : Language {
        if (lastResult is GameOnTime.Empty)
            return DEFAULT_LANGUAGE
        return Language(lastResult.getLanguageCode())
    }

    fun getLastUsedTimeModeOrDefault() : TimeMode {
        if (lastResult is GameOnTime.Empty) {
            return DEFAULT_TIME_MODE
        }
        return TimeMode(lastResult.getTimeSpent())
    }

    fun getLastUsedDictionaryTypeOrDefault() : DictionaryType {
        if (lastResult is GameOnTime.Empty) {
            return DEFAULT_DICTIONARY
        }
        return lastResult.getDictionaryType()
    }

    fun getLastUsedOrientationOrDefault() : ScreenOrientation {
        if (lastResult is GameOnTime.Empty) {
            return DEFAULT_SCREEN_ORIENTATION
        }
        return lastResult.getScreenOrientation()
    }

    fun areSuggestionsUsedInLastResultOrDefault() : Boolean {
        if (lastResult is GameOnTime.Empty) {
            return DEFAULT_SUGGESTIONS_ACTIVATED
        }
        return lastResult.areSuggestionsActivated()
    }

    private companion object {
        val DEFAULT_LANGUAGE = Language(LanguageList.EN)
        val DEFAULT_TIME_MODE = TimeMode(60)
        val DEFAULT_DICTIONARY = DictionaryType.BASIC
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
        const val DEFAULT_SUGGESTIONS_ACTIVATED = true
    }
}