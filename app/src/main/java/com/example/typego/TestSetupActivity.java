package com.example.typego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TestSetupActivity extends AppCompatActivity {
    SeekBar sb;
    RadioGroup rbGroup;
    RadioButton radioButton;
    int progressInSeconds;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        if (savedInstanceState == null) Log.d("State", "New");
        else Log.d("State", "Reopened");
        sb = findViewById(R.id.seekBar);
        TextView tvSeekbarDisplay = findViewById(R.id.tvSeekBarDisplay);
        progressInSeconds = getSelectedSecondsOption(sb.getProgress());
        tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
        currentUser = (User)getIntent().getSerializableExtra("currentUser");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressInSeconds = getSelectedSecondsOption(progress);
                tvSeekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void startTesting(View view) {
        rbGroup = findViewById(R.id.rbGroup);
        radioButton = findViewById(rbGroup.getCheckedRadioButtonId());
        int selectedDictionaryIndex = rbGroup.indexOfChild(radioButton);
        //TODO: Choose dictionary language by system's settings

        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra("AmountOfSeconds", progressInSeconds);
        intent.putExtra("DictionaryType", selectedDictionaryIndex);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    private int getSelectedSecondsOption(int progress) {
        int progressToSeconds;
        if (progress<4)
            progressToSeconds = (progress+1)*15;
        else if (progress==4) progressToSeconds = 90;
        else if (progress==5) progressToSeconds = 120;
        else if (progress==6) progressToSeconds = 180;
        else progressToSeconds = 300;
        return progressToSeconds;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seekBarState", sb.getProgress());
        outState.putInt("radioButtonState", rbGroup.indexOfChild(radioButton));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int m = 1;
    }
}