package com.tetsoft.typego.data.achievement;

import java.util.ArrayList;

public class AchievementMigration {
    ArrayList<Achievement> oldVersion;
    ArrayList<Achievement> newVersion;


    public AchievementMigration(ArrayList<Achievement> oldVersion, ArrayList<Achievement> newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        if (notNumerated()) numerateAchievements();
    }

    // Returns the new version of achievements with saved progress from the old version
    public ArrayList<Achievement> getMergedAchievementList() {
        for (int i = 0; i < newVersion.size(); i++) {
            Achievement newAchievement = newVersion.get(i);
            int foundIndex = findIndexOfOldAchievementId(newAchievement.getId());
            if (foundIndex != -1) {
                newVersion.set(i, oldVersion.get(foundIndex));
            }
        }
        return newVersion;
    }


    private int findIndexOfOldAchievementId(int id) {
        for (int i = 0; i < oldVersion.size(); i++) {
            Achievement achievement = oldVersion.get(i);
            if (achievement.getId() == id) return i;
        }
        return -1;
    }

    // Returns true if any of id is containing 0
    private boolean notNumerated() {
        for (Achievement achievement: oldVersion) {
            if (achievement.getId() == 0) return true;
        }
        return false;
    }

    // Gives an id for each achievement.
    // It's done to migrate from previous versions of achievement list when there was no id field.
    private void numerateAchievements() {
        for (int i = 0; i < oldVersion.size(); i++) {
            oldVersion.get(i).setId(i+1);
        }
    }
}
