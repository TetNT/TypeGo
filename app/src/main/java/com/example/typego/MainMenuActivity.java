package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toast.makeText(this, Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_LONG).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userInfo = database.getReference("users");


    }

    public void startTesting_OnClick(View v) {
        Intent intent = new Intent (MainMenuActivity.this, TestSetupActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}