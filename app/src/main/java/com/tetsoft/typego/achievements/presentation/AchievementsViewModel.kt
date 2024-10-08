package com.tetsoft.typego.achievements.presentation

import androidx.lifecycle.ViewModel
import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.achievements.domain.AchievementsList
import com.tetsoft.typego.achievements.data.CompletedAchievementsList
import com.tetsoft.typego.history.domain.GameHistory
import com.tetsoft.typego.achievements.data.AchievementsProgressStorage
import com.tetsoft.typego.history.data.GameHistoryImpl
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage,
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistoryImpl(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

    fun getHistory() : GameHistory {
        return gameHistory
    }

    fun getAchievementsProgressList() : CompletedAchievementsList {
        return achievementsProgressStorage.getAll()
    }

    fun getAchievementsList() : List<Achievement> {
        return AchievementsList.get()
    }
}