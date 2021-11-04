package com.tetsoft.typego;

import com.tetsoft.typego.achievement.Achievement;
import com.tetsoft.typego.achievement.AchievementMigration;
import com.tetsoft.typego.achievement.RequirementFactory;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class AchievementsListTest {
    @Test
    public void mergeIsCorrect() {

        ArrayList<Achievement> oldAchievements = new ArrayList<>();
        oldAchievements.add(new Achievement(0, "Prev", "Test", R.drawable.ic_achievement_star,
                false, RequirementFactory.passedTestsAmount(1)));
        oldAchievements.add(new Achievement(0, "Prev", "Test", R.drawable.ic_child,
                true, RequirementFactory.WPMisMoreThan(20)));
        oldAchievements.add(new Achievement(0, "Prev", "Test", R.drawable.ic_star,
                true, RequirementFactory.WPMisMoreThan(40)));
        oldAchievements.add(new Achievement(0, "Prev", "Test", R.drawable.ic_keyboard,
                true, RequirementFactory.WPMisMoreThan(50)));

        ArrayList<Achievement> newAchievements = new ArrayList<>();
        newAchievements.add(new Achievement(1, "New", "Test", R.drawable.ic_achievement_star,
                false, RequirementFactory.passedTestsAmount(1)));
        newAchievements.add(new Achievement(3, "New", "Test", R.drawable.ic_star,
                true, RequirementFactory.WPMisMoreThan(40)));
        newAchievements.add(new Achievement(4, "New", "Test", R.drawable.ic_keyboard,
                true, RequirementFactory.WPMisMoreThan(50)));
        newAchievements.add(new Achievement(8, "New", "Test", R.drawable.ic_unmistakable,
                false, RequirementFactory.timeModeNoMistakes(30)));
        AchievementMigration migration = new AchievementMigration(oldAchievements, newAchievements);
        ArrayList<Achievement> merged = migration.getMergedAchievementList();
        assertEquals(merged.get(0).getName(), "Prev");
        assertEquals(merged.get(1).getName(), "Prev");
        assertEquals(merged.get(2).getName(), "Prev");
        assertEquals(merged.get(3).getName(), "New");
        assertEquals(merged.get(0).getId(), 1);
    }
}
