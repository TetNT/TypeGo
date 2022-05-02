package com.tetsoft.typego.data.achievement;

import java.util.ArrayList;

public class RequirementFactory {

    public static ArrayList<AchievementRequirement> WPMisMoreThan(int moreOrEquals) {
        AchievementRequirement requirement = new AchievementRequirement(
                AchievementRequirement.AchievementSection.WPM,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                moreOrEquals);
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;

    }

    public static ArrayList<AchievementRequirement> timeModeNoMistakes(int timeInSeconds) {
        final int MINIMAL_WPM = 30;
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.TIME_MODE,
                AchievementRequirement.CompareType.EQUALS,
                timeInSeconds));
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.MISTAKES,
                AchievementRequirement.CompareType.EQUALS,
                0));
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.WPM,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                MINIMAL_WPM
        ));
        return requirements;
    }


    public static ArrayList<AchievementRequirement> passedTestsAmount(int amount) {
        AchievementRequirement requirement = new AchievementRequirement(
                AchievementRequirement.AchievementSection.PASSED_TESTS_AMOUNT,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                amount);
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;
    }

    public static ArrayList<AchievementRequirement> differentLanguagesAmount(int amount) {
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.DIFFERENT_LANGUAGES_COUNT,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                amount));
        return requirements;
    }
}
