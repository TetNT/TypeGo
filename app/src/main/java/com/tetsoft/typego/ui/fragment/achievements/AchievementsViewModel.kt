package com.tetsoft.typego.ui.fragment.achievements

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage,
    private val gameOnNumberOfWordsHistoryStorage: GameOnNumberOfWordsHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage,
) : ViewModel() {

    fun getResults() : ClassicGameModesHistoryList {
        return ClassicGameModesHistoryList(gameOnTimeHistoryStorage, gameOnNumberOfWordsHistoryStorage)
    }

    fun getAchievementsProgressList() : AchievementsProgressList {
        return achievementsProgressStorage.getAll()
    }

    fun getAchievementsList() : List<Achievement> {
        return AchievementsList.get()
    }
}