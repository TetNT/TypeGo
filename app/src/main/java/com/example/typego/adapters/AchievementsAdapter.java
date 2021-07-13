package com.example.typego.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typego.R;
import com.example.typego.utils.Achievement;
import com.example.typego.utils.AchievementRequirement;
import com.example.typego.utils.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder> {
    private final User user;
    private final ArrayList<Achievement> achievements;
    private final Context context;

    public AchievementsAdapter(@NonNull Context context, @NonNull ArrayList<Achievement> achievements, User user) {
        this.context = context;
        this.achievements = achievements;
        this.user = user;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.achievements_item, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement currAchievement = achievements.get(position);
        holder.tvAchievementName.setText(currAchievement.getName());
        holder.tvAchievementDescription.setText(currAchievement.getDescription());
        holder.imgAchievement.setImageResource(currAchievement.getAssignedImageId());
        if (!currAchievement.isProgressAttached()) {
            holder.progressBarAchievement.setVisibility(View.GONE);
            holder.tvProgressDescription.setVisibility(View.GONE);
        }
        else {
            AchievementRequirement currRequirement = currAchievement.getRequirements().get(0);
            holder.progressBarAchievement.setMax(currRequirement.getRequiredAmount());
            holder.progressBarAchievement.setProgress(currRequirement.getCurrentProgress(user));
            holder.tvProgressDescription.setText(
                    context.getString(
                            R.string.achievement_progress,
                            currRequirement.getCurrentProgress(user),
                            currRequirement.getRequiredAmount()));
        }
        if (currAchievement.getCompletionDate()==null) {
            holder.tvCompletionDate.setVisibility(View.INVISIBLE);
            holder.imgAchievement.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        }
        else {
            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.SHORT);
            holder.tvCompletionDate.setText(dateFormat.format(currAchievement.getCompletionDate()));
        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {
        TextView tvAchievementName, tvAchievementDescription, tvCompletionDate, tvProgressDescription;
        ImageView imgAchievement;
        ProgressBar progressBarAchievement;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            this.tvAchievementName = itemView.findViewById(R.id.tvAchievementName);
            this.tvAchievementDescription = itemView.findViewById(R.id.tvAchievementDescription);
            this.imgAchievement = itemView.findViewById(R.id.imgAchievement);
            this.progressBarAchievement = itemView.findViewById(R.id.progressbarAchievement);
            this.tvCompletionDate = itemView.findViewById(R.id.tvCompletionTime);
            this.tvProgressDescription = itemView.findViewById(R.id.tvProgressDescription);
        }
    }
}
