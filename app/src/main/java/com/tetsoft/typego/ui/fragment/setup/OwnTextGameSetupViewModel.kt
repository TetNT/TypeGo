package com.tetsoft.typego.ui.fragment.setup

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.history.DataSelector
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnTextGameSetupViewModel @Inject constructor(private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage) :
    ViewModel() {
    private val lastResult get() = DataSelector.Standard(ownTextGameHistoryStorage.get()).getMostRecentResult()

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

    private companion object {
        const val DEFAULT_TIME_MODE = 60
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
        const val DEFAULT_SUGGESTIONS_ACTIVATED = true
    }
}