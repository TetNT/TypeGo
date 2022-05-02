package com.tetsoft.typego.data.achievement;

import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair;
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList;
import com.tetsoft.typego.storage.AchievementsProgressStorage;

import java.util.ArrayList;

public class AchievementMigration {
    ArrayList<Achievement> oldVersion;

    public AchievementMigration(ArrayList<Achievement> currentAchievementsProgress) {
        oldVersion = currentAchievementsProgress;
    }

    public void storeProgress(AchievementsProgressStorage achievementsProgressStorage) {
        if (notNumerated()) numerateAchievements();
        AchievementsProgressList achievementsProgressList = new AchievementsProgressList();
        long timeLong;
        for (Achievement achievement : oldVersion) {
            timeLong = 0L;
            if (achievement.getCompletionDate() != null) {
                timeLong = achievement.getCompletionDate().getTime();
            }
            achievementsProgressList.add(new AchievementsCompletionPair(achievement.getId(), timeLong));
        }
        achievementsProgressStorage.store(achievementsProgressList);
    }


    // Returns true if any of id is 0
    private boolean notNumerated() {
        for (Achievement achievement : oldVersion) {
            if (achievement.getId() == 0) return true;
        }
        return false;
    }

    // Gives an id for each achievement.
    // It's done to migrate from previous versions of achievement list when there was no id field.
    private void numerateAchievements() {
        for (int i = 0; i < oldVersion.size(); i++) {
            oldVersion.get(i).setId(i + 1);
        }
    }
}
