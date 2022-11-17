package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
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
            //putInt(StringKeys.STORAGE_APP_VERSION, BuildConfig.VERSION_CODE)
            for (progress in achievementsProgressList) {
                putLong(progress.achievementId.toString(), progress.completionDateTimeLong)
            }
            apply()
        }
    }

    // Remove a redundant version record from the preferences file.
    fun removeVersion() {
        with(sharedPreferences.edit()) {
            remove(StringKeys.STORAGE_APP_VERSION)
            apply()
        }
    }

    fun getAll() : AchievementsProgressList {
        val list = AchievementsProgressList()
        val keys = sharedPreferences.all
        for (entry in keys.entries) {
            list.add(AchievementsCompletionPair(entry.key.toInt(), entry.value.toString().toLong()))
        }
        return list
    }

    companion object {
        private const val ACHIEVEMENTS_PROGRESS_STORAGE = "achievements_progress_storage"
    }
}