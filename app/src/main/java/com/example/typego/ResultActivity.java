package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typego.data.Result;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    User currentUser;
    double wpm;
    int correctWords;
    int timeInSeconds;
    int dictionaryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        currentUser = (User)getIntent().getSerializableExtra("currentUser");
        TextView tvWPM = findViewById(R.id.tvWPM);
        TextView tvPreviousResult = findViewById(R.id.tvPreviousResult);
        TextView tvBestResult = findViewById(R.id.tvBestResult);
        TextView tvCorrectWords = findViewById((R.id.tvCorrectWords));
        TextView tvIncorrectWords = findViewById(R.id.tvIncorrectWords);
        TextView tvDictionary = findViewById(R.id.tvDictionary);
        TextView tvAllottedTime = findViewById(R.id.tvAllottedTime);

        tvPreviousResult.setText(getString(R.string.Previous_result) + ": " + currentUser.getLastResult());
        if (currentUser.getLastResult() == 0)
            tvPreviousResult.setVisibility(View.INVISIBLE);
        else tvPreviousResult.setVisibility(View.VISIBLE);

        tvBestResult.setText(getString(R.string.Best_result) + ": " + currentUser.getBestResult());
        if (currentUser.getBestResult()==0)
            tvBestResult.setVisibility(View.INVISIBLE);
        else tvBestResult.setVisibility(View.VISIBLE);

        Bundle arguments = getIntent().getExtras();
        try {
            correctWords = Integer.parseInt(arguments.get("correctWords").toString());
            timeInSeconds = Integer.parseInt(arguments.get("timeInSeconds").toString());
            int totalWords = Integer.parseInt(arguments.get("totalWords").toString());
            dictionaryType = arguments.getInt("DictionaryType");
            String DictionaryName;
            if (dictionaryType == 0) DictionaryName = getString(R.string.Basic);
            else DictionaryName = getString(R.string.Enhanced);
            wpm = (60.0/timeInSeconds) * correctWords;
            tvWPM.setText(wpm + " "+ getString(R.string.WordsPerMinute));
            tvIncorrectWords.setText(getString(R.string.Incorrect_words)+ ": " + (totalWords-correctWords));
            tvCorrectWords.setText(getString(R.string.Correct_words) + ": " + correctWords);
            tvDictionary.setText(getString(R.string.Dictionary)+": " + DictionaryName);
            tvAllottedTime.setText(getString(R.string.Allotted_time) + ": " + TimeConvert.convertSeconds(this, timeInSeconds));
            if (wpm>0) SaveResultData();
            else Toast.makeText(this, "Результаты с 0 слов в минуту не записываются", Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("ResEx", e.getMessage());
        }



    }

    private void SaveResultData() {
        if (currentUser == null) {
            Toast.makeText(this, "Произошла ошибка при сохранении результатов", Toast.LENGTH_SHORT).show();
            return;
        }
        currentUser.setLastResult((int)wpm);
        if (currentUser.getBestResult() < currentUser.getLastResult()) {
            currentUser.setBestResult(currentUser.getLastResult());
        }
        if (currentUser.getUserName().equals("Guest")) {
            Toast.makeText(this, "Авторизуйтесь для сохранения результатов", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userTable = database.getReference(currentUser.getUserName());


        TypingResult result = new TypingResult(wpm, dictionaryType, "ru", timeInSeconds, Calendar.getInstance().getTime());
        currentUser.addResult(result);
        DatabaseReference userInfo = database.getReference(currentUser.getUserName());
        userInfo.setValue(currentUser);
        DatabaseReference userResults = userInfo.child("results");
        userResults.child(Calendar.getInstance().getTime().toString()).setValue(result);
        //userResult.push().setValue(result);
        userInfo.child("lastResult").setValue(currentUser.getLastResult());
        userInfo.child("bestResult").setValue(currentUser.getBestResult());
    }

    public void SaveAndContinue(View view){
        finish();
    }
}