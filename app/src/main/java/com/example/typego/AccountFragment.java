package com.example.typego;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.typego.adapters.PassedTestsAdapter;
import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.TypingResult;
import com.example.typego.utils.User;

import java.text.DecimalFormat;
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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = User.getFromJson(getActivity()
                        .getSharedPreferences(KeyConstants.USER_STORAGE_FILE, Context.MODE_PRIVATE)
                        .getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        if (currentUser != null) loadData(view);



    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (getView()!=null) {
                NestedScrollView nestedScrollView = getView().findViewById(R.id.accountNestedScrollView);
                nestedScrollView.smoothScrollTo(0, 0);
            }
        }
    }

    private void loadData(View view) {

        TextView tvBestResult = view.findViewById(R.id.tvBestResult);
        TextView tvLastResult = view.findViewById(R.id.tvAccountLastResult);
        TextView tvAvgWpm = view.findViewById(R.id.tvAverageWPM);
        TextView tvTestsPassed = view.findViewById(R.id.tvTestsPassed);
        ArrayList<TypingResult> results = currentUser.getResultList();

        tvBestResult.setText(getString(R.string.best_result) + ": " + currentUser.getBestResult());
        tvLastResult.setText(getString(R.string.previous_result) + ": " + currentUser.getLastResult());
        tvAvgWpm.setText(getString(R.string.average_wpm) + ": 0");
        tvTestsPassed.setText(getString(R.string.tests_passed) + ": 0");

        if (results!=null && !results.isEmpty()) {

            tvTestsPassed.setText(getString(R.string.tests_passed) + ": " + results.size());
            String wpmStr = getString(R.string.msg_average_wpm_unavailable);
            if (results.size()>=5) {
                double wpmSum = 0;
                for (TypingResult res: results
                ) {
                    wpmSum += res.getWPM();
                }
                wpmStr = getString(R.string.average_wpm) + ": " + new DecimalFormat("0.#").format(wpmSum/results.size());
            }
            tvAvgWpm.setText(wpmStr);

            RecyclerView rvPassedTests = view.findViewById(R.id.rv_passed_tests);
            PassedTestsAdapter.RecyclerViewOnClickListener listener = (v, position) -> {
                TypingResult resultInfo = currentUser.getResultList().get(position);
                Intent intent = new Intent(getContext(), ResultActivity.class);
                intent.putExtra(KeyConstants.TEST_CORRECT_WORDS, resultInfo.getCorrectWords());
                intent.putExtra(KeyConstants.TEST_CORRECT_WORDS_WEIGHT, resultInfo.getCorrectWordsWeight());
                intent.putExtra(KeyConstants.TEST_AMOUNT_OF_SECONDS, resultInfo.getTimeInSeconds());
                intent.putExtra(KeyConstants.TOTAL_WORDS, resultInfo.getTotalWords());
                intent.putExtra(KeyConstants.TEST_DICTIONARY_TYPE, resultInfo.getDictionaryType());
                intent.putExtra(KeyConstants.TEST_DICTIONARY_LANG, resultInfo.getLanguage());
                intent.putExtra(KeyConstants.TEST_SUGGESTIONS_ON, resultInfo.isTextSuggestions());
                intent.putExtra(KeyConstants.CALLED_FROM_PASSED_RESULTS, true);
                startActivity(intent);
            };
            rvPassedTests.setAdapter(new PassedTestsAdapter(
                    getContext(), currentUser.getResultList(),listener));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvPassedTests.setLayoutManager(linearLayoutManager);
        } else {
            TextView tvNothingToShow = view.findViewById(R.id.tvNothingToShow);
            tvNothingToShow.setVisibility(View.VISIBLE);
        }
    }

}