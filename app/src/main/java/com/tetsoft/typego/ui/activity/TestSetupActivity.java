package com.tetsoft.typego.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;
import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.data.account.UserPreferences;
import com.tetsoft.typego.databinding.ActivityTestSetupBinding;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.utils.TimeConvert;
import com.tetsoft.typego.data.account.User;
import com.tetsoft.typego.data.TimeMode;
import java.util.HashMap;
import java.util.Map;

public class TestSetupActivity extends AppCompatActivity {

    User currentUser;
    UserPreferences userPreferences;
    ActivityTestSetupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPreferences = ((TypeGoApp)getApplication()).getPreferences();
        binding = ActivityTestSetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentUser = User.getFromStoredData(this);
        if (currentUser == null) {
            Toast.makeText(this, "An error occurred while loading user data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int dictionaryChildIndex = 0;
        if (userPreferences.getDictionaryType() == DictionaryType.ENHANCED)
            dictionaryChildIndex = 1;

        int screenChildIndex = 0;
        if (userPreferences.getScreenOrientation() == ScreenOrientation.LANDSCAPE)
            screenChildIndex = 1;

        if (userPreferences.getTimeMode() != null)
            binding.seekBar.setProgress(timeModeToProgress(userPreferences.getTimeMode()));

        ((RadioButton) binding.rbDictionaryType.getChildAt(dictionaryChildIndex)).setChecked(true);
        ((RadioButton) binding.rbScreenOrientation.getChildAt(screenChildIndex)).setChecked(true);

        setupSeekBar();
        int progressInSeconds = selectedProgressToSeconds(binding.seekBar.getProgress());
        binding.tvSeekBarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));

        selectCurrentLanguageOption();
        binding.cbPredictiveText.setChecked(userPreferences.isSuggestionsActivated());

    }

    public void startTesting(View view) {
        Intent intent = new Intent(this, TypingTestActivity.class);
        TestSettings testSettings = new TestSettings(
                binding.spinLanguageSelection.getSelectedLanguage(),
                new TimeMode(selectedProgressToSeconds(binding.seekBar.getProgress())),
                getSelectedDictionaryType(),
                binding.cbPredictiveText.isChecked(),
                getSelectedScreenOrientation());
        intent.putExtra(StringKeys.TEST_SETTINGS, testSettings);
        userPreferences.update(testSettings);
        finish();
        startActivity(intent);
    }

    private DictionaryType getSelectedDictionaryType() {
        RadioButton radioButton = findViewById(binding.rbDictionaryType.getCheckedRadioButtonId());
        int selectedDictionaryIndex = binding.rbDictionaryType.indexOfChild(radioButton);
        if (selectedDictionaryIndex == 0) return DictionaryType.BASIC;
        else return DictionaryType.ENHANCED;
    }

    private ScreenOrientation getSelectedScreenOrientation() {
        RadioButton screenOrientation = findViewById(binding.rbScreenOrientation.getCheckedRadioButtonId());
        int selectedScreenOrientation = binding.rbScreenOrientation.indexOfChild(screenOrientation);
        if (selectedScreenOrientation == 0) return ScreenOrientation.PORTRAIT;
        else return ScreenOrientation.LANDSCAPE;
    }

    // TODO: refactor this method and move it outside of the activity
    private int selectedProgressToSeconds(int progress) {
        HashMap<Integer, Integer> seekbarValues = new HashMap<>();
        seekbarValues.put(0, 15);
        seekbarValues.put(1, 30);
        seekbarValues.put(2, 60);
        seekbarValues.put(3, 120);
        seekbarValues.put(4, 180);
        seekbarValues.put(5, 300);
        Integer match = seekbarValues.get(progress);
        if (match == null) return 2;
        else return match;
    }

    private int timeModeToProgress(TimeMode timeMode) {
        HashMap<Integer, Integer> seekbarValues = new HashMap<>();
        seekbarValues.put(0, 15);
        seekbarValues.put(1, 30);
        seekbarValues.put(2, 60);
        seekbarValues.put(3, 120);
        seekbarValues.put(4, 180);
        seekbarValues.put(5, 300);
        for (Map.Entry<Integer, Integer> entry: seekbarValues.entrySet()) {
            if (entry.getValue().equals(timeMode.getTimeInSeconds())) return entry.getKey();
        }
        return 2;
    }

    private void setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvSeekBarDisplay.setText(
                        TimeConvert.convertSeconds(
                        TestSetupActivity.this,
                        selectedProgressToSeconds(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    // Selects either user preferred language or system language
    public void selectCurrentLanguageOption() {
        LanguageSpinnerAdapter spinnerAdapter = new LanguageSpinnerAdapter(
                this,
                new LanguageList().getLocalizedList(this));
        binding.spinLanguageSelection.setAdapter(spinnerAdapter);
        if (userPreferences.getLanguage() != null) {
            binding.spinLanguageSelection.setSelection(
                    spinnerAdapter.getItemIndexByLanguage(userPreferences.getLanguage())
            );
        } else {
            binding.spinLanguageSelection.setSelection(
                    spinnerAdapter.getItemIndexBySystemLanguage()
            );
        }
    }

    public void closeActivity(View view) { finish(); }

}