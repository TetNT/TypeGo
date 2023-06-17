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
import com.tetsoft.typego.data.achievement.deprecated.Achievement
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
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

    override fun onBindViewHolder(holder: AchievementsViewHolder, position: Int) {
        val currAchievement = achievements[position]
        holder.tvAchievementName.text = currAchievement.name
        holder.tvAchievementDescription.text = currAchievement.description
        holder.imgAchievement.setImageResource(currAchievement.assignedImageId)
        // TODO: Remove if-else and replace visibility toggle with the VisibilityMapper
        if (!currAchievement.isProgressAttached) {
            holder.progressBarAchievement.visibility = View.GONE
            holder.tvProgressDescription.visibility = View.GONE
        } else {
            val currRequirement = currAchievement.requirements[0]
            holder.progressBarAchievement.max = currRequirement.requiredAmount
            holder.progressBarAchievement.progress = currRequirement.getCurrentProgress(resultList)
            holder.tvProgressDescription.text = context.getString(
                R.string.achievement_progress,
                currRequirement.getCurrentProgress(resultList),
                currRequirement.requiredAmount
            )
        }
        val completionDateTime = achievementsProgressList[currAchievement.id].completionDateTimeLong
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