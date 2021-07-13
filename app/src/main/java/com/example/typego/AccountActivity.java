package com.example.typego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import com.example.typego.utils.StringKeys;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AccountActivity extends AppCompatActivity {
    Fragment achievementsFragment;
    Fragment accountFragment;
    FragmentManager fm;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initFragmentManager();
        initBottomNavigation();
        initAdBanner();
    }



    private void initFragmentManager() {
        fm = getSupportFragmentManager();
        achievementsFragment = new AchievementsFragment();
        accountFragment = new AccountFragment();

        fm.beginTransaction().add(R.id.fragmentFrame, accountFragment, StringKeys.FRAGMENT_ACCOUNT).commit();
        fm.beginTransaction().add(R.id.fragmentFrame, achievementsFragment, StringKeys.FRAGMENT_TEST_SETUP).commit();
        fm.beginTransaction().hide(achievementsFragment).commit();
    }

    private void initBottomNavigation() {
        if (fm ==null) throw new NullPointerException("Fragment manager is not initialized");
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
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

    private void initAdBanner() {
        MobileAds.initialize(this, initializationStatus ->
                Log.i("AD", initializationStatus.toString()));
        mAdView = findViewById(R.id.adBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.i("AD", "Failed: "+loadAdError.getMessage());
            }
        });
    }




}