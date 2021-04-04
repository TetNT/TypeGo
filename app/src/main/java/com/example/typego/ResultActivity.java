package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typego.data.Result;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tvWPM = findViewById(R.id.tvWPM);
        TextView tvCorrectWords = findViewById((R.id.tvCorrectWords));
        TextView tvIncorrectWords = findViewById(R.id.tvIncorrectWords);
        TextView tvDictionary = findViewById(R.id.tvDictionary);
        Bundle arguments = getIntent().getExtras();
        try {
            int correctWords = Integer.parseInt(arguments.get("correctWords").toString());
            int timeInSeconds = Integer.parseInt(arguments.get("timeInSeconds").toString());
            int totalWords = Integer.parseInt(arguments.get("totalWords").toString());
            int DictionaryType = arguments.getInt("DictionaryType");
            String DictionaryName;
            if (DictionaryType == 0) DictionaryName = getString(R.string.Basic);
            else DictionaryName = getString(R.string.Enhanced);
            double wpm = (60.0/timeInSeconds) * correctWords;
            tvWPM.setText(wpm + " "+ getString(R.string.WordsPerMinute));
            tvIncorrectWords.setText(getString(R.string.Incorrect_words)+ ": " + (totalWords-correctWords));
            tvCorrectWords.setText(getString(R.string.Correct_words) + ": " + correctWords);
            tvDictionary.setText("Словарь: " + DictionaryName);

        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void SaveAndContinue(View view){
        //Intent intent = new Intent(ResultActivity.this, TestSetupActivity.class);
        finish();
        //startActivity(intent);


    }
}