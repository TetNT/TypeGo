package com.tetsoft.typego.ui.fragment.achievements

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val gameResultsDataSource: GameResultsDataSource,
    private val achievementsProgressStorage: AchievementsProgressStorage,
    private val achievementsList: AchievementsList
) : ViewModel() {

    fun getResultList() : GameResultList {
        return gameResultsDataSource.get()
    }

    fun getAchievementsProgress() : AchievementsProgressList {
        return achievementsProgressStorage.getAll()
    }

    fun getAchievementsList() : AchievementsList {
        return achievementsList.get()
    }
}