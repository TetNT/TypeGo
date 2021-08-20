package com.tetsoft.typego;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.TimeConvert;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class TypingTestActivity extends AppCompatActivity {
    EditText etWords;
    EditText inpWord;
    TextView tvTimeLeft;
    int correctWordsCount;
    int totalWordsPassed;
    ArrayList<String> loadingWordList;
    int currentWordStartCursor; // the beginning of the current word
    int currentWordEndCursor;
    String currentWord = "";
    int dictionaryType;
    int screenOrientation;
    Language dictionaryLanguage;
    int timeInSeconds;
    int secondsRemaining;
    int scrollerCursorPosition;
    int correctWordsWeight;
    boolean testInitiallyPaused;
    int SCROLL_POWER = 25;
    boolean initErrorFlag;
    CountDownTimer countdown;
    public InterstitialAd mInterstitialAd;
    boolean adShown;
    boolean fromMainMenu;
    ArrayList<Word> typedWordsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_test);
        adShown = false;
        loadAd();
        initialize();
        initializeBackButtonCallback();
        setScreenOrientation();
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
                // if a user pressed space in empty text field
                if (s.toString().equals(" ")) {
                    inpWord.setText("");
                    return;
                }

                // if a test hasn't started yet and a user began to type
                if (testInitiallyPaused && inpWord.getText().length()>0) {
                    startTimer(secondsRemaining);
                    testInitiallyPaused = false;
                }

                // if the last typed letter is space
                if (s.length()>0 && s.charAt(s.length()-1) == ' ') {
                    deselectCurrentWord();
                    addWordToTypedList(inpWord.getText().toString().trim(), currentWord.trim());
                    totalWordsPassed++;
                    if (wordIsCorrect()) {
                        correctWordsCount++;
                        correctWordsWeight += currentWord.length();
                        selectCurrentWordAsCorrect();
                    }
                    else selectCurrentWordAsIncorrect();
                    setNextWordCursors();
                    selectNextWord();
                    inpWord.setText("");

                } else {
                    checkSymbolsCorrectness();
                }
            }
        });
        resetAll();
        initializeWordCursor();
    }

    private void addWordToTypedList(String inputted, String original) {
        typedWordsList.add(new Word(inputted, original, getMistakeIndexes(inputted, original)));
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        timeInSeconds = bundle.getInt(StringKeys.TEST_AMOUNT_OF_SECONDS);
        dictionaryType = bundle.getInt(StringKeys.TEST_DICTIONARY_TYPE);
        screenOrientation = bundle.getInt(StringKeys.TEST_SCREEN_ORIENTATION);
        boolean textSuggestionsIsOn = bundle.getBoolean(StringKeys.TEST_SUGGESTIONS_ON);
        fromMainMenu = bundle.getBoolean(StringKeys.FROM_MAIN_MENU);
        dictionaryLanguage = (Language)bundle.getSerializable(StringKeys.TEST_DICTIONARY_LANG);
        initErrorFlag = false;
        secondsRemaining = timeInSeconds;
        etWords = findViewById(R.id.words);
        inpWord = findViewById(R.id.inpWord);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
        loadingWordList = new ArrayList<>();
        typedWordsList = new ArrayList<>();
        if (textSuggestionsIsOn) inpWord.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        else inpWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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
        showContinueDialog(adShown);
    }

    private void showContinueDialog(boolean adShown) {
        if (adShown) return;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.continue_testing)).setTitle(getString(R.string.welcome_back));
        dialog.setNegativeButton(getString(R.string.no), (dial, which) -> {
            pauseTimer();
            finish();
        });
        dialog.setPositiveButton(getString(R.string.yes), (dial, which) -> {
            resumeTimer();
            dial.cancel();
        });
        dialog.show();
        pauseTimer();
    }

    private void startTimer(int seconds) {
        countdown = new CountDownTimer(seconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimeLeft.setText(TimeConvert.convertSecondsToStamp((int)(millisUntilFinished/1000)));
                secondsRemaining--;
            }

            @Override
            public void onFinish() {
                inpWord.setEnabled(false);
                if (mInterstitialAd == null) {
                    showResultActivity();
                }
                else showAd();

            }
        }.start();
    }

    private void pauseTimer() {
        if (countdown == null) return;
        countdown.cancel();
    }

    private void resumeTimer() {
        if (testInitiallyPaused) return;
        startTimer(secondsRemaining);
    }

    private void checkSymbolsCorrectness() {
        deselectSymbols(currentWordStartCursor, currentWordEndCursor);
        for (int i = 0; i<currentWord.length();i++) {
            if (i<inpWord.length()) {
                char currInpChar = Character.toLowerCase(inpWord.getText().charAt(i));
                char currComparingChar = Character.toLowerCase(currentWord.charAt(i));
                if (currInpChar == currComparingChar) {
                    selectCurrentSymbolAsCorrect(i+currentWordStartCursor);
                } else {
                    selectCurrentSymbolAsIncorrect(i+currentWordStartCursor);
                }
            } else { // if a user hasn't finished typing then deselect the rest of a word
                deselectSymbols(i+currentWordStartCursor, currentWordEndCursor);
            }
        }
    }

    public void cancelTest(View view) {
        showExitTestDialog();
    }

    private void showExitTestDialog() {
        Intent intent;
        if (fromMainMenu) intent = new Intent(TypingTestActivity.this, MainActivity.class);
        else intent = new Intent(TypingTestActivity.this, TestSetupActivity.class);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.dialog_exit_test)).setTitle(R.string.exit);
        dialog.setNegativeButton(R.string.no, (dial, which) -> {
            resumeTimer();
            dial.cancel();
        });
        dialog.setPositiveButton(R.string.yes, (dial, which) -> {
            pauseTimer();
            finish();

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
        correctWordsWeight = 0;
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
        inpWord.setText("");
    }

    private void selectCurrentSymbolAsCorrect(int symbolIndex) {
        ForegroundColorSpan charSpan = new ForegroundColorSpan(Color.rgb(0,128,0));
        etWords.getText().setSpan(charSpan, symbolIndex, symbolIndex+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        String enteredWord = inpWord.getText().toString().trim();
        String comparingWord = currentWord.replace("\t","");
        return enteredWord.equalsIgnoreCase(comparingWord);
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
        BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(Color.rgb(0,100,100));
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.WHITE);
        etWords.getText().setSpan(backgroundSpan, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        etWords.getText().setSpan(foregroundSpan, currentWordStartCursor, currentWordEndCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        scrollerCursorPosition = currentWordEndCursor + SCROLL_POWER;
        etWords.setSelection(scrollerCursorPosition);
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

    protected ArrayList<Integer> getMistakeIndexes(String inputted, String original) {
        ArrayList<Integer> mistakeIndexes = new ArrayList<>();
        for (int i = 0; i<inputted.length(); i++) {
            if (i >= original.length()) mistakeIndexes.add(i);
            else if (inputted.toLowerCase().charAt(i) != original.toLowerCase().charAt(i)) mistakeIndexes.add(i);
        }
        return mistakeIndexes;
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
            is = am.open("Words" + dictionary + dictionaryLanguage.getIdentifier() +".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                loadingWordList.add(currLine);
            }
            is.close();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.words_loading_error_occurred), Toast.LENGTH_SHORT).show();
            initErrorFlag = true;
            return;
        }
        EditText words = findViewById(R.id.words);
        Random rnd = new Random();
        StringBuilder str = new StringBuilder();
        int amountOfWords = (int)(250 * (timeInSeconds / 60.0));
        for (int i = 0; i <= amountOfWords; i++) {
            str.append(loadingWordList.get(rnd.nextInt(loadingWordList.size()))).append(" ");
        }
        words.setText(str.toString());
    }

    private void setScreenOrientation() {
        if (screenOrientation==0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            SCROLL_POWER = 25;
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            SCROLL_POWER = 0;
        }
    }

    private void loadAd() {
        Log.i("LoadAD", "Start loading");
        String testAdID = "ca-app-pub-7144745225390143/6975421286";
        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                testAdID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        setAdCallback();
                        Log.i("LoadAD", "Ad loaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i("LoadAD", "Failed to load ad:" + loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    private void setAdCallback() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.i("AD", "Failed to show ad:" + adError.getMessage());
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                adShown = true;
                showResultActivity();
            }
        });
    }

    private void showAd() {
        if (mInterstitialAd == null) {
            Log.i("AD", "Ad did not load");
            return;
        }
        adShown = true;
        mInterstitialAd.show(this);
    }

    private void showResultActivity() {
        tvTimeLeft.setText(getString(R.string.time_over));
        Intent intent = new Intent(TypingTestActivity.this, ResultActivity.class);
        intent.putExtra(StringKeys.TEST_CORRECT_WORDS, correctWordsCount);
        intent.putExtra(StringKeys.TEST_CORRECT_WORDS_WEIGHT, correctWordsWeight);
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, timeInSeconds);
        intent.putExtra(StringKeys.TOTAL_WORDS, totalWordsPassed);
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, dictionaryType);
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, dictionaryLanguage);
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON,
                getIntent().getExtras().getBoolean(StringKeys.TEST_SUGGESTIONS_ON));
        intent.putExtra(StringKeys.TEST_TYPED_WORDS_LIST, typedWordsList);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, getIntent().getBooleanExtra(StringKeys.FROM_MAIN_MENU, false));
        finish();
        startActivity(intent);
    }
}