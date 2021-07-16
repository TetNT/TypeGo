package com.example.typego.utils;


import java.util.ArrayList;

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
    }

    private int getComparingResultBySection(User user, TypingResult result) {
        switch (achievementSection){
            case WPM:
                return (int)result.getWPM();
            case MISTAKES:
                return result.getTotalWords()-result.getCorrectWords();
            case MISTAKES_IN_A_ROW:
                return getMistakesAmountInARow(user);
            case TIME_MODE:
                return result.getTimeInSeconds();
            case PASSED_TESTS_AMOUNT:
                return user.getResultList().size();
            default:
                throw new IllegalArgumentException("Wrong achievement section type: "+ achievementSection);
        }
    }

    private int getMistakesAmountInARow(User user) {
        int inARow = 5;
        if (user == null) return -1;
        ArrayList<TypingResult> results = user.getResultList();
        int mistakesAmount = 0;
        if (results.size() < inARow) return -1;
        for (int i = 0; i < inARow; i++) {
            mistakesAmount += results.get(i).getIncorrectWords();
        }
        return mistakesAmount;
    }

    public int getCurrentProgress(User user) {
        switch (achievementSection) {
            case WPM:
                return user.getBestResult();
            case PASSED_TESTS_AMOUNT:
                return user.getResultList().size();
            default:
                return 0;
        }
    }

    public boolean isMatching(User user, TypingResult result) {
        boolean matching = false;
        int comparingResult = getComparingResultBySection(user, result);
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

