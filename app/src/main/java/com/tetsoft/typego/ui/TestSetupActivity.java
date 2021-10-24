package com.tetsoft.typego.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.tetsoft.typego.R;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.TimeConvert;
import com.tetsoft.typego.account.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestSetupActivity extends AppCompatActivity {

    SeekBar seekBar;
    RadioGroup rbDictionaryType, rbScreenOrientation;
    Spinner spinner;
    int progressInSeconds;
    CheckBox cbTextSuggestions;
    User currentUser;
    TextView tvSeekbarDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        currentUser = User.getFromStoredData(this);
        if (currentUser == null) {
            Toast.makeText(this, "An error occurred while loading user data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        rbDictionaryType = findViewById(R.id.rbDictionaryType);
        rbScreenOrientation = findViewById(R.id.rbScreenOrientation);
        ((RadioButton) rbDictionaryType.getChildAt(currentUser.getPreferredDictionaryType())).setChecked(true);
        ((RadioButton) rbScreenOrientation.getChildAt(currentUser.getPreferredScreenOrientation())).setChecked(true);

        initSeekBar();
        initTextSuggestions();
        progressInSeconds = getSelectedSecondsOption(seekBar.getProgress());
        tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));

        selectCurrentLanguageOption();
        cbTextSuggestions.setChecked(currentUser.isPreferredTextSuggestions());
        seekBar.setProgress(currentUser.getPreferredTimeMode());

    }

    public void startTesting(View view) {

        RadioButton radioButton = findViewById(rbDictionaryType.getCheckedRadioButtonId());
        Language language = (Language)spinner.getSelectedItem();
        int selectedDictionaryIndex = rbDictionaryType.indexOfChild(radioButton);

        RadioButton screenOrientation = findViewById(rbScreenOrientation.getCheckedRadioButtonId());
        int selectedScreenOrientation = rbScreenOrientation.indexOfChild(screenOrientation);

        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, progressInSeconds);
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, selectedDictionaryIndex);
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, cbTextSuggestions.isChecked());
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, language);
        intent.putExtra(StringKeys.TEST_SCREEN_ORIENTATION, selectedScreenOrientation);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, false);

        // remember user settings
        if (currentUser != null) {
            currentUser.rememberUserPreferences(this,
                    language,
                    seekBar.getProgress(),
                    selectedDictionaryIndex,
                    cbTextSuggestions.isChecked(),
                    selectedScreenOrientation);
        }
        finish();
        startActivity(intent);
    }

    private int getSelectedSecondsOption(int progress) {
        switch (progress) {
            case 0: return 15;
            case 1: return 30;
            case 2: return 60;
            case 3: return 120;
            case 4: return 180;
            case 5: return 300;
        }
        return 15;
    }

    private void initSeekBar() {
        tvSeekbarDisplay = findViewById(R.id.tvSeekBarDisplay);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressInSeconds = getSelectedSecondsOption(progress);
                tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initTextSuggestions() {
        cbTextSuggestions = findViewById(R.id.cbPredictiveText);
        cbTextSuggestions.setOnClickListener((v) -> {
            if (cbTextSuggestions.isChecked()) {
                Toast.makeText(this, getString(R.string.text_suggestions_enabled), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, getString(R.string.text_suggestions_disabled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // select either user preferred language or system language
    public void selectCurrentLanguageOption() {
        spinner = findViewById(R.id.spinLanguageSelection);
        List<Language> languageList = Language.getAvailableLanguages(this);
        SpinnerAdapter adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                languageList);
        spinner.setAdapter(adapter);
        int preferredLanguageIndex = getPreferredLanguageIndex();
        if (preferredLanguageIndex < 0)
            preferredLanguageIndex = getSystemLanguageIndex();
        spinner.setSelection(preferredLanguageIndex);
    }


    public int getPreferredLanguageIndex() {
        ArrayList<Language> languages = Language.getAvailableLanguages(this);
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
            if (language.getName(TestSetupActivity.this).equalsIgnoreCase(systemLanguage))
                return index;
            index++;
        }
        return 0;
    }

    public void closeActivity(View view) { finish(); }

}