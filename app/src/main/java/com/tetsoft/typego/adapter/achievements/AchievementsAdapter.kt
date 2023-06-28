package com.tetsoft.typego.adapter.achievements

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.requirement.GameRequirement
import java.text.SimpleDateFormat
import java.util.*

class AchievementsAdapter(
    private val context: Context,
    private val achievements: List<Achievement>,
    private val resultList: ClassicGameModesHistoryList,
    private val achievementsProgressList: AchievementsProgressList
) : RecyclerView.Adapter<AchievementsAdapter.AchievementsViewHolder>() {

    inner class AchievementsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvAchievementName: TextView = itemView.findViewById(R.id.tvAchievementName)
        val tvAchievementDescription: TextView = itemView.findViewById(R.id.tvAchievementDescription)
        val tvCompletionDate: TextView = itemView.findViewById(R.id.tvCompletionTime)
        val tvProgressDescription: TextView = itemView.findViewById(R.id.tvProgressDescription)
        val imgAchievement: ImageView = itemView.findViewById(R.id.imgAchievement)
        val progressBarAchievement: ProgressBar = itemView.findViewById(R.id.progressbarAchievement)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.achievements_item, parent, false)
        return AchievementsViewHolder(view)
    }

    // TODO: Refactor
    override fun onBindViewHolder(holder: AchievementsViewHolder, position: Int) {
        val currAchievement = achievements[position]
        holder.tvAchievementName.text = currAchievement.getName(context)
        holder.tvAchievementDescription.text = currAchievement.getDescription(context)
        holder.imgAchievement.setImageResource(currAchievement.getAchievementImageId())
        // TODO: Remove if-else and replace visibility toggle with the VisibilityMapper
        if (!currAchievement.withProgressBar()) {
            holder.progressBarAchievement.visibility = View.GONE
            holder.tvProgressDescription.visibility = View.GONE
        } else {
            if (currAchievement.getRequirement() is GameRequirement.WithProgress) {
                val requirement = currAchievement.getRequirement() as GameRequirement.WithProgress
                val progress = requirement.getCurrentProgress(resultList)
                holder.progressBarAchievement.max = requirement.provideRequiredAmount()
                holder.progressBarAchievement.progress = progress
                holder.tvProgressDescription.text = context.getString(
                    R.string.achievement_progress,
                    progress,
                    requirement.provideRequiredAmount()
                )
            }

        }
        val completionDateTime = achievementsProgressList[currAchievement.getId()].completionDateTimeLong
        if (completionDateTime == 0L) {
            holder.tvCompletionDate.visibility = View.INVISIBLE
            holder.imgAchievement.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
        } else {
            val dateFormat = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.DEFAULT,
                SimpleDateFormat.SHORT
            )
            holder.tvCompletionDate.text = dateFormat.format(Date(completionDateTime))
        }
    }

    override fun getItemCount(): Int {
        return achievements.size
    }
}