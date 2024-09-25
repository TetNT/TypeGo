package com.tetsoft.typego.main.presentation

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.history.data.GameHistoryImpl
import com.tetsoft.typego.keynotes.domain.KeyNotesStateStorage
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import com.tetsoft.typego.keynotes.domain.KeyNotesList
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val keyNotesList: KeyNotesList,
    private val keyNotesStateStorage: KeyNotesStateStorage
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistoryImpl(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

    private val keyNotes get() = keyNotesStateStorage

    fun userHasPreviousGames() = gameHistory.getAllResults().isNotEmpty()

    fun getMostRecentGameSettings() = gameHistory.getMostRecentResult().toSettings()

    fun hasUncheckedNotes() : Boolean {
        for (note in keyNotesList.get()) {
            if (!keyNotes.getBoolean(note.identifier)) {
                return true
            }
        }
        return false
    }

    fun getLastUsedLanguageOrDefault(): Language {
        val lastResult = gameHistory.getResultsWithLanguage().maxByOrNull { it.getCompletionDateTime() }
        return Language(lastResult?.getLanguageCode() ?: DEFAULT_LANGUAGE)
    }

    companion object {
        const val DEFAULT_LANGUAGE = Language.EN
    }
}