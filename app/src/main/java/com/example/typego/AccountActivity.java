package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.typego.utils.KeyConstants;
import com.example.typego.utils.TypingResult;
import com.example.typego.utils.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        currentUser = User.getFromJson(getSharedPreferences(KeyConstants.USER_STORAGE_FILE, MODE_PRIVATE).getString(KeyConstants.PREFERENCES_CURRENT_USER, null));
        if (currentUser!=null) loadData();
    }

    private void loadData() {

        TextView tvBestResult = findViewById(R.id.tvBestResult);
        TextView tvLastResult = findViewById(R.id.tvAccountLastResult);
        TextView tvAvgWpm = findViewById(R.id.tvAverageWPM);
        TextView tvTestsPassed = findViewById(R.id.tvTestsPassed);
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