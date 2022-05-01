package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.utils.StringKeys

class AchievementsProgressStorage(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(ACHIEVEMENTS_PROGRESS_STORAGE, Context.MODE_PRIVATE)

    /**
     * @param key achievement identifier in String format
     * @param value achievement completion time in millis
     */
    fun store(key: String, value: Long) {
        with(sharedPreferences.edit()) {
            putLong(key, value)
            apply()
        }
    }

    fun store(achievementsProgressList: AchievementsProgressList) {
        with(sharedPreferences.edit()) {
            putInt(StringKeys.STORAGE_APP_VERSION, BuildConfig.VERSION_CODE)
            for (progress in achievementsProgressList) {
                putLong(progress.achievementId.toString(), progress.completionDateTimeLong)
            }
            apply()
        }
    }

    fun getCompletionDateTimeLong(key: String): Long {
        return sharedPreferences.getLong(key, 0L)
    }

    fun achievementCompleted(key: String): Boolean {
        return getCompletionDateTimeLong(key) != 0L
    }

    private fun getVersion() : Int {
        return sharedPreferences.getInt(StringKeys.STORAGE_APP_VERSION, 0)
    }

    fun isUpToDate() : Boolean {
        return getVersion() == BuildConfig.VERSION_CODE
    }

    fun isEmpty() : Boolean {
        return getVersion() == 0
    }

    companion object {
        private const val ACHIEVEMENTS_PROGRESS_STORAGE = "achievements_progress_storage"
    }
}