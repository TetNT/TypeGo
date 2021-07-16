package com.example.typego.utils;


import java.util.Date;
public class TypingResult {

    private final double WPM;
    private final int dictionaryType;
    private final int screenOrientation;
    private final Language language;
    private final int timeInSeconds;
    private final Date completionDateTime;
    private final int correctWords;
    private final int correctWordsWeight;
    private final int totalWords;
    private final boolean textSuggestions;

    public double getWPM() {
        return WPM;
    }

    public int getDictionaryType() {
        return dictionaryType;
    }

    public int getScreenOrientation() {
        return screenOrientation;
    }

    public Language getLanguage() {
        return language;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public Date getCompletionDateTime() {
        return completionDateTime;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public int getCorrectWordsWeight() {
        return correctWordsWeight;
    }

    public int getIncorrectWords() {
        return totalWords - correctWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public boolean isTextSuggestions() {
        return textSuggestions;
    }

    public TypingResult(int correctWords,
                        int correctWordsWeight,
                        int totalWords,
                        int dictionaryType,
                        int screenOrientation,
                        Language language,
                        int timeInSeconds,
                        boolean textSuggestions,
                        Date completionDateTime) {
        this.correctWords = correctWords;
        this.correctWordsWeight = correctWordsWeight;
        this.totalWords = totalWords;
        this.dictionaryType = dictionaryType;
        this.screenOrientation = screenOrientation;
        this.textSuggestions = textSuggestions;
        this.language = language;
        this.timeInSeconds = timeInSeconds;
        this.completionDateTime = completionDateTime;
        double DIVISION_POWER = 4.0;
        WPM = (60.0 / this.timeInSeconds) * (this.correctWordsWeight/ DIVISION_POWER);
    }
}
