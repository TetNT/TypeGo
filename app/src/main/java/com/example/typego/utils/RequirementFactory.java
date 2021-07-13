package com.example.typego.utils;

import java.util.ArrayList;

public class RequirementFactory {

    public static ArrayList<AchievementRequirement> getRequirementWPMisMoreThan(int moreOrEquals) {
        AchievementRequirement requirement = new AchievementRequirement(
                AchievementRequirement.AchievementSection.WPM,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                moreOrEquals);
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;

    }

    public static ArrayList<AchievementRequirement> getRequirementTimeModeNoMistakes(int timeInSeconds) {
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.TIME_MODE,
                AchievementRequirement.CompareType.EQUALS,
                timeInSeconds));
        requirements.add(new AchievementRequirement(
                AchievementRequirement.AchievementSection.MISTAKES,
                AchievementRequirement.CompareType.EQUALS,
                0));
        return requirements;
    }


    public static ArrayList<AchievementRequirement> getRequirementPassedTestsAmount(int amount) {
        AchievementRequirement requirement = new AchievementRequirement(
                AchievementRequirement.AchievementSection.PASSED_TESTS_AMOUNT,
                AchievementRequirement.CompareType.MORE_OR_EQUALS,
                amount);
        ArrayList<AchievementRequirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        return requirements;
    }
}
