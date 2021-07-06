package com.example.typego.utils;

import androidx.annotation.NonNull;

import com.example.typego.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class Achievement {
    private final int assignedImageId;
    private final String name;
    private final String description;
    private Date completionDate;
    private boolean progressAttached;

   private Achievement(@NonNull String name, @NonNull String description, int assignedImageId, boolean progressAttached) {
       this.name = name;
       this.description = description;
       this.assignedImageId = assignedImageId;
       this.progressAttached = progressAttached;
   }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public int getAssignedImageId() {
        return assignedImageId;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isProgressAttached() {
        return progressAttached;
    }

    @NotNull
    public static ArrayList<Achievement> getAchievementsList() {
       ArrayList<Achievement> achievements = new ArrayList<>();
       achievements.add(new Achievement(
               "The first step",
               "Complete a test for the first time",
               R.drawable.ic_achievement_star,
               false));
       achievements.add(new Achievement(
               "High five!",
               "Complete a test with at least 5 WPM",
               R.drawable.ic_account,
               true));
       return achievements;
    }

}
