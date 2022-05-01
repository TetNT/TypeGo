package com.tetsoft.typego;

import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair;
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList;

import org.junit.Test;

import static org.junit.Assert.*;

public class AchievementsListTest {
    @Test
    public void achievementsProgressListSizeIsCorrect() {
        AchievementsProgressList achievementsProgressList = new AchievementsProgressList();
        achievementsProgressList.add(new AchievementsCompletionPair(1, 1649778842000L));
        achievementsProgressList.add(new AchievementsCompletionPair(2, 1649778882000L));
        achievementsProgressList.add(new AchievementsCompletionPair(3, 0L));
        achievementsProgressList.add(new AchievementsCompletionPair(4, 0L));
        achievementsProgressList.add(new AchievementsCompletionPair(5, 0L));
        achievementsProgressList.add(new AchievementsCompletionPair(6, 1649778842000L));
        assertEquals(6, achievementsProgressList.size());


    }
}
