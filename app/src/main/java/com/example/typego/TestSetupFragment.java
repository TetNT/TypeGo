package com.example.typego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TestSetupFragment extends Fragment {

    SeekBar seekBar;
    RadioGroup rbDictionaryType;
    Spinner spinner;
    int progressInSeconds;
    CheckBox cbTextSuggestions;
    User currentUser;
    public TestSetupFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_test_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser =
                User.getFromJson(getActivity()
                        .getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE)
                        .getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        cbTextSuggestions = view.findViewById(R.id.cbPredictiveText);
        rbDictionaryType = view.findViewById(R.id.rbDictionaryType);
        cbTextSuggestions.setOnClickListener((v) -> {
            if (cbTextSuggestions.isChecked())
                Toast.makeText(getActivity(),
                        getString(R.string.text_suggestions_enabled),
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(),
                        getString(R.string.text_suggestions_disabled),
                        Toast.LENGTH_SHORT).show();
        });
        seekBar = view.findViewById(R.id.seekBar);
        TextView tvSeekbarDisplay = view.findViewById(R.id.tvSeekBarDisplay);
        progressInSeconds = getSelectedSecondsOption(seekBar.getProgress());
        tvSeekbarDisplay.setText(TimeConvert.convertSeconds(getActivity(), progressInSeconds));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressInSeconds = getSelectedSecondsOption(progress);
                tvSeekbarDisplay.setText(TimeConvert.convertSeconds(getActivity(), progressInSeconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        selectCurrentLanguageOption(view);
        cbTextSuggestions.setChecked(currentUser.isPreferredTextSuggestions());
        seekBar.setProgress(currentUser.getPreferredTimeMode());
        ((RadioButton) rbDictionaryType.getChildAt(currentUser.getPreferredDictionaryType())).setChecked(true);
        Button bStart = view.findViewById(R.id.bStartTesting);
        bStart.setOnClickListener(v -> startTesting());
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
    public void selectCurrentLanguageOption(@NotNull View view) {
        spinner = view.findViewById(R.id.spinLanguageSelection);
        List<Language> languageList = Language.getAvailableLanguages(getActivity());
        SpinnerAdapter adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.spinner_style,
                //R.layout.support_simple_spinner_dropdown_item,
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
    }

    public void startTesting() {
        RadioButton radioButton = getActivity().findViewById(rbDictionaryType.getCheckedRadioButtonId());
        Language language = (Language)spinner.getSelectedItem();
        String languageId = language.getIdentifier();

        int selectedDictionaryIndex = rbDictionaryType.indexOfChild(radioButton);
        Intent intent = new Intent(getActivity(), TypingTestActivity.class);
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
            SharedPreferences.Editor editor = getActivity()
                    .getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE)
                    .edit();
            editor.putString(KeyConstants.PREFERENCES_CURRENT_USER,User.serializeToJson(currentUser));
            editor.apply();
        }
        getActivity().finish();
        startActivity(intent);
    }
}