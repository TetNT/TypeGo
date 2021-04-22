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

import com.example.typego.user.TimeConvert;

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
    int totalWordsPassed;
    ArrayList<String> wordList;
    int currentWordStartCursor;
    int currentWordEndCursor;
    String currentWord = "";
    int DictionaryType;
    int timeInSeconds;
    int secondsRemaining;
    int scrollerCursorPosition;
    boolean testInitiallyPaused;
    static final int SCROLL_POWER = 25;
    CountDownTimer cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_test);
        Bundle bundle = getIntent().getExtras();
        timeInSeconds = Integer.parseInt(bundle.get("AmountOfSeconds").toString());
        secondsRemaining = timeInSeconds;
        DictionaryType  = Integer.parseInt(bundle.get("DictionaryType").toString());
        etWords = findViewById(R.id.words);
        inpWord = findViewById(R.id.inpWord);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
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
                // if a test hasn't started yet and user began to type
                if (testInitiallyPaused && inpWord.getText().length()>0) {
                    startTimer(secondsRemaining);
                    testInitiallyPaused = false;
                }
                // if user pressed space in empty field
                if (s.toString().equals(" ")) {
                    inpWord.setText("");
                    return;
                }
                // if last typed letter is space
                if (s.length()>0 && s.charAt(s.length()-1) == ' ') {
                    deselectCurrentWord();
                    totalWordsPassed++;
                    if (wordIsCorrect()) {
                        correctWordsCount++;
                        selectCurrentWordAsCorrect();
                    }
                    else selectCurrentWordAsIncorrect();
                    setNextWordCursors();
                    selectNextWord();

                } else {
                    checkSymbolsCorrectness();
                }
            }
        });
        resetAll();
        initializeWordCursor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (testInitiallyPaused) return;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Продолжить тестирование?").setTitle("Прерывание");
        dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pauseTimer();
                finish();
            }
        });
        dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeTimer();
                dialog.cancel();
            }
        });
        dialog.show();
        pauseTimer();
    }

    private void startTimer(int seconds) {
        cd = new CountDownTimer(seconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimeLeft.setText(TimeConvert.convertSecondsToStamp((int)(millisUntilFinished/1000)));
                secondsRemaining--;
            }

            @Override
            public void onFinish() {
                tvTimeLeft.setText("00:00");
                Intent intent = new Intent(TypingTestActivity.this, ResultActivity.class);
                intent.putExtra("correctWords", correctWordsCount);
                intent.putExtra("timeInSeconds", timeInSeconds);
                intent.putExtra("totalWords", totalWordsPassed);
                intent.putExtra("DictionaryType", DictionaryType);
                intent.putExtra("currentUser", getIntent().getSerializableExtra("currentUser"));
                finish();
                startActivity(intent);
            }
        }.start();
    }

    private void pauseTimer() {
        if (cd == null) return;
        cd.cancel();
    }

    private void resumeTimer() {
        if (testInitiallyPaused) return;
        startTimer(secondsRemaining);
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
        dialog.setMessage("Прервать тестирование?").setTitle("Прерывание");
        dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeTimer();
                dialog.cancel();
            }
        });
        dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pauseTimer();
                finish();
            }
        });
        dialog.show();
        pauseTimer();
    }

    public void restartTest(View view){
        pauseTimer();
        initWords(DictionaryType);
        resetAll();
    }

    protected void resetAll() {
        correctWordsCount = 0;
        currentWordStartCursor = 0;
        currentWordEndCursor = 0;
        scrollerCursorPosition = 0;
        totalWordsPassed = 0;
        secondsRemaining = timeInSeconds;
        tvTimeLeft.setText(TimeConvert.convertSecondsToStamp(secondsRemaining));
        testInitiallyPaused = true;
        initializeWordCursor();
        selectNextWord();
        inpWord.requestFocus();
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
        return enteredWord.equals(currentWord + " ");
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

    protected void initializeWordCursor() {
        while (currentWordEndCursor<etWords.length() && etWords.getText().charAt(currentWordEndCursor)!=' ') {
            currentWordEndCursor++;
        }
        setCurrentWord();
    }

    protected void setCurrentWord() {
        currentWord = etWords.getText().toString().substring(currentWordStartCursor, currentWordEndCursor);
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
            Toast.makeText(this, getString(R.string.Words_loading_error_occurred), Toast.LENGTH_SHORT).show();
        }
        EditText words = findViewById(R.id.words);
        Random rnd = new Random();
        for (int i = 0; i<=150; i++) {
            str += wordList.get(rnd.nextInt(wordList.size())) + " ";
        }
        words.setText(str);
    }
}