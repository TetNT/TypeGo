package com.tetsoft.typego.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.OldResultsToNewMigration
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameResultListStorage: GameResultListStorage,
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage
) : ViewModel() {

    fun migrateFromOldResultsToNew() {
        val gameResults = gameResultListStorage.get()
        if (gameResults.isNotEmpty() && gameOnTimeHistoryStorage.get().isEmpty()) {
            gameOnTimeHistoryStorage.store(OldResultsToNewMigration(gameResults).getNewResultsList())
        }
    }

    fun userHasPreviousGames() = gameOnTimeHistoryStorage.get().isNotEmpty()

    fun getMostRecentGameSettings() = GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getMostRecentResult()

    fun getLastUsedLanguageOrDefault(): Language {
        val lastResult = GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getMostRecentResult()
        if (lastResult is com.tetsoft.typego.game.GameOnTime.Empty) return DEFAULT_LANGUAGE
        return Language(lastResult.getLanguageCode())
    }

    companion object {
        val DEFAULT_LANGUAGE = Language(LanguageList.EN)
    }
}