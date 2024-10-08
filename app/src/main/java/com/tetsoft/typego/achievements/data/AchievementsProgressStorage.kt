package com.tetsoft.typego.achievements.data

import android.content.Context
import com.tetsoft.typego.achievements.domain.AchievementsCompletionPair
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AchievementsProgressStorage {

    fun store(key: String, value: Long)

    fun store(achievementsProgressList: CompletedAchievementsList)

    fun getAll() : CompletedAchievementsList

    class SharedPreferences @Inject constructor(@ApplicationContext context: Context) : AchievementsProgressStorage {
        private val sharedPreferences =
            context.getSharedPreferences(ACHIEVEMENTS_PROGRESS_STORAGE, Context.MODE_PRIVATE)

        /**
         * @param key achievement identifier in the String format (not an index!)
         * @param value achievement completion time in millis
         */
        override fun store(key: String, value: Long) {
            with(sharedPreferences.edit()) {
                putLong(key, value)
                apply()
            }
        }

        override fun store(achievementsProgressList: CompletedAchievementsList) {
            with(sharedPreferences.edit()) {
                for (progress in achievementsProgressList) {
                    putLong(progress.achievementId.toString(), progress.completionDateTimeLong)
                }
                apply()
            }
        }

        override fun getAll() : CompletedAchievementsList {
            val list = CompletedAchievementsList()
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


    class Mock : AchievementsProgressStorage {

        private val achievementsProgressList = CompletedAchievementsList()

        override fun store(key: String, value: Long) {
            val foundElement = achievementsProgressList.find { it.achievementId.toString() == key }
            if (foundElement == null) {
                achievementsProgressList.add(AchievementsCompletionPair(key.toInt(), value))
            }
        }

        override fun store(achievementsProgressList: CompletedAchievementsList) {
            this.achievementsProgressList.clear()
            this.achievementsProgressList.addAll(achievementsProgressList)
        }

        override fun getAll(): CompletedAchievementsList {
            return achievementsProgressList
        }

    }

}



