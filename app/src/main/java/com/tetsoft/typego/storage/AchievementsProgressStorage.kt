package com.tetsoft.typego.storage

import android.content.Context
import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList

class AchievementsProgressStorage(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(ACHIEVEMENTS_PROGRESS_STORAGE, Context.MODE_PRIVATE)

    /**
     * @param key achievement identifier in the String format (not index!)
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
            for (progress in achievementsProgressList) {
                putLong(progress.achievementId.toString(), progress.completionDateTimeLong)
            }
            apply()
        }
    }

    // TODO: To be deleted in the next version (1.5.x or 1.6.0)
    fun removeVersion() {
        with(sharedPreferences.edit()) {
            remove("stored_on_app_version")
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