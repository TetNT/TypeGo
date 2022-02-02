package com.tetsoft.typego.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.tetsoft.typego.R
import com.google.android.gms.ads.initialization.InitializationStatus
import com.tetsoft.typego.databinding.ActivityAccountBinding
import com.tetsoft.typego.utils.StringKeys

class AccountActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAccountBinding
    private var mAdView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        initAdBanner()

        binding.tvAccountActivityTitle.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        val toAchievementSection: Boolean? =
            intent.extras?.getBoolean(StringKeys.TO_ACHIEVEMENT_SECTION)
        if (toAchievementSection != null && toAchievementSection) {
            try {
                findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_navigation_account_to_achievements)
            } catch (illegalArg: IllegalArgumentException) {
                Toast.makeText(this, "Не удалось перейти в достижения.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initAdBanner() {
        MobileAds.initialize(this) { initializationStatus: InitializationStatus ->
            Log.i("ADB", initializationStatus.adapterStatusMap.toString())
        }
        mAdView = findViewById(R.id.adBanner)
        val adRequest = AdRequest.Builder().build()
        binding.adBanner.loadAd(adRequest)
        binding.adBanner.let {
            it.adListener = object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.i("AdBanner", "Failed: " + loadAdError.message)
                }

                override fun onAdLoaded() {
                    Log.i("AdBanner", "Loaded successfully")
                }
            }
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

}