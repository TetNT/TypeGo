package com.tetsoft.typego.data.achievement;

import java.util.ArrayList;

public class RequirementFactory {

    public static ArrayList<Requirement> WPMisMoreThan(int moreOrEquals) {
        Requirement requirement = new Requirement(
                Requirement.AchievementSection.WPM,
                Requirement.CompareType.MORE_OR_EQUALS,
                moreOrEquals);
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;

    }

    public static ArrayList<Requirement> timeModeNoMistakes(int timeInSeconds) {
        final int MINIMAL_WPM = 30;
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(new Requirement(
                Requirement.AchievementSection.TIME_MODE,
                Requirement.CompareType.EQUALS,
                timeInSeconds));
        requirements.add(new Requirement(
                Requirement.AchievementSection.MISTAKES,
                Requirement.CompareType.EQUALS,
                0));
        requirements.add(new Requirement(
                Requirement.AchievementSection.WPM,
                Requirement.CompareType.MORE_OR_EQUALS,
                MINIMAL_WPM
        ));
        return requirements;
    }


    public static ArrayList<Requirement> passedTestsAmount(int amount) {
        Requirement requirement = new Requirement(
                Requirement.AchievementSection.PASSED_TESTS_AMOUNT,
                Requirement.CompareType.MORE_OR_EQUALS,
                amount);
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;
    }

    public static ArrayList<Requirement> differentLanguagesAmount(int amount) {
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(new Requirement(
                Requirement.AchievementSection.DIFFERENT_LANGUAGES_COUNT,
                Requirement.CompareType.MORE_OR_EQUALS,
                amount));
        return requirements;
    }
}
