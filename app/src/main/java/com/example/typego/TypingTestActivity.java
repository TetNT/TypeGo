package com.example.typego;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class TypingTestActivity extends AppCompatActivity {
    EditText etWords;
    EditText inpWord;
    TextView tvTimeLeft;
    int correctWordsCount;
    int totalWordsCount;
    ArrayList<String> wordList;
    int currentWordStartCursor;
    int currentWordEndCursor;
    String currentWord = "";
    int DictionaryType;
    int timeInSeconds;
    int scrollerCursorPosition;
    boolean testPaused;
    static final int SCROLL_POWER = 25;
    CountDownTimer cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_test);
        testPaused = true;
        Bundle bundle = getIntent().getExtras();
        timeInSeconds = Integer.parseInt(bundle.get("AmountOfSeconds").toString());
        DictionaryType  = Integer.parseInt(bundle.get("DictionaryType").toString());
        etWords = findViewById(R.id.words);
        inpWord = findViewById(R.id.inpWord);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
        correctWordsCount = 0;
        totalWordsCount = 0;
        wordList = new ArrayList<String>();
        initWords(DictionaryType);

        inpWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: Refresh or stop a game if a user reached the last word
                if (s.toString().equals(" ")) {
                    inpWord.setText("");
                    return;
                }
                if (s.length()>0 && s.charAt(s.length()-1) == ' ') {
                    deselectCurrentWord();
                    if (wordIsCorrect()) {
                        correctWordsCount++;
                        selectCurrentWordAsCorrect();
                    }
                    else {
                        selectCurrentWordAsIncorrect();
                    }
                    totalWordsCount++;
                    setNextWordCursors();
                    selectNextWord();

                } else {
                    checkSymbolsCorrectness();
                }
            }
        });
        cd = new CountDownTimer(timeInSeconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimeLeft.setText(TimeConvert.convertSecondsToStamp((int)(millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                tvTimeLeft.setText("00:00");

                Intent intent = new Intent(TypingTestActivity.this, ResultActivity.class);
                intent.putExtra("correctWords", correctWordsCount);
                intent.putExtra("timeInSeconds", timeInSeconds);
                intent.putExtra("totalWords",totalWordsCount);
                intent.putExtra("DictionaryType", DictionaryType);
                finish();
                startActivity(intent);

            }
        };
        resetAll();
        InitializeWordCursor();
    }

    private void checkSymbolsCorrectness() {
        for (int i = 0; i<currentWord.length();i++) {
            if (i<inpWord.length()) {
                char currInpChar = inpWord.getText().charAt(i);
                char currComparingChar = currentWord.charAt(i);
                if (currInpChar == currComparingChar) {
                    selectCurrentSymbolAsCorrect(i+currentWordStartCursor);
                } else {
                    selectCurrentSymbolAsIncorrect(i+currentWordStartCursor);
                }
            } else {
                deselectSymbols(i+currentWordStartCursor, currentWordEndCursor);
            }
        }
    }

    public void cancelTest(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Вы уверены, что хотите прервать тестирование?").setTitle("Прервать тест");
        dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cd.cancel();
                finish();
            }
        });
        dialog.show();

    }

    private void selectCurrentSymbolAsCorrect(int symbolIndex) {
        ForegroundColorSpan selectedWordFG = new ForegroundColorSpan(Color.rgb(0,128,0));
        etWords.getText().setSpan(selectedWordFG, symbolIndex, symbolIndex+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void selectCurrentSymbolAsIncorrect(int symbolIndex) {
        ForegroundColorSpan selectedWordFG = new ForegroundColorSpan(Color.rgb(192,0,0));
        etWords.getText().setSpan(selectedWordFG, symbolIndex, symbolIndex+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void deselectSymbols(int startIndex, int endIndex) {
        final Object[] removableSpans = etWords.getText().getSpans(startIndex, endIndex, Object.class);
        for (final Object span : removableSpans) {
            if (span instanceof ForegroundColorSpan) {
                etWords.getText().removeSpan(span);
            }
        }
    }

    protected boolean wordIsCorrect() {
        String enteredWord = inpWord.getText().toString();
        if (enteredWord.equals(currentWord+" ")){
            return true;
        }
        return false;
    }

    protected void deselectCurrentWord() {
        final Object[] removableSpans = etWords.getText().getSpans(currentWordStartCursor, currentWordEndCursor, Object.class);
        for (final Object span : removableSpans) {
            if (span instanceof BackgroundColorSpan || span instanceof StyleSpan || span instanceof ForegroundColorSpan) {
                etWords.getText().removeSpan(span);
            }
        }
    }

    protected void selectCurrentWordAsIncorrect() {
        ForegroundColorSpan selectedWordFG = new ForegroundColorSpan(Color.rgb(192,0,0));
        etWords.getText().setSpan(selectedWordFG, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    protected void selectCurrentWordAsCorrect() {
        ForegroundColorSpan selectedWordFG = new ForegroundColorSpan(Color.rgb(0,128,0));
        etWords.getText().setSpan(selectedWordFG, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    protected void selectNextWord() {
        BackgroundColorSpan selectedWordBG = new BackgroundColorSpan(Color.rgb(0,100,100));
        ForegroundColorSpan selectedWordFG = new ForegroundColorSpan(Color.WHITE);
        etWords.getText().setSpan(selectedWordBG, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        etWords.getText().setSpan(selectedWordFG, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        scrollerCursorPosition = currentWordEndCursor + SCROLL_POWER;
        etWords.setSelection(scrollerCursorPosition);
        inpWord.setText("");
    }


    protected void setNextWordCursors() {
        currentWordStartCursor = currentWordEndCursor + 1;
        currentWordEndCursor = currentWordStartCursor;
        while (currentWordEndCursor<etWords.length() && etWords.getText().charAt(currentWordEndCursor)!=' ') {
            currentWordEndCursor++;
        }
        setCurrentWord();
    }

    protected void InitializeWordCursor() {
        while (currentWordEndCursor<etWords.length() && etWords.getText().charAt(currentWordEndCursor)!=' ') {
            currentWordEndCursor++;
        }
        setCurrentWord();
    }

    protected void setCurrentWord() {
        currentWord = etWords.getText().toString().substring(currentWordStartCursor, currentWordEndCursor);
    }


    public void RestartTest(View view){
        cd.cancel();
        initWords(DictionaryType);
        resetAll();
    }

    protected void resetAll() {
        correctWordsCount = 0;
        currentWordStartCursor = 0;
        currentWordEndCursor = 0;
        scrollerCursorPosition = 0;
        cd.start();
        InitializeWordCursor();
        selectNextWord();
        inpWord.requestFocus();
    }

    protected void initWords(int DictionaryType){
        AssetManager am = getAssets();
        String Dictionary;
        if (DictionaryType==0) {
             Dictionary = "Basic";
        } else {
            Dictionary = "Enhanced";
        }
        String str = "";
        InputStream is;
        try {
            is = am.open("Words" + Dictionary + "RU.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                wordList.add(currLine);
            }
            is.close();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.Words_loading_error_occured), Toast.LENGTH_SHORT).show();
        }
        EditText words = (EditText)findViewById(R.id.words);
        Random rnd = new Random();
        for (int i = 0; i<=150; i++) {
            str += wordList.get(rnd.nextInt(wordList.size())) + " ";
        }
        words.setText(str);
    }
}