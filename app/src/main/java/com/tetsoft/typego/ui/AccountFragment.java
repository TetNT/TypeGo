package com.tetsoft.typego.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.tetsoft.typego.AnimationManager;
import com.tetsoft.typego.R;
import com.tetsoft.typego.account.User;
import com.tetsoft.typego.testing.PassedTestsAdapter;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.testing.ResultListUtils;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.TimeMode;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    User currentUser;
    ArrayList<TypingResult> selectedResults;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = User.getFromStoredData(getContext());
        initLanguageSpinner(view);
        loadAccountData();
        leaveFeedbackClick();
    }


    private void loadAccountData() {
        View view = getView();
        if (view==null) return;
        setupStatFields(view);
        initResultSet();
        updateResults(view);
        loadLastResultsData(view);
    }

    private void setupStatFields(View view) {
        view.<TextView>findViewById(R.id.tvAverageWPM).setText(getString(R.string.average_wpm_pl, 0));
        view.<TextView>findViewById(R.id.tvBestResult).setText(getString(R.string.best_result_pl, 0));
        view.<TextView>findViewById(R.id.tvAccountLastResult).setText(getString(R.string.previous_result_pl, 0));
        view.<TextView>findViewById(R.id.tvTestsPassed).setText(getString(R.string.tests_passed_pl, 0));
    }

    Spinner spinner;
    public void initLanguageSpinner(View view) {
        ArrayList<Language> languages = Language.getAvailableLanguages(getContext());
        // an option to show the whole stats
        languages.add(0, new Language(StringKeys.LANGUAGE_ALL, getContext()));
        spinner = view.findViewById(R.id.spinnerResultsLanguageSelection);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, languages);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadAccountData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initResultSet() {
        if (currentUser==null) return;
        Language selectedLanguage = (Language)spinner.getSelectedItem();
        if (selectedLanguage.getIdentifier().equalsIgnoreCase(StringKeys.LANGUAGE_ALL))
            selectedResults = currentUser.getResultList();
        else selectedResults = ResultListUtils.getResultsByLanguage(currentUser.getResultList(), selectedLanguage);
    }

    private void updateResults(@NonNull View view) {
        TextView tvInfo = view.findViewById(R.id.tvPassedTestsInfo);
        TextView tvAvgWpm = view.findViewById(R.id.tvAverageWPM);
        if (selectedResults == null || selectedResults.isEmpty()) {
            tvInfo.setText(getString(R.string.msg_nothing_to_show));
            tvAvgWpm.setText(getString(R.string.msg_average_wpm_unavailable));
            return;
        }
        tvInfo.setText(getString(R.string.msg_passed_tests_information));
        TextView tvTestsPassed = view.findViewById(R.id.tvTestsPassed);
        tvTestsPassed.setText(getString(R.string.tests_passed_pl, selectedResults.size()));
        TextView tvBestResult = view.findViewById(R.id.tvBestResult);
        int bestResult = (int)ResultListUtils.getBestResult(selectedResults).getWPM();
        tvBestResult.setText(getString(R.string.best_result_pl, bestResult));
        TextView tvLastResult = view.findViewById(R.id.tvAccountLastResult);
        tvLastResult.setText(getString(R.string.previous_result_pl, (int)selectedResults.get(0).getWPM()));
        String wpmStr;
        if (selectedResults.size()>=5) {
            double wpmSum = 0;
            for (TypingResult res: selectedResults) wpmSum += res.getWPM();
            wpmStr = getString(R.string.average_wpm) + ": " + (int)(wpmSum/selectedResults.size());
            tvAvgWpm.setText(wpmStr);
        } else
            tvAvgWpm.setText(getString(R.string.msg_average_wpm_unavailable));
    }

    public void leaveFeedbackClick() {
        TextView tvLeaveFeedback = getActivity().findViewById(R.id.tvLeaveFeedback);
        tvLeaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    public void loadLastResultsData(@NonNull View view) {
        RecyclerView rvPassedTests = view.findViewById(R.id.rv_passed_tests);
        PassedTestsAdapter.RecyclerViewOnClickListener listener = (v, position) -> {
            TypingResult resultInfo = selectedResults.get(position);
            DictionaryType dictionaryType =
                    (resultInfo.getDictionaryType() == 0) ? DictionaryType.BASIC : DictionaryType.ENHANCED;
            ScreenOrientation orientation =
                    (resultInfo.getScreenOrientation() == 0) ? ScreenOrientation.PORTRAIT : ScreenOrientation.LANDSCAPE;
            Intent intent = new Intent(getContext(), ResultActivity.class);
            TestSettings testSettings = new TestSettings(
                    resultInfo.getLanguage(),
                    new TimeMode(resultInfo.getTimeInSeconds()),
                    dictionaryType,
                    resultInfo.isTextSuggestions(),
                    orientation);
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS, resultInfo.getCorrectWords());
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS_WEIGHT, resultInfo.getCorrectWordsWeight());
            intent.putExtra(StringKeys.TOTAL_WORDS, resultInfo.getTotalWords());
            intent.putExtra(StringKeys.TEST_SETTINGS, testSettings);
            intent.putExtra(StringKeys.CALLED_FROM_PASSED_RESULTS, true);
            startActivity(intent);
        };
        rvPassedTests.setAdapter(new PassedTestsAdapter(getContext(), selectedResults,listener));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPassedTests.setLayoutManager(linearLayoutManager);

        AnimationManager animationManager = new AnimationManager();
        Animation slideIn = animationManager.getSlideInAnimation(0, 50f, 500);
        Animation fadeIn = animationManager.getFadeInAnimation(500);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(slideIn);
        animationSet.addAnimation(fadeIn);
        rvPassedTests.setAnimation(animationSet);
    }
}