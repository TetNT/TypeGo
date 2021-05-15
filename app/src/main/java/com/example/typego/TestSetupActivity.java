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

import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.Language;
import com.example.typego.utils.TimeConvert;
import com.example.typego.utils.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        currentUser = User.getFromJson(getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE).getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        cbTextSuggestions = findViewById(R.id.cbPredictiveText);
        rbDictionaryType = findViewById(R.id.rbDictionaryType);
        BottomNavigationView bnv = findViewById(R.id.test_setup_bottom_nav);
        bnv.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.testSetupActivity:
                    break;
                case R.id.accountActivity:
                    intent = new Intent(TestSetupActivity.this, AccountActivity.class);
                    finish();
                    startActivity(intent);
            }
            return true;
        });
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
        String languageId = language.getIdentifier();

        int selectedDictionaryIndex = rbDictionaryType.indexOfChild(radioButton);

        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra(KeyConstants.TEST_AMOUNT_OF_SECONDS, progressInSeconds);
        intent.putExtra(KeyConstants.TEST_DICTIONARY_TYPE, selectedDictionaryIndex);
        intent.putExtra(KeyConstants.TEST_SUGGESTIONS_ON, cbTextSuggestions.isChecked());
        intent.putExtra(KeyConstants.TEST_DICTIONARY_LANG, languageId);

        // remember user settings
        if (currentUser != null) {
            currentUser.setPreferredLanguage(languageId);
            currentUser.setPreferredDictionaryType(selectedDictionaryIndex);
            currentUser.setPreferredTextSuggestions(cbTextSuggestions.isChecked());
            currentUser.setPreferredTimeMode(seekBar.getProgress());
            SharedPreferences.Editor editor = getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE).edit();
            editor.putString(KeyConstants.PREFERENCES_CURRENT_USER,User.serializeToJson(currentUser));
            editor.apply();
        }
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
        if (currentUser.getPreferredLanguageId()!=null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                String languageId = ((Language) adapter.getItem(i)).getIdentifier();
                if (languageId.equalsIgnoreCase(currentUser.getPreferredLanguageId()))
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
        return;
    }

}