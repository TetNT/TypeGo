package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class TestSetupActivity extends AppCompatActivity {

    int progressInSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        SeekBar sb = (SeekBar)findViewById(R.id.seekBar);
        TextView seekbarDisplay = (TextView)findViewById(R.id.tvSeekBarDisplay);
        progressInSeconds = getSelectedSecondsOption(sb.getProgress());
        seekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressInSeconds = getSelectedSecondsOption(progress);
                seekbarDisplay.setText(TimeConvert.convertSeconds(TestSetupActivity.this, progressInSeconds));
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
        RadioGroup rbGroup = (RadioGroup)findViewById(R.id.rbGroup);
        RadioButton radioButton = findViewById(rbGroup.getCheckedRadioButtonId());
        int selectedDictionaryIndex = rbGroup.indexOfChild(radioButton);
        //TODO: Choose dictionary language by system's settings

        Intent intent = new Intent(this, TypingTestActivity.class);
        intent.putExtra("AmountOfSeconds", progressInSeconds);
        intent.putExtra("DictionaryType", selectedDictionaryIndex);
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
}