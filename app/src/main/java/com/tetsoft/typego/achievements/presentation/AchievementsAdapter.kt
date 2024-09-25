package com.tetsoft.typego.achievements.presentation

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.achievements.data.CompletedAchievementsList
import com.tetsoft.typego.achievements.domain.AchievementProgressCache
import com.tetsoft.typego.history.domain.GameHistory
import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.core.ui.VisibilityMapper
import com.tetsoft.typego.core.utils.DateTimeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AchievementsAdapter(
    private val context: Context,
    private val achievements: List<Achievement>,
    private val gameHistory: GameHistory,
    private val achievementsProgressList: CompletedAchievementsList
) : RecyclerView.Adapter<AchievementsAdapter.AchievementsViewHolder>() {

    private val wpmClusterCache = AchievementProgressCache.Standard()
    private val languageClusterCache = AchievementProgressCache.Standard()

    inner class AchievementsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvAchievementName: TextView = itemView.findViewById(R.id.tvAchievementName)
        private val tvAchievementDescription: TextView =
            itemView.findViewById(R.id.tvAchievementDescription)
        private val tvCompletionDate: TextView = itemView.findViewById(R.id.tvCompletionTime)
        private val tvProgressDescription: TextView =
            itemView.findViewById(R.id.tvProgressDescription)
        private val imgAchievement: ImageView = itemView.findViewById(R.id.imgAchievement)
        private val progressBarAchievement: ProgressBar =
            itemView.findViewById(R.id.progressbarAchievement)

        fun bind(
            dataItem: AchievementDataItem
        ) {
            tvCompletionDate.visibility =
                VisibilityMapper.VisibleInvisible(dataItem.completionTime != 0L).get()
            tvProgressDescription.visibility =
                VisibilityMapper.FromBoolean(dataItem.achievement.withProgressBar()).get()
            progressBarAchievement.visibility =
                VisibilityMapper.FromBoolean(dataItem.achievement.withProgressBar()).get()
            tvAchievementName.text = dataItem.achievement.getName(context)
            tvAchievementDescription.text = dataItem.achievement.getDescription(context)
            tvCompletionDate.text = DateTimeFormatter.Standard().format(dataItem.completionTime)
            imgAchievement.setImageResource(dataItem.achievement.getAchievementImageId())
            progressBarAchievement.max = dataItem.requiredAmount
            progressBarAchievement.progress = dataItem.calculatedProgress
            tvProgressDescription.text =
                context.getString(
                    R.string.achievement_progress,
                    dataItem.calculatedProgress,
                    dataItem.requiredAmount
                )
            val colorMatrix = ColorMatrix()
            if (dataItem.completionTime == 0L) {
                colorMatrix.setSaturation(0f)
            } else
                colorMatrix.setSaturation(1f)
            imgAchievement.colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.achievements_item, parent, false)
        return AchievementsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementsViewHolder, position: Int) {
        val currAchievement = achievements[position]
        CoroutineScope(Dispatchers.IO).launch {
            val achievementDataItem = getAchievementDataItem(currAchievement)
            withContext(Dispatchers.Main) {
                holder.bind(achievementDataItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    private fun getAchievementDataItem(currAchievement: Achievement): AchievementDataItem {
        val completionTime =
            achievementsProgressList[currAchievement.getId()].completionDateTimeLong
        var calculatedProgress = 0
        var requiredAmount = 0
        val requirement = currAchievement.getRequirement()
        if (requirement is GameRequirement.WithProgress) {

            calculatedProgress = when (currAchievement) {
                is Achievement.Wpm -> {
                    applyCache(wpmClusterCache, requirement)
                }

                is Achievement.DifferentLanguages -> {
                    applyCache(languageClusterCache, requirement)
                }

                else -> {
                    requirement.getCurrentProgress(gameHistory)
                }
            }
            requiredAmount = requirement.provideRequiredAmount()
        }
        return AchievementDataItem(
            currAchievement,
            calculatedProgress,
            completionTime,
            requiredAmount
        )
    }

    private fun applyCache(cache: AchievementProgressCache, requirement: GameRequirement.WithProgress) : Int {
        return if (cache.isCached()) {
            cache.get()
        } else {
            cache.put(requirement.getCurrentProgress(gameHistory))
            cache.get()
        }
    }
}

data class AchievementDataItem(
    val achievement: Achievement,
    val calculatedProgress: Int,
    val completionTime: Long,
    val requiredAmount: Int
)