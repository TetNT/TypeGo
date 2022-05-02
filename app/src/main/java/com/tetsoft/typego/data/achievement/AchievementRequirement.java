package com.tetsoft.typego.data.achievement;


import com.tetsoft.typego.data.Language;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.game.mode.GameOnTime;
import com.tetsoft.typego.game.result.GameResult;
import com.tetsoft.typego.game.result.GameResultList;

public class AchievementRequirement {

    private final CompareType compareType;
    private final AchievementSection achievementSection;
    private final int requiredAmount;

    public AchievementRequirement(AchievementSection achievementSection, CompareType compareType, int requiredAmount) {
        this.achievementSection = achievementSection;
        this.compareType = compareType;
        this.requiredAmount = requiredAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public enum CompareType {
        MORE_OR_EQUALS,
        LESS_OR_EQUALS,
        EQUALS
    }

    public enum AchievementSection {
        WPM,
        PASSED_TESTS_AMOUNT,
        MISTAKES,
        TIME_MODE,
        MISTAKES_IN_A_ROW,
        DIFFERENT_LANGUAGES_COUNT,
    }

    private int getComparingResultBySection(GameResultList resultList) {
        if (resultList.isEmpty()) throw new IllegalArgumentException("The result list should not be empty!");
        GameResult result = resultList.get(resultList.size()-1);
        switch (achievementSection){
            case WPM:
                return (int)resultList.getLastWpm();
            case MISTAKES:
                return result.getWordsWritten() - result.getCorrectWords();
            case MISTAKES_IN_A_ROW:
                return getMistakesAmountInARow(resultList);
            case TIME_MODE:
                if (result.getGameMode() instanceof GameOnTime)
                return ((GameOnTime)result.getGameMode()).getTimeMode().getTimeInSeconds();
                return 0;
            case PASSED_TESTS_AMOUNT:
                return resultList.size();
            case DIFFERENT_LANGUAGES_COUNT:
                return countUniqueLanguageEntries(resultList);
            default:
                throw new IllegalArgumentException("Wrong achievement section type: "+ achievementSection);
        }
    }

    private int getMistakesAmountInARow(GameResultList resultList) {
        final int IN_A_ROW = 5;

        int mistakesAmount = 0;
        if (resultList.size() < IN_A_ROW) return -1;
        for (int i = 0; i < IN_A_ROW; i++) {
            GameResult result = resultList.get(i);
            mistakesAmount += result.getWordsWritten() - result.getCorrectWords();
        }
        return mistakesAmount;
    }

    public int getCurrentProgress(GameResultList resultList) {
        switch (achievementSection) {
            case WPM:
                return resultList.getBestResultWpm();
            case PASSED_TESTS_AMOUNT:
                return resultList.size();
            default:
                return 0;
        }
    }

    // returns the amount of different languages in a results list
    public int countUniqueLanguageEntries(GameResultList resultList) {
        int entries = 0;
        for (Language language : new LanguageList().getList()) {
            if (!resultList.getResultsByLanguage(language).isEmpty())
                entries++;
        }
        return entries;
    }

    public boolean isMatching(GameResultList resultList) {
        boolean matching = false;
        int comparingResult = getComparingResultBySection(resultList);
        switch (compareType) {
            case EQUALS:
                matching = (comparingResult == requiredAmount);
                break;
            case LESS_OR_EQUALS:
                matching = (comparingResult <= requiredAmount);
                break;
            case MORE_OR_EQUALS:
                matching = (comparingResult >= requiredAmount);
                break;
        }
        return matching;
    }
}

