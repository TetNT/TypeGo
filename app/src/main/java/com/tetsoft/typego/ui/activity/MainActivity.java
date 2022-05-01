package com.tetsoft.typego.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.game.mode.GameOnTime;
import com.tetsoft.typego.game.result.GameResultList;
import com.tetsoft.typego.data.account.UserPreferences;
import com.tetsoft.typego.data.achievement.AchievementList;
import com.tetsoft.typego.data.achievement.AchievementMigration;
import com.tetsoft.typego.databinding.ActivityMainBinding;
import com.tetsoft.typego.game.result.migration.TypingResultToGameResultMigration;
import com.tetsoft.typego.storage.AchievementsProgressStorage;
import com.tetsoft.typego.storage.GameResultListStorage;
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
        userPreferences = ((TypeGoApp) getApplication()).getPreferences();
        setContentView(binding.getRoot());
        checkMigrations();
        setupLanguageSpinner();
    }

    public void checkMigrations() {
        User currentUser = User.getFromStoredData(this);
        GameResultListStorage resultListStorage = ((TypeGoApp) getApplication()).getResultListStorage();
        try {
            if (resultListStorage.isEmpty() && currentUser == null) {
                Log.i("MIGR", "checkMigrations: first if statement");
                resultListStorage.store(new GameResultList());
            } else if (resultListStorage.isEmpty() && currentUser != null) {
                Log.i("MIGR", "checkMigrations: second if statement");
                TypingResultToGameResultMigration migration =
                        new TypingResultToGameResultMigration(currentUser.getResultList());
                GameResultList results = migration.migrate();
                resultListStorage.store(results);
            }
        } catch (Exception e) {
            Log.e("MIGR", "checkMigrations failed to migrate to the new GameResultList: " + e.getMessage());
        }

        try {
            AchievementsProgressStorage achievementStorage = ((TypeGoApp) getApplication()).getAchievementsProgress();
            if (achievementStorage.isEmpty() && currentUser == null) {
                AchievementMigration achievementMigration = new AchievementMigration(new AchievementList(this).get());
                achievementMigration.storeProgress(achievementStorage);
            } else if (achievementStorage.isEmpty() && currentUser != null) {
                AchievementMigration achievementMigration = new AchievementMigration(currentUser.getAchievements());
                achievementMigration.storeProgress(achievementStorage);
            }
        } catch (Exception e) {
            Log.e("MIGR", "checkMigrations failed to migrate to the new achievements progress:" + e.getMessage());
        }
    }

    public void startBasicTest(View v) {
        Intent intent = new Intent(this, TypingTestActivity.class);
        GameOnTime basicGameMode = new GameOnTime(binding.spinnerBasicTestLanguageSelection.getSelectedLanguage(),
                DEFAULT_TIME_MODE,
                DEFAULT_DICTIONARY_TYPE,
                DEFAULT_SUGGESTIONS_IS_ON,
                DEFAULT_SCREEN_ORIENTATION);
        intent.putExtra(StringKeys.TEST_SETTINGS, basicGameMode);
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