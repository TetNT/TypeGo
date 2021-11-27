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
    private int id;
    private final int assignedImageId;
    private final String name;
    private final String description;
    private Date completionDate;
    private final boolean progressAttached;
    // Progress will be shown based of it's very first value.
    private final ArrayList<AchievementRequirement> requirements;

   public Achievement(int id, @NonNull String name, @NonNull String description, int assignedImageId, boolean progressAttached, ArrayList<AchievementRequirement> requirements) {
       this.id = id;
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

    public int getId() {
       return id;
    }

    public void setId(int id) {
       this.id = id;
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
        achievements.add(new Achievement(1,
                context.getString(R.string.the_first_step),
                context.getString(R.string.the_first_step_desc),
                R.drawable.ic_achievement_star,
                false,
                RequirementFactory.passedTestsAmount(1)));
        achievements.add(new Achievement(2,
                context.getString(R.string.beginner),
                context.getString(R.string.typewriter_wpm, 20),
                R.drawable.ic_child,
                true,
                RequirementFactory.WPMisMoreThan(20)));
        achievements.add(new Achievement(3,
                context.getString(R.string.promising),
                context.getString(R.string.typewriter_wpm, 40),
                R.drawable.ic_star,
                true,
                RequirementFactory.WPMisMoreThan(40)));
        achievements.add(new Achievement(4,
                context.getString(R.string.typewriter),
                context.getString(R.string.typewriter_wpm, 50),
                R.drawable.ic_keyboard,
                true,
                RequirementFactory.WPMisMoreThan(50)));
        achievements.add(new Achievement(5,
                context.getString(R.string.type_bachelor),
                context.getString(R.string.typewriter_wpm, 60),
                R.drawable.ic_bachelor,
                true,
                RequirementFactory.WPMisMoreThan(60)));
        achievements.add(new Achievement(6,
                context.getString(R.string.mastermind),
                context.getString(R.string.typewriter_wpm, 70),
                R.drawable.ic_brain,
                true,
                RequirementFactory.WPMisMoreThan(70)));
        achievements.add(new Achievement(7,
                context.getString(R.string.alien),
                context.getString(R.string.typewriter_wpm, 80),
                R.drawable.ic_alien,
                true,
                RequirementFactory.WPMisMoreThan(80)));
        achievements.add(new Achievement(8,
                context.getString(R.string.unmistakable, "I"),
                context.getString(R.string.unmistakable_desc_with_seconds, 30, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(30)));
        achievements.add(new Achievement(9,
                context.getString(R.string.unmistakable, "II"),
                context.getString(R.string.unmistakable_desc_with_minutes, 1, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(60)));
        achievements.add(new Achievement(10,
                context.getString(R.string.unmistakable, "III"),
                context.getString(R.string.unmistakable_desc_with_minutes, 2, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(120)));
        achievements.add(new Achievement(11,
                context.getString(R.string.unmistakable, "IV"),
                context.getString(R.string.unmistakable_desc_with_minutes, 3, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(180)));
        achievements.add(new Achievement(12,
                context.getString(R.string.unmistakable, "V"),
                context.getString(R.string.unmistakable_desc_with_minutes, 5, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(300)));
        achievements.add(new Achievement(13,
                context.getString(R.string.big_fan, "I"),
                context.getString(R.string.big_fan_desc, 10),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(10)));
        achievements.add(new Achievement(14,
                context.getString(R.string.big_fan, "II"),
                context.getString(R.string.big_fan_desc, 50),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(50)));
        achievements.add(new Achievement(15,
                context.getString(R.string.big_fan, "III"),
                context.getString(R.string.big_fan_desc, 100),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(100)));
        achievements.add(new Achievement(16,
                context.getString(R.string.big_fan, "IV"),
                context.getString(R.string.big_fan_desc, 200),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(200)));
        achievements.add(new Achievement(17,
                context.getString(R.string.big_fan, "V"),
                context.getString(R.string.big_fan_desc, 500),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(500)));
        achievements.add(new Achievement(18,
                context.getString(R.string.double_agent),
                context.getString(R.string.polyglot_desc, 2),
                R.drawable.ic_language,
                true,
                RequirementFactory.differentLanguagesAmount(2)));
        achievements.add(new Achievement(19,
                context.getString(R.string.linguist),
                context.getString(R.string.polyglot_desc, 3),
                R.drawable.ic_language,
                true,
                RequirementFactory.differentLanguagesAmount(3)));
        achievements.add(new Achievement(19,
                context.getString(R.string.polyglot),
                context.getString(R.string.polyglot_desc, 5),
                R.drawable.ic_language,
                true,
                RequirementFactory.differentLanguagesAmount(5)));
        return achievements;
    }

    public boolean isCompleted() {
       return completionDate != null;
    }

    // Returns true if user completed the achievement.
    public boolean requirementsAreComplete(User user, TypingResult result) {
       for (AchievementRequirement requirement: requirements)
           // if any of the requirements isn't complete then return false
           if (!requirement.isMatching(user, result))
               return false;
           return true;
    }

}
