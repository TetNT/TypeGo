package com.tetsoft.typego.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.KeyNotesStateStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import com.tetsoft.typego.data.keynote.KeyNotesList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage,
    private val keyNotesList: KeyNotesList,
    private val keyNotesStateStorage: KeyNotesStateStorage
) : ViewModel() {

    private val keyNotes get() = keyNotesStateStorage

    fun userHasPreviousGames() = gameOnTimeHistoryStorage.get().isNotEmpty()

    fun getMostRecentGameSettings() = GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getMostRecentResult()

    fun hasUncheckedNotes() : Boolean {
        for (note in keyNotesList.get()) {
            if (!keyNotes.getBoolean(note.identifier)) {
                return true
            }
        }
        return false
    }

    fun getLastUsedLanguageOrDefault(): Language {
        val lastResult = GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getMostRecentResult()
        if (lastResult is GameOnTime.Empty) return DEFAULT_LANGUAGE
        return Language(lastResult.getLanguageCode())
    }

    companion object {
        val DEFAULT_LANGUAGE = Language(Language.EN)
    }
}