package com.tetsoft.typego.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.data.account.UserPreferences;
import com.tetsoft.typego.databinding.ActivityMainBinding;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.data.account.User;
import com.tetsoft.typego.data.TimeMode;

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
                binding.spinnerBasicTestLanguageSelection.getSelectedLanguage(),
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
        LanguageSpinnerAdapter spinnerAdapter = new LanguageSpinnerAdapter(
                this,
                new LanguageList().getTranslatableListInAlphabeticalOrder(this));
        binding.spinnerBasicTestLanguageSelection.setAdapter(spinnerAdapter);
        if (userPreferences.getLanguage() != null) {
            binding.spinnerBasicTestLanguageSelection.setSelection(
                    spinnerAdapter.getItemIndexByLanguage(userPreferences.getLanguage())
            );
        } else {
            binding.spinnerBasicTestLanguageSelection.setSelection(
                    spinnerAdapter.getItemIndexBySystemLanguage()
            );
        }
    }
}