package com.tetsoft.typego.ui.fragment.achievements

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.data.history.GameHistory
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage,
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistory.Standard(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

    fun getHistory() : GameHistory {
        return gameHistory
    }

    fun getAchievementsProgressList() : AchievementsProgressList {
        return achievementsProgressStorage.getAll()
    }

    fun getAchievementsList() : List<Achievement> {
        return AchievementsList.get()
    }
}