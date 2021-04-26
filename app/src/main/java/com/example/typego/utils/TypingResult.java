package com.example.typego.utils;


import java.util.Date;
public class TypingResult {

    private double WPM;
    private int dictionaryType;
    private String language;
    private int timeInSeconds;
    private Date completionDateTime;

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

    public TypingResult(double WPM, int dictionaryType, String language, int timeInSeconds, Date completionDateTime) {
        this.WPM = WPM;
        this.dictionaryType = dictionaryType;
        this.language = language;
        this.timeInSeconds = timeInSeconds;
        this.completionDateTime = completionDateTime;
    }
}
