package com.tetsoft.typego.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.tetsoft.typego.R
import com.google.android.gms.ads.initialization.InitializationStatus
import com.tetsoft.typego.databinding.ActivityAccountBinding
import com.tetsoft.typego.utils.StringKeys

class AccountActivity : AppCompatActivity() {
    lateinit var binding : ActivityAccountBinding
    var mAdView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        initAdBanner()
    }

    override fun onStart() {
        super.onStart()
        val toAchievementSection : Boolean? = intent.extras?.getBoolean(StringKeys.TO_ACHIEVEMENT_SECTION)
        if (toAchievementSection != null && toAchievementSection) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_navigation_account_to_achievements)

        }
    }

    private fun initBottomNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)
    }

    private fun initAdBanner() {
        MobileAds.initialize(this) { initializationStatus: InitializationStatus ->
            Log.i("ADB", initializationStatus.adapterStatusMap.toString())
        }
        mAdView = findViewById(R.id.adBanner)
        val adRequest = AdRequest.Builder().build()
        mAdView.let { it!!.loadAd(adRequest) }
        mAdView.let {
            it!!.setAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.i("ADB", "Failed: " + loadAdError.message)
                }

                override fun onAdLoaded() {
                    Log.i("ADB", "Loaded successfully")
                }
            })
        }
    }

    override fun onDestroy() {
        if (mAdView != null) mAdView!!.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (mAdView != null) mAdView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        if (mAdView != null) mAdView!!.pause()
    }

    fun closeActivity(view: View?) {
        finish()
    }
}