package com.example.typego.adapters;

import android.content.Context;
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
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder> {
    private ArrayList<Achievement> achievements;
    private Context context;

    public AchievementsAdapter(@NonNull Context context, @NonNull ArrayList<Achievement> achievements) {
        this.context = context;
        this.achievements = achievements;
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

    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    class AchievementViewHolder extends RecyclerView.ViewHolder {
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
