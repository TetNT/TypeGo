package com.tetsoft.typego.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.tetsoft.typego.R;
import com.tetsoft.typego.account.UserPreferences;
import com.tetsoft.typego.utils.Dictionary;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.account.User;
import com.tetsoft.typego.utils.TimeMode;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final int DEFAULT_AMOUNT_OF_SECONDS = 60;
    static final int DEFAULT_DICTIONARY_TYPE_INDEX = 0;
    static final boolean DEFAULT_SUGGESTIONS_IS_ON = true;
    static final int DEFAULT_SCREEN_ORIENTATION = 0;

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
        Language selectedLanguage = (Language)spinner.getSelectedItem();
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, selectedLanguage);
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, DEFAULT_AMOUNT_OF_SECONDS);
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, DEFAULT_DICTIONARY_TYPE_INDEX);
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, DEFAULT_SUGGESTIONS_IS_ON);
        intent.putExtra(StringKeys.TEST_SCREEN_ORIENTATION, DEFAULT_SCREEN_ORIENTATION);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, true);
        startActivity(intent);
        User user = User.getFromStoredData(this);
        UserPreferences preferences = new UserPreferences(selectedLanguage,
                new TimeMode(DEFAULT_AMOUNT_OF_SECONDS),
                new Dictionary(Dictionary.Type.BASIC),
                DEFAULT_SUGGESTIONS_IS_ON,
                new ScreenOrientation(ScreenOrientation.Orientation.PORTRAIT));
        user.setPreferences(preferences);
        user.rememberUserPreferences(this,
                selectedLanguage,
                2,
                DEFAULT_DICTIONARY_TYPE_INDEX,
                DEFAULT_SUGGESTIONS_IS_ON,
                DEFAULT_SCREEN_ORIENTATION);
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
        }
        currentUser.initAchievements(this);
        currentUser.storeData(this);
    }

    Spinner spinner;
    public void languageSpinnerInit() {
        spinner = findViewById(R.id.spinnerBasicTestLanguageSelection);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Language.getAvailableLanguages(this));
        spinner.setAdapter(spinnerAdapter);
        int preferredLanguageIndex = getPreferredLanguageIndex();
        if (preferredLanguageIndex < 0)
            preferredLanguageIndex = getSystemLanguageIndex();
        spinner.setSelection(preferredLanguageIndex);
    }

    public int getPreferredLanguageIndex() {
        ArrayList<Language> languages = Language.getAvailableLanguages(this);
        User currentUser = User.getFromStoredData(this);
        if (currentUser.getPreferredLanguage() == null) return -1;
        String preferredLanguageId = currentUser.getPreferredLanguage().getIdentifier();
        int index = 0;
        for (Language language:languages) {
            if (language.getIdentifier().equalsIgnoreCase(preferredLanguageId))
                return index;
            index++;
        }
        return 0;
    }

    public int getSystemLanguageIndex() {
        ArrayList<Language> languages = Language.getAvailableLanguages(this);
        String systemLanguage = Locale.getDefault().getDisplayLanguage().toLowerCase();
        int index = 0;
        for (Language language:languages
             ) {
            if (language.getName(MainActivity.this).equalsIgnoreCase(systemLanguage))
                return index;
            index++;
        }
        return 0;
    }
}