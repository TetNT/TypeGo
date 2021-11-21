package com.tetsoft.typego.testing;

import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;
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
                        DictionaryType dictionaryType,
                        ScreenOrientation screenOrientation,
                        Language language,
                        TimeMode timeMode,
                        boolean textSuggestions,
                        Date completionDateTime) {
        this.correctWords = correctWords;
        this.correctWordsWeight = correctWordsWeight;
        this.totalWords = totalWords;
        if (dictionaryType == DictionaryType.BASIC) this.dictionaryType = 0;
        else this.dictionaryType = 1;
        if (screenOrientation == ScreenOrientation.PORTRAIT) this.screenOrientation = 0;
        else this.screenOrientation = 1;
        this.language = language;
        timeInSeconds = timeMode.getTimeInSeconds();
        this.textSuggestions = textSuggestions;
        this.completionDateTime = completionDateTime;
        WPM = calculateWPM();
    }

    private double calculateWPM() {
        double WPM;
        double DIVISION_POWER = 4.0;
        try {
            WPM = (60.0 / timeInSeconds) * (correctWordsWeight / DIVISION_POWER);
        } catch (ArithmeticException ae) {
            WPM = 0.0;
        }
        return WPM;
    }
}
