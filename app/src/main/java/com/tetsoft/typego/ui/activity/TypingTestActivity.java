package com.tetsoft.typego.ui.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tetsoft.typego.data.AdsCounter;
import com.tetsoft.typego.Config;
import com.tetsoft.typego.game.mode.GameMode;
import com.tetsoft.typego.game.mode.GameOnTime;
import com.tetsoft.typego.game.result.GameResultList;
import com.tetsoft.typego.ui.custom.SpannableEditText;
import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.data.Word;
import com.tetsoft.typego.R;
import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
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
    SpannableEditText etWords;
    EditText inpWord;
    TextView tvTimeLeft;
    int correctWordsCount;
    int totalWordsPassed;
    int currentWordStartCursor; // index of the beginning of the current word
    int currentWordEndCursor;
    String currentWord = "";
    int timeTotalAmount;
    int secondsRemaining;
    int scrollerCursorPosition; // scroller cursor is used to automatically scroll down the text
    int correctWordsWeight;
    boolean testInitiallyPaused; // a flag that decides if the timer should start or not.
    int SCROLL_POWER = 25;
    CountDownTimer countdown;
    public InterstitialAd mInterstitialAd;
    boolean adShown;
    boolean ignoreCase = true;
    ArrayList<Word> typedWordsList;
    GameOnTime gameMode;
    AdsCounter adsCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_test);
        adsCounter = ((TypeGoApp)getApplication()).getAdsCounter();
        adShown = false;
        loadAd();
        initialize();
        initializeBackButtonCallback();
        setScreenOrientation();
        initWords();

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

                // if a test hasn't started yet and the user began to type
                if (testInitiallyPaused && inpWord.getText().length()>0) {
                    startTimer(secondsRemaining);
                    testInitiallyPaused = false;
                }

                if (s.length() < 1) {
                    deselectLetters(currentWordStartCursor, currentWordEndCursor + 1);
                    return;
                }

                if (s.length() > 0 && !lastCharIsSpace(s)) {
                    checkCorrectnessByLetter();
                    return;
                }

                // if user's input is not empty and it has space at the end
                deselectCurrentWord();
                addWordToTypedList(inpWord.getText().toString().trim(), currentWord.trim());
                totalWordsPassed++;
                if (wordIsCorrect(ignoreCase)) {
                    correctWordsCount++;
                    correctWordsWeight += currentWord.length();
                    selectCurrentWordAsCorrect();
                } else selectCurrentWordAsIncorrect();
                setNextWordCursors();
                selectNextWord();
                inpWord.setText("");
            }
        });
        resetAll();
        initializeWordCursor();
    }

    private boolean lastCharIsSpace(Editable s) {
        return s.charAt(s.length() - 1) == ' ';
    }

    private void addWordToTypedList(String inputted, String original) {
        typedWordsList.add(new Word(inputted, original, getMistakeIndexes(inputted, original)));
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        gameMode = (GameOnTime)bundle.getSerializable(StringKeys.TEST_SETTINGS);
        timeTotalAmount = gameMode.getTimeMode().getTimeInSeconds();
        secondsRemaining = timeTotalAmount;
        etWords = findViewById(R.id.words);
        inpWord = findViewById(R.id.inpWord);
        tvTimeLeft = findViewById(R.id.tvTimeLeft);
        typedWordsList = new ArrayList<>();
        if (gameMode.getSuggestionsActivated())
            inpWord.setInputType(InputType.TYPE_CLASS_TEXT);
        else inpWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    private void initializeBackButtonCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
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
        countdown = new CountDownTimer(seconds* 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimeLeft.setText(TimeConvert.convertSecondsToStamp((int)(millisUntilFinished/1000)));
                secondsRemaining--;
            }

            @Override
            public void onFinish() {
                inpWord.setEnabled(false);
                adsCounter.addValue(timeTotalAmount/60f);
                if (mInterstitialAd == null || !adsCounter.enoughToShowAd()) {
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

    private void checkCorrectnessByLetter() {
        deselectLetters(currentWordStartCursor, currentWordEndCursor);
        for (int i = 0; i < currentWord.length(); i++) {
            // if a user hasn't finished typing then deselect the rest of a word
            if (i >= inpWord.length()) {
                deselectLetters(i + currentWordStartCursor, currentWordEndCursor);
                return;
            }
            if (charactersMatch(inpWord.getText().charAt(i), currentWord.charAt(i), ignoreCase)) {
                selectCurrentLetterAsCorrect(i + currentWordStartCursor);
            } else {
                selectCurrentLetterAsIncorrect(i + currentWordStartCursor);
            }
        }
    }

    public boolean charactersMatch(char char1, char char2, boolean ignoreCase) {
        if (ignoreCase) {
            char1 = Character.toLowerCase(char1);
            char2 = Character.toLowerCase(char2);
        }
        return (char1 == char2);
    }

    public void cancelTest(View view) {
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.dialog_exit_test)).setTitle(R.string.exit);
        dialog.setNegativeButton(R.string.no, (dial, which) -> {
            resumeTimer();
            dial.cancel();
        });
        dialog.setPositiveButton(R.string.yes, (dial, which) -> {
            pauseTimer();
            finish();
            Intent intent;
            boolean fromMainMenu = getIntent().getExtras().getBoolean(StringKeys.FROM_MAIN_MENU);
            if (fromMainMenu) intent = new Intent(TypingTestActivity.this, MainActivity.class);
            else intent = new Intent(TypingTestActivity.this, TestSetupActivity.class);
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

    private void resetAll() {
        correctWordsCount = 0;
        correctWordsWeight = 0;
        currentWordStartCursor = 0;
        currentWordEndCursor = 0;
        scrollerCursorPosition = 0;
        totalWordsPassed = 0;
        secondsRemaining = timeTotalAmount;
        tvTimeLeft.setText(TimeConvert.convertSecondsToStamp(secondsRemaining));
        testInitiallyPaused = true;
        initializeWordCursor();
        selectNextWord();
        inpWord.requestFocus();
        inpWord.setText("");
    }

    private void selectCurrentLetterAsCorrect(int symbolIndex) {
        etWords.paintForeground(symbolIndex, SpannableEditText.getGreenForeground());
    }

    private void selectCurrentLetterAsIncorrect(int symbolIndex) {
        etWords.paintForeground(symbolIndex, SpannableEditText.getRedForeground());
    }

    private void deselectLetters(int startIndex, int endIndex) {
        etWords.clearForeground(startIndex, endIndex);
    }

    private boolean wordIsCorrect(boolean ignoreCase) {
        String enteredWord = inpWord.getText().toString().trim();  // remove a space at the end
        String comparingWord = currentWord;
        if (ignoreCase) return enteredWord.equalsIgnoreCase(comparingWord);
        return enteredWord.equals(comparingWord);
    }

    private void deselectCurrentWord() {
        etWords.clearBackground(currentWordStartCursor, currentWordEndCursor);
    }

    private void selectCurrentWordAsIncorrect() {
        etWords.paintForeground(currentWordStartCursor, currentWordEndCursor, SpannableEditText.getRedForeground());
    }

    private void selectCurrentWordAsCorrect() {
        etWords.paintForeground(currentWordStartCursor, currentWordEndCursor, SpannableEditText.getGreenForeground());
    }

    private void selectNextWord() {
        BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(Color.rgb(0,80,100));
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.WHITE);
        etWords.paintBackground(currentWordStartCursor, currentWordEndCursor, backgroundSpan);
        etWords.paintForeground(currentWordStartCursor, currentWordEndCursor, foregroundSpan);
        updateAutoScroll();
    }

    private void updateAutoScroll() {
        scrollerCursorPosition = currentWordEndCursor + SCROLL_POWER;
        if (scrollerCursorPosition > etWords.length())
            etWords.setSelection(currentWordEndCursor);
        else
            etWords.setSelection(scrollerCursorPosition);
    }

    private void setNextWordCursors() {
        currentWordStartCursor = currentWordEndCursor + 1;
        currentWordEndCursor = currentWordStartCursor;
        while (currentWordEndCursor<etWords.length() && etWords.getText().charAt(currentWordEndCursor)!=' ') {
            currentWordEndCursor++;
        }
        updateCurrentWord();
    }

    private void initializeWordCursor() {
        while (currentWordEndCursor<etWords.length() && etWords.getText().charAt(currentWordEndCursor)!=' ') {
            currentWordEndCursor++;
        }
        updateCurrentWord();
    }

    private void updateCurrentWord() {
        currentWord = etWords.getText().toString().substring(currentWordStartCursor, currentWordEndCursor);
    }

    private ArrayList<Integer> getMistakeIndexes(String inputted, String original) {
        ArrayList<Integer> mistakeIndexes = new ArrayList<>();
        for (int i = 0; i<inputted.length(); i++) {
            if (i >= original.length()) mistakeIndexes.add(i);
            else if (inputted.toLowerCase().charAt(i) != original.toLowerCase().charAt(i))
                mistakeIndexes.add(i);
        }
        return mistakeIndexes;
    }

    private String getDictionaryFolderPath(DictionaryType dictionaryType) {
        if (dictionaryType == DictionaryType.BASIC) return "WordLists/Basic/";
        else return "WordLists/Enhanced/";
    }

    // TODO: move this method to a class that will implement a TextSource interface
    private void initWords(){
        ArrayList<String> loadingWordList = new ArrayList<>();
        AssetManager assets = getAssets();
        InputStream inputStream;
        try {
            String languageIdentifier = gameMode.getLanguage().getIdentifier();
            inputStream = assets.open(
                    getDictionaryFolderPath(gameMode.getDictionaryType())
                            + languageIdentifier +".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                loadingWordList.add(currLine);
            }
            inputStream.close();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.words_loading_error_occurred), Toast.LENGTH_SHORT).show();
            return;
        }
        Random rnd = new Random();
        StringBuilder str = new StringBuilder();
        int amountOfWords = (int)(250 * (timeTotalAmount / 60.0));
        for (int i = 0; i <= amountOfWords; i++) {
            str.append(loadingWordList.get(rnd.nextInt(loadingWordList.size()))).append(" ");
        }
        etWords.setText(str.toString());
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void setScreenOrientation() {
        // TODO: Measure what exact SCROLL_POWER should be
        if (gameMode.getScreenOrientation() == ScreenOrientation.PORTRAIT) {
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
        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, Config.INTERSTITIAL_ID, adRequest,
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
                showResultActivity();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                adShown = true;
                adsCounter.reset();
                showResultActivity();
            }
        });
    }

    private void showAd() {
        if (mInterstitialAd == null) {
            Log.i("AD", "showAd(): Ad is null");
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
        intent.putExtra(StringKeys.TOTAL_WORDS, totalWordsPassed);
        intent.putExtra(StringKeys.TEST_SETTINGS, gameMode);
        intent.putExtra(StringKeys.TEST_TYPED_WORDS_LIST, typedWordsList);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, getIntent().getBooleanExtra(StringKeys.FROM_MAIN_MENU, false));
        finish();
        startActivity(intent);
    }
}