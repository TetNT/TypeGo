package com.example.typego;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        currentUser = User.getFromJson(getActivity().getSharedPreferences(KeyConstants.USER_STORAGE_FILE, Context.MODE_PRIVATE).getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        if (currentUser != null) loadData(view);
    }

    private void loadData(View view) {

        TextView tvBestResult = view.findViewById(R.id.tvBestResult);
        TextView tvLastResult = view.findViewById(R.id.tvAccountLastResult);
        TextView tvAvgWpm = view.findViewById(R.id.tvAverageWPM);
        TextView tvTestsPassed = view.findViewById(R.id.tvTestsPassed);
        ArrayList<TypingResult> results = currentUser.getResultList();

        tvBestResult.setText(getString(R.string.Best_result) + ": " + currentUser.getBestResult());
        tvLastResult.setText(getString(R.string.Previous_result) + ": " + currentUser.getLastResult());

        if (results == null || results.isEmpty()) return;
        tvTestsPassed.setText(getString(R.string.tests_passed) + ": " + results.size());
        String wpmStr = "Average WPM will be available after you pass 5 tests";
        if (results.size()>=5) {
            double wpmSum = 0;
            for (TypingResult res: results
            ) {
                wpmSum += res.getWPM();
            }
            wpmStr = getString(R.string.average_wpm) + ": " + new DecimalFormat("0.#").format(wpmSum/results.size());
        }
        tvAvgWpm.setText(wpmStr);
    }

}