package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.typego.utils.StringKeys;
import com.example.typego.utils.Language;
import com.example.typego.utils.TimeConvert;
import com.example.typego.utils.User;

import java.util.List;
import java.util.Locale;

public class TestSetupActivity extends AppCompatActivity {

    SeekBar seekBar;
    RadioGroup rbDictionaryType;
    Spinner spinner;
    int progressInSeconds;
    CheckBox cbTextSuggestions;
    User currentUser;

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
        cbTextSuggestions = findViewById(R.id.cbPredictiveText);
        rbDictionaryType = findViewById(R.id.rbDictionaryType);
        cbTextSuggestions.setOnClickListener((v) -> {
            if (cbTextSuggestions.isChecked())
                Toast.makeText(this, getString(R.string.text_suggestions_enabled), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, getString(R.string.text_suggestions_disabled), Toast.LENGTH_SHORT).show();
        });
        seekBar = findViewById(R.id.seekBar);
        TextView tvSeekbarDisplay = findViewById(R.id.tvSeekBarDisplay);
        progressInSeconds = getSelectedSecondsOption(seekBar.getProgress());
        tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
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
        selectCurrentLanguageOption();
        cbTextSuggestions.setChecked(currentUser.isPreferredTextSuggestions());
        seekBar.setProgress(currentUser.getPreferredTimeMode());
        ((RadioButton) rbDictionaryType.getChildAt(currentUser.getPreferredDictionaryType())).setChecked(true);
    }

    public void startTesting(View view) {

        RadioButton radioButton = findViewById(rbDictionaryType.getCheckedRadioButtonId());
        Language language = (Language)spinner.getSelectedItem();

        int selectedDictionaryIndex = rbDictionaryType.indexOfChild(radioButton);
        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, progressInSeconds);
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, selectedDictionaryIndex);
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, cbTextSuggestions.isChecked());
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, language);
        intent.putExtra(StringKeys.FROM_MAIN_MENU, false);

        // remember user settings
        if (currentUser != null) {
            currentUser.setPreferredLanguage(language);
            currentUser.setPreferredDictionaryType(selectedDictionaryIndex);
            currentUser.setPreferredTextSuggestions(cbTextSuggestions.isChecked());
            currentUser.setPreferredTimeMode(seekBar.getProgress());
            currentUser.storeData(this);
        }
        finish();
        startActivity(intent);
    }

    private int getSelectedSecondsOption(int progress) {
        int progressToSeconds;
        if (progress<4)
            progressToSeconds = (progress+1)*15;
        else if (progress==4) progressToSeconds = 90;
        else if (progress==5) progressToSeconds = 120;
        else if (progress==6) progressToSeconds = 180;
        else progressToSeconds = 300;
        return progressToSeconds;
    }

    // select either user preferred language or system language
    public void selectCurrentLanguageOption() {
        spinner = findViewById(R.id.spinLanguageSelection);
        List<Language> languageList = Language.getAvailableLanguages(this);
        SpinnerAdapter adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                languageList);
        spinner.setAdapter(adapter);
        int systemLanguageIndex = 0;
        String systemLanguage = Locale.getDefault().getDisplayLanguage().toLowerCase();
        if (currentUser.getPreferredLanguage()!=null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                String languageId = ((Language) adapter.getItem(i)).getIdentifier();
                if (languageId.equalsIgnoreCase(currentUser.getPreferredLanguage().getIdentifier()))
                    systemLanguageIndex = i;
            }
            spinner.setSelection(systemLanguageIndex);
            return;
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            String language = ((Language) adapter.getItem(i)).getName();
            if (language.equals(systemLanguage))
                systemLanguageIndex = i;
        }
        spinner.setSelection(systemLanguageIndex);
    }

}