package com.example.typego;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.TimeConvert;

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
    int dictionaryType;
    String dictionaryLanguageId;
    int timeInSeconds;
    int secondsRemaining;
    int scrollerCursorPosition;
    boolean testInitiallyPaused;
    static final int SCROLL_POWER = 25;
    boolean initErrorFlag;
    CountDownTimer cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_test);
        initialize();
        initWords();
        if (initErrorFlag) {
            finish();
            return;
        }
        inpWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // if user pressed space in empty text field
                if (s.toString().equals(" ")) {
                    inpWord.setText("");
                    return;
                }

                // if a test hasn't started yet and user began to type
                if (testInitiallyPaused && inpWord.getText().length()>0) {
                    startTimer(secondsRemaining);
                    testInitiallyPaused = false;
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

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        timeInSeconds = bundle.getInt(KeyConstants.TEST_AMOUNT_OF_SECONDS);
        dictionaryType = bundle.getInt(KeyConstants.TEST_DICTIONARY_TYPE);
        boolean textSuggestionsIsOn = bundle.getBoolean(KeyConstants.TEST_SUGGESTIONS_ON);
        dictionaryLanguageId = bundle.getString(KeyConstants.TEST_DICTIONARY_LANG);
        initErrorFlag = false;
        secondsRemaining = timeInSeconds;
        etWords = findViewById(R.id.words);
        inpWord = findViewById(R.id.inpWord);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
        wordList = new ArrayList<>();
        if (textSuggestionsIsOn) inpWord.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        else inpWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        initializeBackButtonCallback();
    }

    private void initializeBackButtonCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitTestDialog();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
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
        showContinueDialog();
    }

    private void showContinueDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.continue_testing)).setTitle(getString(R.string.welcome_back));
        dialog.setNegativeButton(getString(R.string.No), (dial, which) -> {
            pauseTimer();
            finish();
        });
        dialog.setPositiveButton(getString(R.string.Yes), (dial, which) -> {
            resumeTimer();
            dial.cancel();
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
                tvTimeLeft.setText(getString(R.string.time_over));
                Intent intent = new Intent(TypingTestActivity.this, ResultActivity.class);
                intent.putExtra(KeyConstants.TEST_CORRECT_WORDS, correctWordsCount);
                intent.putExtra(KeyConstants.TEST_AMOUNT_OF_SECONDS, timeInSeconds); // may cause an exception
                intent.putExtra(KeyConstants.TOTAL_WORDS, totalWordsPassed);
                intent.putExtra(KeyConstants.TEST_DICTIONARY_TYPE, dictionaryType);
                intent.putExtra(KeyConstants.TEST_DICTIONARY_LANG, dictionaryLanguageId);
                intent.putExtra(KeyConstants.TEST_SUGGESTIONS_ON, getIntent().getExtras().getBoolean(KeyConstants.TEST_SUGGESTIONS_ON));
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
        deselectSymbols(currentWordStartCursor, currentWordEndCursor);
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
        showExitTestDialog();
    }

    private void showExitTestDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.dialog_exit_test)).setTitle(R.string.Exit);
        dialog.setNegativeButton(R.string.No, (dial, which) -> {
            resumeTimer();
            dial.cancel();
        });
        dialog.setPositiveButton(R.string.Yes, (dial, which) -> {
            pauseTimer();
            finish();
            Intent intent = new Intent(TypingTestActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });
        dialog.show();
        pauseTimer();
    }

    public void restartTest(View view){
        pauseTimer();
        initWords();
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

    protected void initWords(){
        AssetManager am = getAssets();
        String dictionary;
        if (dictionaryType==0) {
             dictionary = "Basic";
        } else {
            dictionary = "Enhanced";
        }

        InputStream is;
        try {
            is = am.open("Words" + dictionary + dictionaryLanguageId +".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                wordList.add(currLine);
            }
            is.close();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.Words_loading_error_occurred), Toast.LENGTH_SHORT).show();
            initErrorFlag = true;
            return;
        }
        EditText words = findViewById(R.id.words);
        Random rnd = new Random();
        StringBuilder str = new StringBuilder();
        int amountOfWords = (int)(250 * (timeInSeconds / 60.0));
        for (int i = 0; i <= amountOfWords; i++) {
            str.append(wordList.get(rnd.nextInt(wordList.size()))).append(" ");
        }
        words.setText(str.toString());
    }
}