package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.typego.utils.Emoji;
import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.TimeConvert;
import com.example.typego.utils.TypingResult;
import com.example.typego.utils.User;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {
    User currentUser;
    double wpm;
    int correctWords;
    int correctWordsWeight;
    int timeInSeconds;
    int dictionaryType;
    int totalWords = 0;
    boolean calledFromResultsTab;
    boolean textSuggestionsIsOn;
    String dictionaryLanguageId;
    SharedPreferences spUser;
    TextView tvBestResult;
    TypingResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiCompat.Config emojiConfig = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(emojiConfig);
        setContentView(R.layout.activity_result);
        spUser = getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE);
        currentUser = User.getFromJson(spUser.getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        EmojiTextView tvWPM = findViewById(R.id.tvWPM);
        TextView tvPreviousResult = findViewById(R.id.tvPreviousResult);
        tvBestResult = findViewById(R.id.tvBestResult);
        TextView tvCorrectWords = findViewById((R.id.tvCorrectWords));
        TextView tvIncorrectWords = findViewById(R.id.tvIncorrectWords);
        TextView tvDictionary = findViewById(R.id.tvDictionary);
        TextView tvAllottedTime = findViewById(R.id.tvAllottedTime);
        TextView tvLanguage = findViewById(R.id.tvLanguage);
        TextView tvTextSuggestions = findViewById(R.id.tvTextSuggestions);

        tvPreviousResult.setText(getString(R.string.previous_result) + ": " + currentUser.getLastResult());
        if (currentUser.getLastResult() == 0)
            tvPreviousResult.setVisibility(View.GONE);
        else tvPreviousResult.setVisibility(View.VISIBLE);

        tvBestResult.setText(getString(R.string.best_result) + ": " + currentUser.getBestResult());
        if (currentUser.getBestResult() == 0)
            tvBestResult.setVisibility(View.GONE);
        else tvBestResult.setVisibility(View.VISIBLE);

        Bundle arguments = getIntent().getExtras();
        initIntentData(arguments);
        String textSuggestions = getString(R.string.no);
        if (textSuggestionsIsOn)
            textSuggestions = getString(R.string.yes);
        String DictionaryName = getString(R.string.enhanced);
        if (dictionaryType == 0) DictionaryName = getString(R.string.basic);
        result = getCurrentResult();
        wpm = result.getWPM();
        String wpmText = new DecimalFormat("0.#").format(wpm) + " " + getString(R.string.wordsPerMinute) + "\n" + Emoji.getEmojiOfWpm((int) wpm);
        tvWPM.setText(wpmText);
        tvIncorrectWords.setText(getString(R.string.incorrect_words) + ": " + (totalWords - correctWords));
        tvCorrectWords.setText(getString(R.string.correct_words) + ": " + correctWords);
        tvDictionary.setText(getString(R.string.dictionary) + ": " + DictionaryName);
        tvAllottedTime.setText(getString(R.string.allotted_time) + ": " + TimeConvert.convertSeconds(this, timeInSeconds));
        tvLanguage.setText(getString(R.string.selected_language) + ": " + dictionaryLanguageId);
        tvTextSuggestions.setText(getString(R.string.text_suggestions) + ": " + textSuggestions);
        if (wpm <= 0 && !calledFromResultsTab)
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT).show();
        else if (wpm>0 && !calledFromResultsTab) SaveResultData();

        Button bStartOver = findViewById(R.id.bStartOver);
        bStartOver.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, TypingTestActivity.class);
            intent.putExtra(KeyConstants.TEST_SUGGESTIONS_ON, arguments.getBoolean(KeyConstants.TEST_SUGGESTIONS_ON));
            intent.putExtra(KeyConstants.TEST_AMOUNT_OF_SECONDS, arguments.getInt(KeyConstants.TEST_AMOUNT_OF_SECONDS));
            intent.putExtra(KeyConstants.TEST_DICTIONARY_TYPE, arguments.getInt(KeyConstants.TEST_DICTIONARY_TYPE));
            intent.putExtra(KeyConstants.TEST_DICTIONARY_LANG, arguments.getString(KeyConstants.TEST_DICTIONARY_LANG));
            finish();
            startActivity(intent);
        });
        if (calledFromResultsTab) {
            bStartOver.setVisibility(View.GONE);
            tvPreviousResult.setVisibility(View.GONE);
        }

    }

    void initIntentData(Bundle arguments) {
        correctWords = arguments.getInt(KeyConstants.TEST_CORRECT_WORDS);
        correctWordsWeight = arguments.getInt(KeyConstants.TEST_CORRECT_WORDS_WEIGHT);
        timeInSeconds = arguments.getInt(KeyConstants.TEST_AMOUNT_OF_SECONDS);
        totalWords = arguments.getInt(KeyConstants.TOTAL_WORDS);
        dictionaryType = arguments.getInt(KeyConstants.TEST_DICTIONARY_TYPE);
        dictionaryLanguageId = arguments.getString(KeyConstants.TEST_DICTIONARY_LANG);
        textSuggestionsIsOn = arguments.getBoolean(KeyConstants.TEST_SUGGESTIONS_ON);
        calledFromResultsTab = arguments.getBoolean(KeyConstants.CALLED_FROM_PASSED_RESULTS);
    }

    private void SaveResultData() {
        if (currentUser == null) {
            Toast.makeText(this, getString(R.string.saving_results_error_occurred), Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setLastResult((int)wpm);
        if (currentUser.getBestResult() < currentUser.getLastResult()) {
            currentUser.setBestResult(currentUser.getLastResult());
            TextView tvNewBestResult = findViewById(R.id.tvNewBestResult);
            tvNewBestResult.setVisibility(View.VISIBLE);
        }

        currentUser.addResult(result);

        SharedPreferences.Editor editor = spUser.edit();
        editor.putString(KeyConstants.PREFERENCES_CURRENT_USER,User.serializeToJson(currentUser));
        editor.apply();
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable: textView.getCompoundDrawables())
            if (drawable!=null) drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void SaveAndContinue(View view){
        finish();
        if (!calledFromResultsTab) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }

    private TypingResult getCurrentResult() {
        TypingResult result = new TypingResult(
                correctWords,
                correctWordsWeight,
                totalWords,
                dictionaryType,
                dictionaryLanguageId,
                timeInSeconds,
                textSuggestionsIsOn,
                Calendar.getInstance().getTime());
        return result;
    }

}