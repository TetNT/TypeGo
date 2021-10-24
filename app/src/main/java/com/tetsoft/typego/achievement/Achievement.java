package com.tetsoft.typego.achievement;

import android.content.Context;
import androidx.annotation.NonNull;
import com.tetsoft.typego.R;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.account.User;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Date;

public class Achievement {
    private final int assignedImageId;
    private final String name;
    private final String description;
    private Date completionDate;
    private final boolean progressAttached;
    private final ArrayList<AchievementRequirement> requirements;

   private Achievement(@NonNull String name, @NonNull String description, int assignedImageId, boolean progressAttached, ArrayList<AchievementRequirement> requirements) {
       this.name = name;
       this.description = description;
       this.assignedImageId = assignedImageId;
       this.progressAttached = progressAttached;
       this.requirements = requirements;
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

    public ArrayList<AchievementRequirement> getRequirements() {
        return requirements;
    }

    @NotNull
    public static ArrayList<Achievement> getAchievementList(Context context) {
        ArrayList<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement(
                context.getString(R.string.the_first_step),
                context.getString(R.string.the_first_step_desc),
                R.drawable.ic_achievement_star,
                false,
                RequirementFactory.passedTestsAmount(1)));
        achievements.add(new Achievement(
                context.getString(R.string.beginner),
                context.getString(R.string.typewriter_wpm, 20),
                R.drawable.ic_child,
                true,
                RequirementFactory.WPMisMoreThan(20)));
        achievements.add(new Achievement(
                context.getString(R.string.promising),
                context.getString(R.string.typewriter_wpm, 40),
                R.drawable.ic_star,
                true,
                RequirementFactory.WPMisMoreThan(40)));
        achievements.add(new Achievement(
                context.getString(R.string.typewriter),
                context.getString(R.string.typewriter_wpm, 50),
                R.drawable.ic_keyboard,
                true,
                RequirementFactory.WPMisMoreThan(50)));
        achievements.add(new Achievement(
                context.getString(R.string.type_bachelor),
                context.getString(R.string.typewriter_wpm, 60),
                R.drawable.ic_bachelor,
                true,
                RequirementFactory.WPMisMoreThan(60)));
        achievements.add(new Achievement(
                context.getString(R.string.mastermind),
                context.getString(R.string.typewriter_wpm, 70),
                R.drawable.ic_brain,
                true,
                RequirementFactory.WPMisMoreThan(70)));
        achievements.add(new Achievement(
                context.getString(R.string.alien),
                context.getString(R.string.typewriter_wpm, 80),
                R.drawable.ic_alien,
                true,
                RequirementFactory.WPMisMoreThan(80)));
        achievements.add(new Achievement(
                context.getString(R.string.unmistakable, "I"),
                context.getString(R.string.unmistakable_desc_with_seconds, 30, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(30)));
        achievements.add(new Achievement(
                context.getString(R.string.unmistakable, "II"),
                context.getString(R.string.unmistakable_desc_with_minutes, 1, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(60)));
        achievements.add(new Achievement(
                context.getString(R.string.unmistakable, "III"),
                context.getString(R.string.unmistakable_desc_with_minutes, 2, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(120)));
        achievements.add(new Achievement(
                context.getString(R.string.unmistakable, "IV"),
                context.getString(R.string.unmistakable_desc_with_minutes, 3, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(180)));
        achievements.add(new Achievement(
                context.getString(R.string.unmistakable, "V"),
                context.getString(R.string.unmistakable_desc_with_minutes, 5, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(300)));
        achievements.add(new Achievement(
                context.getString(R.string.big_fan, "I"),
                context.getString(R.string.big_fan_desc, 10),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(10)));
        achievements.add(new Achievement(
                context.getString(R.string.big_fan, "II"),
                context.getString(R.string.big_fan_desc, 50),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(50)));
        achievements.add(new Achievement(
                context.getString(R.string.big_fan, "III"),
                context.getString(R.string.big_fan_desc, 100),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(100)));
        achievements.add(new Achievement(
                context.getString(R.string.big_fan, "IV"),
                context.getString(R.string.big_fan_desc, 200),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(200)));
        achievements.add(new Achievement(
                context.getString(R.string.big_fan, "V"),
                context.getString(R.string.big_fan_desc, 500),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(500)));
        return achievements;
    }


    public boolean isCompleted(User user, TypingResult result) {
       for (AchievementRequirement requirement: requirements)
           // if any of the requirements isn't complete then return false
           if (!requirement.isMatching(user, result))
               return false;
           return true;
    }

}
