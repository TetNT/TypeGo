package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        TextView tvCorrectWords = findViewById((R.id.tvCorrectWords));
        TextView tvIncorrectWords = findViewById(R.id.tvIncorrectWords);
        TextView tvDictionary = findViewById(R.id.tvDictionary);
        TextView tvAllottedTime = findViewById(R.id.tvAllottedTime);
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
        }



    }

    private void SaveResultData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userTable = database.getReference("users");


        String userid = userTable.getKey();
        currentUser = new User(userid, "Tet", "123");
        currentUser.setLastResult((int)wpm);
        if (currentUser.getBestResult()>currentUser.getLastResult()) currentUser.setBestResult(currentUser.getLastResult());
        TypingResult result = new TypingResult(wpm, dictionaryType, "ru", timeInSeconds, Calendar.getInstance().getTime());
        DatabaseReference userInfo = database.getReference(currentUser.getUserName());
        DatabaseReference userResult = userInfo.child("results");
        userInfo.setValue(currentUser);
        //Map<String, TypingResult> resultMap = new HashMap<>();
        //resultMap.put(result.getCompletionDateTime().toString(), result);
        userResult.push().setValue(result);
        userInfo.child("lastResult").setValue(currentUser.getLastResult());
        userInfo.child("bestResult").setValue(currentUser.getBestResult());
    }

    public void SaveAndContinue(View view){
        //Intent intent = new Intent(ResultActivity.this, TestSetupActivity.class);
        finish();
        //startActivity(intent);


    }
}