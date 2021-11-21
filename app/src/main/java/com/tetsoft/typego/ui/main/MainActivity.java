package com.tetsoft.typego.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.R;
import com.tetsoft.typego.account.UserPreferences;
import com.tetsoft.typego.databinding.ActivityMainBinding;
import com.tetsoft.typego.storage.UserStorage;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.ui.AccountActivity;
import com.tetsoft.typego.ui.TestSetupActivity;
import com.tetsoft.typego.ui.TypingTestActivity;
import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.account.User;
import com.tetsoft.typego.utils.TimeMode;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final TimeMode DEFAULT_TIME_MODE = new TimeMode(60);
    static final DictionaryType DEFAULT_DICTIONARY_TYPE = DictionaryType.BASIC;
    static final boolean DEFAULT_SUGGESTIONS_IS_ON = true;
    static final ScreenOrientation DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT;

    ActivityMainBinding binding;
    UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        userPreferences = ((TypeGoApp)getApplication()).getPreferences();
        setContentView(binding.getRoot());
        userInit();
        setupLanguageSpinner();
    }

    public void startBasicTest(View v) {
        Intent intent = new Intent(this, TypingTestActivity.class);
        TestSettings DEFAULT_TEST_SETTINGS = new TestSettings(
                (Language)binding.spinnerBasicTestLanguageSelection.getSelectedItem(),
                DEFAULT_TIME_MODE,
                DEFAULT_DICTIONARY_TYPE,
                DEFAULT_SUGGESTIONS_IS_ON,
                DEFAULT_SCREEN_ORIENTATION);
        intent.putExtra(StringKeys.TEST_SETTINGS, DEFAULT_TEST_SETTINGS);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, true);
        startActivity(intent);
        finish();
    }

    public void openTestCustomization(View v) {
        startActivity(new Intent(this, TestSetupActivity.class));
    }

    public void openUserAccount(View v) {
        startActivity(new Intent(this, AccountActivity.class));
    }


    public void userInit() {
        User currentUser = User.getFromStoredData(this);
        if (currentUser==null) {
            currentUser = new User();
        }
        currentUser.initAchievements(this);
        currentUser.storeData(this);
    }

    public void setupLanguageSpinner() {
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Language.getAvailableLanguages(this));
        binding.spinnerBasicTestLanguageSelection.setAdapter(spinnerAdapter);
        int preferredLanguageIndex = getPreferredLanguageIndex();
        if (preferredLanguageIndex < 0)
            preferredLanguageIndex = getSystemLanguageIndex();
        binding.spinnerBasicTestLanguageSelection.setSelection(preferredLanguageIndex);
    }

    // Returns an index of the preferred language in the list.
    public int getPreferredLanguageIndex() {
        ArrayList<Language> languages = Language.getAvailableLanguages(this);
        if (userPreferences.getLanguage() == null) return -1;
        String preferredLanguageId = userPreferences.getLanguage().getIdentifier();
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
        for (Language language:languages) {
            if (language.getName(MainActivity.this).equalsIgnoreCase(systemLanguage))
                return index;
            index++;
        }
        return 0;
    }
}