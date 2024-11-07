package com.tetsoft.typego.gamesetup.presentation

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.domain.TimeMode
import com.tetsoft.typego.gamesetup.data.LoopedListIterator
import com.tetsoft.typego.history.data.DataSelectorImpl
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnTextGameSetupViewModel @Inject constructor(private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage) :
    ViewModel() {
    private val lastResult get() = DataSelectorImpl(ownTextGameHistoryStorage.get()).getMostRecentResult()

    private val textIterator = LoopedListIterator<String>()

    fun getLastUsedUserText() : String {
        return lastResult?.getText() ?: ""
    }

    fun getLastUsedTimeModeOrDefault() : TimeMode {
        return TimeMode(lastResult?.getChosenTimeInSeconds() ?: DEFAULT_TIME_MODE)
    }

    fun getLastUsedOrientationOrDefault() : ScreenOrientation {
        return lastResult?.getScreenOrientation() ?: DEFAULT_SCREEN_ORIENTATION
    }

    fun areSuggestionsUsedInLastResultOrDefault() : Boolean {
        return lastResult?.areSuggestionsActivated() ?: DEFAULT_SUGGESTIONS_ACTIVATED
    }

    fun sanitizeUserTextInput(originalInput: String) : String {
        return originalInput.trim { it <= ' ' }
    }

    fun initSampleTextIterator(sampleTextList: List<String>) {
        textIterator.init(sampleTextList)
    }

    fun getNextSampleText() : String {
        return textIterator.nextElement()
    }

    private companion object {
        const val DEFAULT_TIME_MODE = 60
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
        const val DEFAULT_SUGGESTIONS_ACTIVATED = true
    }
}