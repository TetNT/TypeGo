package com.tetsoft.typego.ui.fragment.achievements

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.achievement.deprecated.AchievementsList
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val results: ClassicGameModesHistoryList,
    private val achievementsProgressStorage: AchievementsProgressStorage,
    private val achievementsList: AchievementsList
) : ViewModel() {

    fun getResults() : ClassicGameModesHistoryList {
        return results
    }

    fun getAchievementsProgressList() : AchievementsProgressList {
        return achievementsProgressStorage.getAll()
    }

    fun getAchievementsList() : AchievementsList {
        return achievementsList
    }
}