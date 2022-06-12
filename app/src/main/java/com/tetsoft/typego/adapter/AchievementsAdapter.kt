package com.tetsoft.typego.adapter

import android.content.Context
import android.graphics.Color
import com.tetsoft.typego.data.achievement.Achievement
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.adapter.AchievementsAdapter.AchievementViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tetsoft.typego.R
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AchievementsAdapter(
    private val context: Context,
    private val achievements: ArrayList<Achievement>,
    private val resultList: GameResultList,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : RecyclerView.Adapter<AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.achievements_item, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currAchievement = achievements[position]
        holder.tvAchievementName.text = currAchievement.name
        holder.tvAchievementDescription.text = currAchievement.description
        holder.imgAchievement.setImageResource(currAchievement.assignedImageId)
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
        val completionDateTime = achievementsProgressStorage.getCompletionDateTimeLong(currAchievement.id.toString())
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

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAchievementName: TextView
        var tvAchievementDescription: TextView
        var tvCompletionDate: TextView
        var tvProgressDescription: TextView
        var imgAchievement: ImageView
        var progressBarAchievement: ProgressBar

        init {
            tvAchievementName = itemView.findViewById(R.id.tvAchievementName)
            tvAchievementDescription = itemView.findViewById(R.id.tvAchievementDescription)
            imgAchievement = itemView.findViewById(R.id.imgAchievement)
            progressBarAchievement = itemView.findViewById(R.id.progressbarAchievement)
            tvCompletionDate = itemView.findViewById(R.id.tvCompletionTime)
            tvProgressDescription = itemView.findViewById(R.id.tvProgressDescription)
        }
    }
}