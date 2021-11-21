package com.tetsoft.typego.account;

import android.content.Context;

import com.tetsoft.typego.achievement.Achievement;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.TimeConvert;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Statistics {
    private final User user;
    private final ArrayList<TypingResult> results;

    public Statistics(User user) {
        this.user = user;
        results = user.getResultList();
    }

    public String getFavoriteLanguageName(Context context) {
        if (results.isEmpty()) return "";
        Hashtable<Language, Integer> usedLanguages = new Hashtable<>();
        for (TypingResult result : results) {
            Language pickedLanguage = result.getLanguage();
            Integer languageFrequency = 0;
            if (usedLanguages.containsKey(pickedLanguage)) {
                languageFrequency = usedLanguages.get(pickedLanguage);
            }
            usedLanguages.put(pickedLanguage, languageFrequency + 1);
        }
        Set<Language> languageKeys = usedLanguages.keySet();
        Iterator<Language> iterator = languageKeys.iterator();
        int biggestAmount = 0;
        Language mostFrequentLanguage = null;
        while (iterator.hasNext()){
            Language currentLanguage = iterator.next();
            int currentAmount = usedLanguages.get(currentLanguage);
            if (currentAmount > biggestAmount) {
                biggestAmount = currentAmount;
                mostFrequentLanguage = currentLanguage;
            }
        }
        if (mostFrequentLanguage == null) return "";
        return mostFrequentLanguage.getName(context);
    }

    public String getFavoriteTimeMode(Context context) {
        if (results.isEmpty()) return "";
        Hashtable<Integer, Integer> usedTimeModes = new Hashtable<>();
        for (TypingResult result : results) {
            Integer pickedTimeMode = result.getTimeInSeconds();
            Integer timeModeFrequency = 0;
            if (usedTimeModes.containsKey(pickedTimeMode)) {
                timeModeFrequency = usedTimeModes.get(pickedTimeMode);
            }
            usedTimeModes.put(pickedTimeMode, timeModeFrequency + 1);
        }
        Iterator<Integer> iterator = usedTimeModes.keySet().iterator();
        int biggestAmount = 0;
        Integer mostFrequentTimeMode = 0;
        while (iterator.hasNext()){
            Integer currentTimeMode = iterator.next();
            int currentAmount = usedTimeModes.get(currentTimeMode);
            if (currentAmount > biggestAmount) {
                biggestAmount = currentAmount;
                mostFrequentTimeMode = currentTimeMode;
            }
        }
        return TimeConvert.convertSeconds(context, mostFrequentTimeMode);
    }

    public int getDoneAchievementsCount() {
        int count = 0;
        for (Achievement achievement: user.getAchievements())
            if (achievement.isCompleted()) count++;
        return count;
    }

    // Returns the percentage of done achievements out of all achievements.
    public int getAchievementsProgressInPercents() {
        if (getDoneAchievementsCount() == 0 || user.getAchievements().size() == 0) return 0;
        return (getDoneAchievementsCount()*100)/user.getAchievements().size();
    }

    final int PICK_SIZE = 10;
    public int getAveragePastWPM() {
        if (results.size() < PICK_SIZE * 2) return 0;
        int WPM = 0;
        int PICK_ENHANCEMENT = PICK_SIZE + (results.size()/5);
        for (int i = results.size()-1; i > results.size() - PICK_ENHANCEMENT; i--)
            WPM += results.get(i).getWPM();
        return WPM/PICK_ENHANCEMENT;
    }

    public int getAverageCurrentWPM() {
        if (results.size() < PICK_SIZE * 2) return 0;
        int WPM = 0;
        for (int i = 0; i < PICK_SIZE; i++)
            WPM += results.get(i).getWPM();
        if (WPM == 0) return 0;
        return WPM/PICK_SIZE;
    }

    public int getProgression() {
        int pastWPM = getAveragePastWPM();
        int currWPM = getAverageCurrentWPM();
        if (pastWPM == 0 || currWPM == 0) return 0;
        return 100 - (100*pastWPM) / currWPM;
    }

    public int getDaysSinceFirstTest() {
        if (results.size() == 0) return 0;
        Date firstResultCompletionDate = results.get(results.size()-1).getCompletionDateTime();
        Date lastResultCompletionDate = results.get(0).getCompletionDateTime();
        long dateDiff = lastResultCompletionDate.getTime() - firstResultCompletionDate.getTime();
        return (int)TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    }

    public int getTotalTimeSpent() {
        int total = 0;
        for (TypingResult result : results
             ) {
            total += result.getTimeInSeconds();
        }
        return total;
    }

    public int getTotalWordsWritten() {
        int total = 0;
        for (TypingResult result: results
             ) {
            total += result.getCorrectWords();
            total += result.getIncorrectWords();
        }
        return total;
    }

    public int getAccuracy() {
        int correctWordsCount = 0;
        int incorrectWordsCount = 0;
        for (TypingResult result: results) {
            correctWordsCount += result.getCorrectWords();
            incorrectWordsCount += result.getIncorrectWords();
        }
        if (correctWordsCount == 0 & incorrectWordsCount > 0) return 0;
        if (incorrectWordsCount == 0 & correctWordsCount > 0) return 100;
        return 100 - ((100 * incorrectWordsCount) / correctWordsCount);
    }

    public String getLastCompletedAchievementName() {
        Date lastCompletionDate = new Date(0);
        Achievement lastCompletedAchievement = null;
        for (Achievement achievement: user.getAchievements()) {
            if (achievement.getCompletionDate()!=null) {
                if (achievement.getCompletionDate().getTime() > lastCompletionDate.getTime()) {
                    lastCompletedAchievement = achievement;
                    lastCompletionDate = achievement.getCompletionDate();
                }
            }
        }
        if (lastCompletedAchievement == null) return "";
        return lastCompletedAchievement.getName();
    }

}
