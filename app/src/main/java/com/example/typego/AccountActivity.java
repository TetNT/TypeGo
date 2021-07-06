package com.example.typego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.typego.utils.StringKeys;
import com.example.typego.utils.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AccountActivity extends AppCompatActivity {
    User currentUser;
    Fragment achievementsFragment;
    Fragment accountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button buttonStart = findViewById(R.id.bMainMenuStartTest);
        buttonStart.setEnabled(false);
        currentUser = User.getFromStoredData(this);
        currentUser.storeData(this);
        buttonStart.setEnabled(true);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);

        FragmentManager fm = getSupportFragmentManager();
        achievementsFragment = new AchievementsFragment();
        accountFragment = new AccountFragment();

        fm.beginTransaction().add(R.id.fragmentFrame, accountFragment, StringKeys.FRAGMENT_ACCOUNT).commit();
        fm.beginTransaction().add(R.id.fragmentFrame, achievementsFragment, StringKeys.FRAGMENT_TEST_SETUP).commit();
        fm.beginTransaction().hide(achievementsFragment).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int selectedItem = item.getItemId();
            if (selectedItem == R.id.achievements_menu_item) {
                fm.beginTransaction().show(achievementsFragment).commit();
                fm.beginTransaction().hide(accountFragment).commit();
            } else if (selectedItem == R.id.account_menu_item) {
                fm.beginTransaction().show(accountFragment).commit();
                fm.beginTransaction().hide(achievementsFragment).commit();
            }
            return true;
        });
    }



    public void startTesting_OnClick(View v) {
        if (currentUser == null) {
            throw new NullPointerException("User is not initialized.");
        }
        Intent intent = new Intent (AccountActivity.this, TestSetupActivity.class);
        startActivity(intent);
    }


}