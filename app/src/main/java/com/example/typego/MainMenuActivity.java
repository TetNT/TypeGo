package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        DatabaseManager db = new DatabaseManager(this);

    }

    public void startTesting_OnClick(View v) {
        Intent intent = new Intent (MainMenuActivity.this, TestSetupActivity.class);
        startActivity(intent);
    }
}