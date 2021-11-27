package com.tetsoft.typego.testing;

import androidx.annotation.NonNull;
import com.tetsoft.typego.utils.Language;
import java.util.ArrayList;

public final class ResultListUtils {

    public static TypingResult getBestResult(@NonNull ArrayList<TypingResult> resultList) {
        if (resultList.isEmpty()) return null;
        TypingResult currentBest = resultList.get(0);
        for (TypingResult result: resultList) {
            if (result.getWPM()>currentBest.getWPM()) currentBest = result;
        }
        return currentBest;
    }

    public static ArrayList<TypingResult> getResultsByLanguage(@NonNull ArrayList<TypingResult> results, @NonNull Language language) {
        ArrayList<TypingResult> selectedResults = new ArrayList<>();
        for (TypingResult res: results) {
            if (res.getLanguage().getIdentifier().equalsIgnoreCase(language.getIdentifier()))
                selectedResults.add(res);
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
        // TODO: when changed to ResultList class, replace parameter below "0" with "results.size()" method.
        return resultsByLanguage.get(0);
    }
}
