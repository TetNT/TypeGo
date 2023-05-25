package com.tetsoft.typego.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.OldResultsToNewMigration
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameResultListStorage: GameResultListStorage,
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    // TODO: Delete it
    fun removeVersioning() {
        achievementsProgressStorage.removeVersion()
    }

    fun migrateFromOldResultsToNew() {
        if (gameResults.isNotEmpty() && gameOnTimeHistoryStorage.get().isEmpty()) {
            gameOnTimeHistoryStorage.store(OldResultsToNewMigration(gameResults).getNewResultsList())
        }
    }

    private val gameResults get() = gameResultListStorage.get()

    fun userHasPreviousGames() = gameResults.isNotEmpty()

    fun getPreviousGameSettings() = gameResults[gameResults.size - 1].gameMode

    fun getLastUsedLanguageOrDefault(): Language {
        val lastResult = gameResults.getLastResultByGameType(PrebuiltTextGameMode::class.java)
        if (lastResult.gameMode is GameMode.Empty) return DEFAULT_LANGUAGE
        if (lastResult.gameMode is GameOnTime) {
            return (lastResult.gameMode).getLanguage()
        }
        return DEFAULT_LANGUAGE
    }

    companion object {
        val DEFAULT_LANGUAGE = Language(LanguageList.EN)
    }
}