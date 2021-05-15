package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiEditText;
import androidx.emoji.widget.EmojiTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typego.utils.Emoji;
import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.TimeConvert;
import com.example.typego.utils.TypingResult;
import com.example.typego.utils.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {
    User currentUser;
    double wpm;
    int correctWords;
    int timeInSeconds;
    int dictionaryType;
    String dictionaryLanguageId;
    SharedPreferences spUser;

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
        TextView tvBestResult = findViewById(R.id.tvBestResult);
        TextView tvCorrectWords = findViewById((R.id.tvCorrectWords));
        TextView tvIncorrectWords = findViewById(R.id.tvIncorrectWords);
        TextView tvDictionary = findViewById(R.id.tvDictionary);
        TextView tvAllottedTime = findViewById(R.id.tvAllottedTime);
        TextView tvLanguage = findViewById(R.id.tvLanguage);
        TextView tvTextSuggestions = findViewById(R.id.tvTextSuggestions);

        tvPreviousResult.setText(getString(R.string.Previous_result) + ": " + currentUser.getLastResult());
        if (currentUser.getLastResult() == 0)
            tvPreviousResult.setVisibility(View.GONE);
        else tvPreviousResult.setVisibility(View.VISIBLE);

        tvBestResult.setText(getString(R.string.Best_result) + ": " + currentUser.getBestResult());
        if (currentUser.getBestResult() == 0)
            tvBestResult.setVisibility(View.GONE);
        else tvBestResult.setVisibility(View.VISIBLE);

        Bundle arguments = getIntent().getExtras();
        correctWords = arguments.getInt(KeyConstants.TEST_CORRECT_WORDS);
        timeInSeconds = arguments.getInt(KeyConstants.TEST_AMOUNT_OF_SECONDS);
        int totalWords = arguments.getInt(KeyConstants.TOTAL_WORDS);
        dictionaryType = arguments.getInt(KeyConstants.TEST_DICTIONARY_TYPE);
        dictionaryLanguageId = arguments.getString(KeyConstants.TEST_DICTIONARY_LANG);
        String textSuggestions = getString(R.string.No);
        if (arguments.getBoolean(KeyConstants.TEST_SUGGESTIONS_ON))
            textSuggestions = getString(R.string.Yes);
        String DictionaryName = getString(R.string.Enhanced);
        if (dictionaryType == 0) DictionaryName = getString(R.string.Basic);
        wpm = (60.0 / timeInSeconds) * correctWords;
        String wpmText = new DecimalFormat("0.#").format(wpm) + " " + getString(R.string.WordsPerMinute) + "\n" + Emoji.getEmojiOfWpm((int) wpm);
        tvWPM.setText(wpmText);
        tvIncorrectWords.setText(getString(R.string.Incorrect_words) + ": " + (totalWords - correctWords));
        tvCorrectWords.setText(getString(R.string.Correct_words) + ": " + correctWords);
        tvDictionary.setText(getString(R.string.Dictionary) + ": " + DictionaryName);
        tvAllottedTime.setText(getString(R.string.Allotted_time) + ": " + TimeConvert.convertSeconds(this, timeInSeconds));
        tvLanguage.setText(getString(R.string.selected_language) + ": " + dictionaryLanguageId);
        tvTextSuggestions.setText(getString(R.string.text_suggestions) + ": " + textSuggestions);
        if (wpm > 0) SaveResultData();
        else
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT).show();

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
    }


    private void SaveResultData() {
        if (currentUser == null) {
            Toast.makeText(this, getString(R.string.saving_results_error_occurred), Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setLastResult((int)wpm);
        if (currentUser.getBestResult() < currentUser.getLastResult()) {
            currentUser.setBestResult(currentUser.getLastResult());
        }

        TypingResult result = new TypingResult(wpm, dictionaryType, dictionaryLanguageId, timeInSeconds, Calendar.getInstance().getTime());
        currentUser.addResult(result);

        SharedPreferences.Editor editor = spUser.edit();
        editor.putString(KeyConstants.PREFERENCES_CURRENT_USER,User.serializeToJson(currentUser));
        editor.apply();



    }


    public void SaveAndContinue(View view){
        finish();
    }

}