package com.tetsoft.typego;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.tetsoft.typego.adapters.PassedTestsAdapter;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ResultListUtils;
import com.tetsoft.typego.result.TypingResult;
import com.tetsoft.typego.utils.User;

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
        // Inflate the layout for this fragment
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
        initStatFields(view);
        initResultSet();
        updateResults(view);
        loadLastResultsData(view);
    }

    private void initStatFields(@NonNull View view) {
        TextView tvAvgWpm = view.findViewById(R.id.tvAverageWPM);
        TextView tvBestResult = view.findViewById(R.id.tvBestResult);
        TextView tvLastResult = view.findViewById(R.id.tvAccountLastResult);
        TextView tvTestsPassed = view.findViewById(R.id.tvTestsPassed);

        tvAvgWpm.setText(getString(R.string.average_wpm_pl, 0));
        tvBestResult.setText(getString(R.string.best_result_pl, 0));
        tvLastResult.setText(getString(R.string.previous_result_pl, 0));
        tvTestsPassed.setText(getString(R.string.tests_passed_pl, 0));
    }

    Spinner spinner;
    public void initLanguageSpinner(@NonNull View view) {
        ArrayList<Language> languages = Language.getAvailableLanguages(getContext());
        // an option to show the whole stats
        languages.add(0, new Language(StringKeys.LANGUAGE_ALL, R.string.all, getContext()));
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initResultSet() {
        if (currentUser==null) return;
        Language selectedLanguage = (Language)spinner.getSelectedItem();
        if (selectedLanguage.getIdentifier().equalsIgnoreCase(StringKeys.LANGUAGE_ALL))
            selectedResults = currentUser.getResultList();
        else selectedResults = ResultListUtils.getResultsByLanguage(currentUser.getResultList() ,selectedLanguage);
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
        int bestResult = ResultListUtils.getBestResult(selectedResults);
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
            Intent intent = new Intent(getContext(), ResultActivity.class);
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS, resultInfo.getCorrectWords());
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS_WEIGHT, resultInfo.getCorrectWordsWeight());
            intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, resultInfo.getTimeInSeconds());
            intent.putExtra(StringKeys.TOTAL_WORDS, resultInfo.getTotalWords());
            intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, resultInfo.getDictionaryType());
            intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, resultInfo.getLanguage());
            intent.putExtra(StringKeys.TEST_SCREEN_ORIENTATION, resultInfo.getScreenOrientation());
            intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, resultInfo.isTextSuggestions());
            intent.putExtra(StringKeys.CALLED_FROM_PASSED_RESULTS, true);
            startActivity(intent);
        };
        rvPassedTests.setAdapter(new PassedTestsAdapter(getContext(), selectedResults,listener));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPassedTests.setLayoutManager(linearLayoutManager);
    }
}