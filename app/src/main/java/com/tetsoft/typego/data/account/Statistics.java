package com.tetsoft.typego.data.account;

import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.data.achievement.Achievement;
import com.tetsoft.typego.testing.ResultListUtils;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.data.Language;
import com.tetsoft.typego.data.TimeMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Statistics {
    private final User user;
    private final ArrayList<TypingResult> results;

    public Statistics(User user) {
        this.user = user;
        results = user.getResultList();
    }

    public int getResultsCount() {
        return results.size();
    }

    public ArrayList<TypingResult> getResults() {
        return results;
    }

    public Language getFavoriteLanguage() {
        if (results.isEmpty()) return null;
        Language mostFrequentLanguage = null;
        int mostFrequency = 0;
        for (Language language : new LanguageList().getList()) {
            int langFrequency = ResultListUtils.getResultsByLanguage(results, language).size();
            if (langFrequency > mostFrequency) {
                mostFrequency = langFrequency;
                mostFrequentLanguage = language;
            }
        }
        return mostFrequentLanguage;
    }

    public TimeMode getFavoriteTimeMode() {
        if (results.isEmpty()) return null;
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
        return new TimeMode(mostFrequentTimeMode);
    }

    public int getDoneAchievementsCount() {
        int count = 0;
        for (Achievement achievement: user.getAchievements())
            if (achievement.isCompleted()) count++;
        return count;
    }

    // Returns the percentage of done achievements.
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
            WPM += results.get(i).getWpm();
        return WPM/PICK_ENHANCEMENT;
    }

    public int getAverageCurrentWPM() {
        if (results.size() < PICK_SIZE * 2) return 0;
        int WPM = 0;
        for (int i = 0; i < PICK_SIZE; i++)
            WPM += results.get(i).getWpm();
        if (WPM == 0) return 0;
        return WPM/PICK_SIZE;
    }

    public int getProgression() {
        int pastWPM = getAveragePastWPM();
        int currWPM = getAverageCurrentWPM();
        if (pastWPM == 0 || currWPM == 0) return -1;
        return 100 - (100*pastWPM) / currWPM;
    }

    public int getDaysSinceFirstTest() {
        if (results.size() == 0) return 0;
        Date firstResultCompletionDate = results.get(results.size()-1).getCompletionDateTime();
        Date lastResultCompletionDate = results.get(0).getCompletionDateTime();
        long dateDiff = lastResultCompletionDate.getTime() - firstResultCompletionDate.getTime();
        return (int)TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    }

    public int getTotalTimeSpentInMinutes() {
        int total = 0;
        for (TypingResult result : results) {
            total += result.getTimeInSeconds();
        }
        return total/60;
    }

    public int getTotalWordsWritten() {
        int total = 0;
        for (TypingResult result: results) {
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
            if (achievement.isCompleted()) {
                if (achievement.getCompletionDate().getTime() > lastCompletionDate.getTime()) {
                    lastCompletedAchievement = achievement;
                    lastCompletionDate = achievement.getCompletionDate();
                }
            }
        }
        if (lastCompletedAchievement == null) return "";
        return lastCompletedAchievement.getName();
    }

    public int getCorrectCharactersTotal() {
        int total = 0;
        for (TypingResult result: results) {
            total += result.getCorrectWordsWeight();
        }
        return total;
    }



    private Date getBestResultCompletionDate() {
        TypingResult best = ResultListUtils.getBestResult(results);
        if (best == null) return null;
        return best.getCompletionDateTime();
    }

    public TypingResult getBestResult() {
        return ResultListUtils.getBestResult(results);
    }


    public int getDaysSinceRecordHasBeenSet() {
        Date firstResultCompletionDate = getBestResultCompletionDate();
        if (firstResultCompletionDate == null) return 0;
        Date lastResultCompletionDate = Calendar.getInstance().getTime();
        long dateDiff = lastResultCompletionDate.getTime() - firstResultCompletionDate.getTime();
        return (int)TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    }


}
