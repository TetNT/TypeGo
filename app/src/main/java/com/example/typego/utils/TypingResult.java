package com.example.typego.utils;


import java.util.Date;
public class TypingResult {

    private final double WPM;
    private final int dictionaryType;
    private final String language;
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

    public String getLanguage() {
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
                        String language,
                        int timeInSeconds,
                        boolean textSuggestions,
                        Date completionDateTime) {
        this.correctWords = correctWords;
        this.correctWordsWeight = correctWordsWeight;
        this.totalWords = totalWords;
        this.dictionaryType = dictionaryType;
        this.textSuggestions = textSuggestions;
        this.language = language;
        this.timeInSeconds = timeInSeconds;
        this.completionDateTime = completionDateTime;
        WPM = (60.0 / this.timeInSeconds) * this.correctWords;
    }
}
