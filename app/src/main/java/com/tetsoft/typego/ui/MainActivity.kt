package com.tetsoft.typego.ui

import androidx.appcompat.app.AppCompatActivity
//import com.tetsoft.typego.data.account.UserPreferences
import android.os.Bundle
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}