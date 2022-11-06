package com.tetsoft.typego.data.account;

import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList;
import com.tetsoft.typego.data.language.LanguageList;
import com.tetsoft.typego.data.achievement.Achievement;
import com.tetsoft.typego.data.achievement.AchievementList;
import com.tetsoft.typego.game.result.GameResult;
import com.tetsoft.typego.game.result.GameResultList;
import com.tetsoft.typego.storage.AchievementsProgressStorage;
import com.tetsoft.typego.storage.GameResultListStorage;
import com.tetsoft.typego.data.language.Language;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Statistics {
    private final GameResultList results;

    public Statistics(GameResultListStorage resultListStorage) {
        results = resultListStorage.get();
    }

    public int getResultsCount() {
        return results.size();
    }

    public GameResultList getResults() {
        return results;
    }

    public Language getFavoriteLanguage() {
        if (results.isEmpty()) return null;
        Language mostFrequentLanguage = null;
        int mostFrequency = 0;
        for (Language language : new LanguageList().getList()) {
            int langFrequency = results.getResultsByLanguage(language).size();
            if (langFrequency > mostFrequency) {
                mostFrequency = langFrequency;
                mostFrequentLanguage = language;
            }
        }
        return mostFrequentLanguage;
    }

    public int getDoneAchievementsCount(AchievementsProgressStorage progressStorage, AchievementList achievementList) {
        int count = 0;
        AchievementsProgressList completedAchievements = progressStorage.getAll();
        for (Achievement achievement : achievementList.get())
            //if (progressStorage.achievementCompleted(String.valueOf(achievement.getId()))) count++;
            if (completedAchievements.get(achievement.getId()).isCompleted()) count++;
        return count;
    }

    // Returns the percentage of done achievements out of all achievements.
    public int getAchievementsProgressInPercents(AchievementsProgressStorage progressStorage, AchievementList achievementList) {
        int achievementsSize = achievementList.get().size();
        int doneAchievements = getDoneAchievementsCount(progressStorage, achievementList);
        if (doneAchievements == 0 || achievementsSize == 0) return 0;
        return (doneAchievements * 100) / achievementsSize;
    }

    final int PICK_SIZE = 10;
    public int getAveragePastWPM() {
        if (results.size() < PICK_SIZE * 2) return 0;
        int WPM = 0;
        int PICK_ENHANCEMENT = PICK_SIZE + (results.size() / 5);
        for (int i = results.size() - 1; i > results.size() - PICK_ENHANCEMENT; i--)
            WPM += results.get(i).getWpm();
        return WPM / PICK_ENHANCEMENT;
    }

    public int getAverageCurrentWPM() {
        if (results.size() < PICK_SIZE * 2) return 0;
        int WPM = 0;
        for (int i = 0; i < PICK_SIZE; i++)
            WPM += results.get(i).getWpm();
        if (WPM == 0) return 0;
        return WPM / PICK_SIZE;
    }

    public int getProgression() {
        int pastWPM = getAveragePastWPM();
        int currWPM = getAverageCurrentWPM();
        if (pastWPM == 0 || currWPM == 0) return 0;
        return 100 - (100 * pastWPM) / currWPM;
    }

    public int getDaysSinceFirstTest() {
        if (results.size() == 0) return 0;
        Date firstResultCompletionDate = new Date(results.get(0).getCompletionDateTime());
        Date lastResultCompletionDate = new Date(results.get(results.size() - 1).getCompletionDateTime());
        long dateDiff = lastResultCompletionDate.getTime() - firstResultCompletionDate.getTime();
        return (int) TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    }

    public int getTotalTimeSpentInMinutes() {
        int total = 0;
        for (GameResult result : results
        ) {
            total += result.getTimeSpentInSeconds();
        }
        return total / 60;
    }

    public int getTotalWordsWritten() {
        int total = 0;
        for (GameResult result : results) {
            total += result.getWordsWritten();
        }
        return total;
    }

    public int getAccuracy() {
        int correctWordsCount = 0;
        int incorrectWordsCount = 0;
        for (GameResult result : results) {
            correctWordsCount += result.getCorrectWords();
            incorrectWordsCount += result.getWordsWritten() - result.getCorrectWords();
        }
        if (correctWordsCount == 0 & incorrectWordsCount > 0) return 0;
        if (incorrectWordsCount == 0 & correctWordsCount > 0) return 100;
        return 100 - ((100 * incorrectWordsCount) / correctWordsCount);
    }

    public String getLastCompletedAchievementName(AchievementsProgressStorage progressStorage, AchievementList achievementList) {
        long lastCompletionDate = 0L;
        Achievement lastCompletedAchievement = null;
        for (Achievement achievement : achievementList.get()) {
            //long completionTimeMillis = progressStorage.getCompletionDateTimeLong(String.valueOf(achievement.getId()));
            long completionTimeMillis = progressStorage.getAll().get(achievement.getId()).getCompletionDateTimeLong();
            if (completionTimeMillis != 0L) {
                if (completionTimeMillis > lastCompletionDate) {
                    lastCompletedAchievement = achievement;
                    lastCompletionDate = completionTimeMillis;
                }
            }
        }
        if (lastCompletedAchievement == null) return "";
        return lastCompletedAchievement.getName();
    }

    private Date getBestResultCompletionDate() {
        GameResult best = results.getBestResult();
        if (best == null) return null;
        return new Date(best.getCompletionDateTime());
    }

    public GameResult getBestResult() {
        return results.getBestResult();
    }


    public int getDaysSinceRecordHasBeenSet() {
        Date firstResultCompletionDate = getBestResultCompletionDate();
        if (firstResultCompletionDate == null) return 0;
        Date lastResultCompletionDate = Calendar.getInstance().getTime();
        long dateDiff = lastResultCompletionDate.getTime() - firstResultCompletionDate.getTime();
        return (int) TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    }


}
