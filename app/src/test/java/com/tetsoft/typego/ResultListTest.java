package com.tetsoft.typego;

import com.tetsoft.typego.testing.ResultList;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.Calendar;
import java.util.Random;

public class ResultListTest {
    @Test
    public void reverseIsCorrect() {
        int capacity = 100;
        ResultList original = generateResultList(capacity);
        ResultList copy = original.getInDescendingOrder();
        for (int i = 0; i < capacity; i++) {
            assertEquals(
                    original.get(i).getCorrectWords(),
                    copy.get(copy.size()-1-i).getCorrectWords());
        }

    }

    @Test
    public void bestResultIsCorrect() {
        ResultList results = generateResultList(20);
        results.getBestResult();
    }

    private ResultList generateResultList(int capacity) {
        ResultList results = new ResultList();
        Random rnd = new Random();

        for (int i = 0; i < capacity; i++) {
            int correctWords = rnd.nextInt(80);
            TypingResult result = new TypingResult(
                    correctWords,
                    correctWords*4,
                    correctWords+5,
                    DictionaryType.BASIC,
                    ScreenOrientation.PORTRAIT,
                    new Language("Test", "Test language"),
                    new TimeMode(30),
                    true,
                    Calendar.getInstance().getTime());
            results.add(result);
        }
        return results;
    }

}
