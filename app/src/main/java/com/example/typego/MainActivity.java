package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.typego.utils.Language;
import com.example.typego.utils.StringKeys;
import com.example.typego.utils.User;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInit();
        languageSpinnerInit();
    }

    public void startBasicTest(View v) {
        lockScreen();
        Intent intent = new Intent(this, TypingTestActivity.class);
        Spinner spinner = findViewById(R.id.spinnerBasicTestLanguageSelection);
        Language selectedLanguage = (Language)spinner.getSelectedItem();
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, selectedLanguage);
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, 60);
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, 0);
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, true);
        intent.putExtra(StringKeys.TEST_SCREEN_ORIENTATION, 0);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, true);
        startActivity(intent);
        finish();
    }

    public void openTestCustomization(View v) {
        lockScreen();
        Intent intent = new Intent(this, TestSetupActivity.class);
        startActivity(intent);
        unlockScreen();
    }

    public void openUserAccount(View v) {
        lockScreen();
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        unlockScreen();
    }

    private void lockScreen() {
        ProgressBar loading = findViewById(R.id.loadingProgressBar);
        loading.setVisibility(View.VISIBLE);
        LinearLayout cardsSection = findViewById(R.id.cardsSection);
        for (int i = 0; i < cardsSection.getChildCount(); i++) {
            View child = cardsSection.getChildAt(i);
            child.setEnabled(false);
        }
    }

    private void unlockScreen() {
        ProgressBar loading = findViewById(R.id.loadingProgressBar);
        loading.setVisibility(View.GONE);
        LinearLayout cardsSection = findViewById(R.id.cardsSection);
        for (int i = 0; i < cardsSection.getChildCount(); i++) {
            View child = cardsSection.getChildAt(i);
            child.setEnabled(true);
        }
    }

    public void userInit() {
        User currentUser = User.getFromStoredData(this);
        if (currentUser==null) {
            currentUser = new User();
            currentUser.initAchievements(this);
        }
        currentUser.storeData(this);
    }

    public void languageSpinnerInit() {
        Spinner spinner = findViewById(R.id.spinnerBasicTestLanguageSelection);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Language.getAvailableLanguages(this));
        spinner.setAdapter(spinnerAdapter);
    }
}