package com.tetsoft.typego.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public final class ResultListUtils {

    private ResultListUtils() {

    }

    public static int getBestResult(@NonNull ArrayList<TypingResult> resultList) {
        int currentBest = 0;
        for (TypingResult result: resultList
        ) {
            if (result.getWPM()>currentBest) currentBest = (int)result.getWPM();
        }
        return currentBest;
    }

    public static ArrayList<TypingResult> getResultsByLanguage(@NonNull ArrayList<TypingResult> results, Language language) {
        ArrayList<TypingResult> selectedResults = new ArrayList<>();
        for (TypingResult res: results
        ) {
            if (res.getLanguage().getIdentifier().equalsIgnoreCase(language.getIdentifier()))
                selectedResults.add(res);
        }
        return selectedResults;
    }
}
