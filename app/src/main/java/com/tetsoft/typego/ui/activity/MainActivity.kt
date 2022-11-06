package com.tetsoft.typego.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.tetsoft.typego.data.account.UserPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.game.result.migration.TypingResultToGameResultMigration
import com.tetsoft.typego.data.achievement.AchievementMigration
import com.tetsoft.typego.data.achievement.AchievementList
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.tetsoft.typego.R
import com.tetsoft.typego.utils.StringKeys
import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    var userPreferences: UserPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        userPreferences = (application as TypeGoApp).preferences
        setContentView(binding!!.root)
    }
}