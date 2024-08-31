package com.tetsoft.typego.ui.fragment.setup

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.history.DataSelector
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomWordsGameSetupViewModel @Inject constructor(private val randomWordsHistoryStorage: RandomWordsHistoryStorage) : ViewModel() {

    private val lastResult get() = DataSelector.Standard(randomWordsHistoryStorage.get()).getMostRecentResult()

    fun getLastUsedLanguageOrDefault() : Language {
        return Language(lastResult?.getLanguageCode() ?: DEFAULT_LANGUAGE)
    }

    fun getLastUsedTimeModeOrDefault() : TimeMode {
        return TimeMode(lastResult?.getTimeSpent() ?: DEFAULT_TIME_MODE)
    }

    fun getLastUsedDictionaryTypeOrDefault() : DictionaryType {
        return lastResult?.getDictionaryType() ?: DEFAULT_DICTIONARY
    }

    fun getLastUsedOrientationOrDefault() : ScreenOrientation {
        return lastResult?.getScreenOrientation() ?: DEFAULT_SCREEN_ORIENTATION
    }

    fun areSuggestionsUsedInLastResultOrDefault() : Boolean {
        return lastResult?.areSuggestionsActivated() ?: DEFAULT_SUGGESTIONS_ACTIVATED
    }

    fun getLastUsedSeed(): String {
        return lastResult?.getSeed() ?: ""
    }

    private companion object {
        const val DEFAULT_LANGUAGE = Language.EN
        const val DEFAULT_TIME_MODE = 60
        val DEFAULT_DICTIONARY = DictionaryType.BASIC
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
        const val DEFAULT_SUGGESTIONS_ACTIVATED = true
    }
}