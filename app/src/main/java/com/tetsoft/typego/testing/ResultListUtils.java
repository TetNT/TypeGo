package com.tetsoft.typego.testing;

import androidx.annotation.NonNull;
import com.tetsoft.typego.data.Language;
import com.tetsoft.typego.data.TimeMode;

import java.util.ArrayList;

public final class ResultListUtils {

    public static TypingResult getBestResult(@NonNull ArrayList<TypingResult> resultList) {
        if (resultList.isEmpty()) return null;
        TypingResult currentBest = resultList.get(0);
        for (TypingResult result: resultList) {
            if (result.getWpm()>currentBest.getWpm()) currentBest = result;
        }
        return currentBest;
    }

    public static ArrayList<TypingResult> getResultsByLanguage(ArrayList<TypingResult> results, Language language) {
        ArrayList<TypingResult> selectedResults = new ArrayList<>();
        for (TypingResult res: results) {
            if (res.getLanguage().getIdentifier().equalsIgnoreCase(language.getIdentifier()))
                selectedResults.add(res);
        }
        return selectedResults;
    }

    public static ArrayList<TypingResult> getResultsByTimeMode(ArrayList<TypingResult> results, TimeMode timeMode) {
        ArrayList<TypingResult> selectedResults = new ArrayList<>();
        int comparingSeconds = timeMode.getTimeInSeconds();
        for (TypingResult res: results) {
            if (res.getTimeInSeconds() == comparingSeconds) selectedResults.add(res);
        }
        return selectedResults;
    }

    public static TypingResult getBestResultByLanguage(@NonNull ArrayList<TypingResult> results, Language language) {
        ArrayList<TypingResult> resultsByLanguage = getResultsByLanguage(results, language);
        return getBestResult(resultsByLanguage);
    }

    public static TypingResult getLastResultByLanguage(@NonNull ArrayList<TypingResult> results, Language language) {
        ArrayList<TypingResult> resultsByLanguage = getResultsByLanguage(results, language);
        if (resultsByLanguage.size() == 0) return null;
        // TODO: when changed to ResultList class, replace parameter "0" with "results.size()" method.
        return resultsByLanguage.get(0);
    }
}
