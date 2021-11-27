package com.tetsoft.typego.testing;

import com.tetsoft.typego.utils.Language;

import java.util.ArrayList;
import java.util.Collections;

public class ResultList extends ArrayList<TypingResult> {

    public ResultList getResultsByLanguage(Language language) {
        ResultList selectedResults = new ResultList();
        for (TypingResult result: this) {
            String rowLanguageId = result.getLanguage().getIdentifier();
            if (rowLanguageId.equalsIgnoreCase(language.getIdentifier()))
                selectedResults.add(result);
        }
        return selectedResults;
    }

    public int getBestResult() {
        int currentBest = 0;
        for (TypingResult result: this) {
            if (result.getWPM()>currentBest) currentBest = (int)result.getWPM();
        }
        return currentBest;
    }

    public ResultList getInDescendingOrder() {
        ResultList copy = (ResultList)this.clone();
        Collections.reverse(copy);
        return copy;
    }
}
