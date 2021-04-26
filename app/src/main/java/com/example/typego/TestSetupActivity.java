package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.typego.utils.Language;
import com.example.typego.utils.TimeConvert;

import java.util.List;
import java.util.Locale;

public class TestSetupActivity extends AppCompatActivity {
    SeekBar sb;
    RadioGroup rbGroup;
    RadioButton radioButton;
    Spinner spinner;
    int progressInSeconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        sb = findViewById(R.id.seekBar);
        TextView tvSeekbarDisplay = findViewById(R.id.tvSeekBarDisplay);
        progressInSeconds = getSelectedSecondsOption(sb.getProgress());
        tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
    }
    public void startTesting(View view) {
        rbGroup = findViewById(R.id.rbDictionaryType);
        radioButton = findViewById(rbGroup.getCheckedRadioButtonId());
        Language language = (Language)spinner.getSelectedItem();
        String languageId = language.getIdentifier();

        int selectedDictionaryIndex = rbGroup.indexOfChild(radioButton);

        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra("AmountOfSeconds", progressInSeconds);
        intent.putExtra("DictionaryType", selectedDictionaryIndex);
        intent.putExtra("DictionaryLanguageId", languageId);
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
        String systemLanguage = Locale.getDefault().getDisplayLanguage().toLowerCase();
        spinner = findViewById(R.id.spinLanguageSelection);
        List<Language> languageList = Language.getAvailableLanguages(this);
        SpinnerAdapter adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                languageList);
        spinner.setAdapter(adapter);
        int systemLanguageIndex = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            String language = adapter.getItem(i).toString();
            if (language.equals(systemLanguage))
                systemLanguageIndex = i;
        }
        spinner.setSelection(systemLanguageIndex);

    }

}