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
import com.tetsoft.typego.TypeGoApp;
import com.tetsoft.typego.adapter.GamesHistoryAdapter;
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter;
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem;
import com.tetsoft.typego.data.LanguageList;
import com.tetsoft.typego.game.mode.GameOnTime;
import com.tetsoft.typego.game.result.GameResult;
import com.tetsoft.typego.game.result.GameResultList;
import com.tetsoft.typego.databinding.FragmentAccountBinding;
import com.tetsoft.typego.storage.GameResultListStorage;
import com.tetsoft.typego.ui.activity.ResultActivity;
import com.tetsoft.typego.utils.AnimationManager;
import com.tetsoft.typego.R;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.data.Language;
import java.util.ArrayList;


public class AccountFragment extends Fragment {

    FragmentAccountBinding _binding;

    @NonNull
    private FragmentAccountBinding getBinding() {
        return _binding;
    }

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

    GameResultList resultList;
    GameResultListStorage resultListStorage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            resultListStorage = ((TypeGoApp) (getActivity().getApplication())).getResultListStorage();
            resultList = getResultListDescending();
        }
        initLanguageSpinner();
        loadAccountData();
        leaveFeedbackClick();
    }


    private void loadAccountData() {
        View view = getView();
        if (view == null) return;
        setupStatFields();
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
        ArrayList<LanguageSpinnerItem> languages = new LanguageList().getTranslatableListInAlphabeticalOrder(requireContext());
        // an option to show the whole stats
        LanguageSpinnerItem spinnerItem = new LanguageSpinnerItem(
                new Language(StringKeys.LANGUAGE_ALL),
                requireContext().getString(R.string.ALL),
                R.drawable.ic_language);
        languages.add(0, spinnerItem);
        LanguageSpinnerAdapter spinnerAdapter = new LanguageSpinnerAdapter(requireContext(), languages);
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

    private GameResultList getResultListDescending() {
        GameResultList results = resultListStorage.get();
        if (results == null) return null;
        return results.getInDescendingOrder();
    }

    private void updateResults() {
        if (resultList == null || getPickedResults().isEmpty()) {
            getBinding().tvPassedTestsInfo.setText(getString(R.string.msg_nothing_to_show));
            return;
        }
        getBinding().tvPassedTestsInfo.setText(getString(R.string.msg_passed_tests_information));
        getBinding().tvTestsPassed.setText(getString(R.string.tests_passed_pl, resultList.size()));
        int bestResult = resultList.getBestResultWpm();
        getBinding().tvBestResult.setText(getString(R.string.best_result_pl, bestResult));
        getBinding().tvAccountLastResult.setText(getString(R.string.previous_result_pl, (int) resultList.get(0).getWpm()));
        String wpmStr;
        if (resultList.size() >= 5) {
            double wpmSum = 0;
            for (GameResult res : resultList) wpmSum += res.getWpm();
            wpmStr = getString(R.string.average_wpm) + ": " + (int) (wpmSum / resultList.size());
            getBinding().tvAverageWPM.setText(wpmStr);
        } else
            getBinding().tvAverageWPM.setText(getString(R.string.msg_average_wpm_unavailable));
    }

    public void leaveFeedbackClick() {
        getBinding().tvLeaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = requireContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }

    private GameResultList getPickedResults() {
        Language selectedLanguage = getBinding().spinnerResultsLanguageSelection.getSelectedLanguage();
        return resultList.getResultsByLanguage(selectedLanguage);
    }

    public void loadLastResultsData() {
        GamesHistoryAdapter.RecyclerViewOnClickListener listener = (v, position) -> {
            GameResult resultInfo = resultList.get(position);
            Intent intent = new Intent(getContext(), ResultActivity.class);
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS, resultInfo.getCorrectWords());
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS_WEIGHT, resultInfo.getScore());
            intent.putExtra(StringKeys.TOTAL_WORDS, resultInfo.getWordsWritten());
            intent.putExtra(StringKeys.TEST_SETTINGS, (GameOnTime) resultInfo.getGameMode());
            intent.putExtra(StringKeys.CALLED_FROM_PASSED_RESULTS, true);
            startActivity(intent);
        };
        getBinding().rvPassedTests.setAdapter(new GamesHistoryAdapter(getContext(), getPickedResults(), listener));
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