package com.tetsoft.typego.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameResultListStorage: GameResultListStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    fun removeVersioning() {
        achievementsProgressStorage.removeVersion()
    }

    private val gameResults get() = gameResultListStorage.get()

    private val lastResult: GameResult =
        gameResults.getLastResultByGameType(PrebuiltTextGameMode::class.java)

    fun userHasPreviousGames() = gameResults.isNotEmpty()

    fun getPreviousGameSettings() = gameResults[gameResults.size - 1].gameMode

    fun getLastUsedLanguageOrDefault(): Language {
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