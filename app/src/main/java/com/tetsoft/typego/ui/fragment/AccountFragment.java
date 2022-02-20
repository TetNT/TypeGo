package com.tetsoft.typego.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter;
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.databinding.FragmentAccountBinding;
import com.tetsoft.typego.utils.AnimationManager;
import com.tetsoft.typego.R;
import com.tetsoft.typego.data.account.User;
import com.tetsoft.typego.adapter.PassedTestsAdapter;
import com.tetsoft.typego.testing.TestSettings;
import com.tetsoft.typego.ui.activity.ResultActivity;
import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.data.Language;
import com.tetsoft.typego.testing.ResultListUtils;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.data.TimeMode;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    FragmentAccountBinding _binding;
    @NonNull private FragmentAccountBinding getBinding() { return _binding; }

    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentAccountBinding.inflate(inflater, container, false);
        return getBinding().getRoot();
    }

    User currentUser;
    ArrayList<TypingResult> selectedResults;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = User.getFromStoredData(getContext());
        initLanguageSpinner();
        loadAccountData();
        leaveFeedbackClick();
    }


    private void loadAccountData() {
        View view = getView();
        if (view==null) return;
        setupStatFields();
        initResultSet();
        updateResults();
        loadLastResultsData();
    }

    private void setupStatFields() {
        getBinding().tvAverageWPM.setText(getString(R.string.average_wpm_pl, 0));
        getBinding().tvBestResult.setText(getString(R.string.best_result_pl, 0));
        getBinding().tvAccountLastResult.setText(getString(R.string.previous_result_pl, 0));
        getBinding().tvTestsPassed.setText(getString(R.string.tests_passed_pl, 0));
    }

    public void initLanguageSpinner() {
        ArrayList<LanguageSpinnerItem> languages = new LanguageList().getLocalizedList(getContext());
        // an option to show the whole stats
        LanguageSpinnerItem spinnerItem = new LanguageSpinnerItem(
                new Language(StringKeys.LANGUAGE_ALL),
                getContext().getString(R.string.ALL),
                R.drawable.ic_language);
        languages.add(0, spinnerItem);
        LanguageSpinnerAdapter spinnerAdapter = new LanguageSpinnerAdapter(getContext(), languages);
        getBinding().spinnerResultsLanguageSelection.setAdapter(spinnerAdapter);
        getBinding().spinnerResultsLanguageSelection.setSelection(0);
        getBinding().spinnerResultsLanguageSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadAccountData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initResultSet() {
        Language selectedLanguage = getBinding().spinnerResultsLanguageSelection.getSelectedLanguage();
        if (selectedLanguage.getIdentifier().equalsIgnoreCase(StringKeys.LANGUAGE_ALL))
            selectedResults = currentUser.getResultList();
        else selectedResults = ResultListUtils.getResultsByLanguage(currentUser.getResultList(), selectedLanguage);
    }

    private void updateResults() {
        if (selectedResults == null || selectedResults.isEmpty()) {
            getBinding().tvPassedTestsInfo.setText(getString(R.string.msg_nothing_to_show));
            getBinding().tvAverageWPM.setText(getString(R.string.msg_average_wpm_unavailable));
            return;
        }
        getBinding().tvPassedTestsInfo.setText(getString(R.string.msg_passed_tests_information));
        getBinding().tvTestsPassed.setText(getString(R.string.tests_passed_pl, selectedResults.size()));
        int bestResult = (int)ResultListUtils.getBestResult(selectedResults).getWpm();
        getBinding().tvBestResult.setText(getString(R.string.best_result_pl, bestResult));
        getBinding().tvAccountLastResult.setText(getString(R.string.previous_result_pl, (int)selectedResults.get(0).getWpm()));
        String wpmStr;
        if (selectedResults.size()>=5) {
            double wpmSum = 0;
            for (TypingResult res: selectedResults) wpmSum += res.getWpm();
            wpmStr = getString(R.string.average_wpm) + ": " + (int)(wpmSum/selectedResults.size());
            getBinding().tvAverageWPM.setText(wpmStr);
        } else
            getBinding().tvAverageWPM.setText(getString(R.string.msg_average_wpm_unavailable));
    }

    public void leaveFeedbackClick() {
        getBinding().tvLeaveFeedback.setOnClickListener(new View.OnClickListener() {
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

    public void loadLastResultsData() {
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
        getBinding().rvPassedTests.setAdapter(new PassedTestsAdapter(getContext(), selectedResults,listener));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        getBinding().rvPassedTests.setLayoutManager(linearLayoutManager);

        AnimationManager animationManager = new AnimationManager();
        Animation slideIn = animationManager.getSlideInAnimation(0, 50f, 500);
        Animation fadeIn = animationManager.getFadeInAnimation(500);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(slideIn);
        animationSet.addAnimation(fadeIn);
        getBinding().rvPassedTests.setAnimation(animationSet);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _binding = null;
    }
}