package com.tetsoft.typego

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.tetsoft.typego.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val consentInformation = UserMessagingPlatform.getConsentInformation(this)
        consentInformation.requestConsentInfoUpdate(
            this,
            ConsentRequestParameters.Builder().build(),
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) { formError ->
                    if (formError != null) {
                        Log.d("CONSENT", "Form error. " + formError.message)
                    }

                    if (consentInformation.canRequestAds()) {
                        (application as TypeGoApp).canRequestAds.set(true)
                        MobileAds.initialize(this) {}
                    }
                }
            },
            { formError ->
                Log.e("CONSENT", "Failure. " + formError.message)
            }
        )
        if (consentInformation.canRequestAds()) {
            (application as TypeGoApp).canRequestAds.set(true)
            MobileAds.initialize(this) {}
        }
    }
}