package com.tetsoft.typego.ui.fragment.account

import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.utils.AnimationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AccountViewModel @Inject constructor(private val gameResultListStorage: GameResultListStorage) :
    ViewModel() {

    private val resultList get() = gameResultListStorage.get()

    private val mutableSelectedLanguage = MutableLiveData<Language>()

    val selectedLanguage : LiveData<Language> = mutableSelectedLanguage

    fun selectLanguage(language: Language) {
        mutableSelectedLanguage.postValue(language)
    }

    fun getResults(language: Language, inDescendingOrder: Boolean): GameResultList {
        val byLang = resultList.getResultsByLanguage(language)
        return if (inDescendingOrder) {
            byLang.inDescendingOrder
        } else byLang
    }

    fun historyCanBeShown(gameResultList: List<GameResult>) = gameResultList.isNotEmpty()

    fun averageWpmCanBeShown(gameResultList: List<GameResult>): Boolean = gameResultList.size >= 5

    fun getAverageWpm(gameResultList: List<GameResult>): Int {
        var wpmSum = 0.0
        for (res in gameResultList)
            wpmSum += res.wpm
        if (wpmSum == 0.0 || gameResultList.isEmpty()) return 0
        return (wpmSum / gameResultList.size).roundToInt()
    }

    fun getGameHistoryEnteringAnimation() : AnimationSet {
        val animationManager = AnimationManager()
        val slideIn: Animation = animationManager.getSlideInAnimation(0f, 50f, 500)
        val fadeIn: Animation = animationManager.getFadeInAnimation(500)
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(slideIn)
        animationSet.addAnimation(fadeIn)
        return animationSet
    }

    fun getBestResultWpm(gameResultList: GameResultList): Int {
        return gameResultList.bestResultWpm
    }
}