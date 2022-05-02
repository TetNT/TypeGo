package com.tetsoft.typego.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.tetsoft.typego.data.account.UserPreferences
import android.os.Bundle
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.game.result.migration.TypingResultToGameResultMigration
import com.tetsoft.typego.data.achievement.AchievementMigration
import com.tetsoft.typego.data.achievement.AchievementList
import android.content.Intent
import android.util.Log
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.utils.StringKeys
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.LanguageList
import com.tetsoft.typego.data.TimeMode
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var userPreferences: UserPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        userPreferences = (application as TypeGoApp).preferences
        setContentView(binding!!.root)
        checkMigrations()
        setupLanguageSpinner()
        setupButtonsOnClickListeners()
    }

    private fun setupButtonsOnClickListeners() {
        binding!!.cardBasicTest.setOnClickListener { startBasicTest() }
        binding!!.cardCustomizableTest.setOnClickListener { openTestCustomization() }
        binding!!.cardUserAccount.setOnClickListener { openUserAccount() }
    }

    private fun checkMigrations() {
        val currentUser = User.getFromStoredData(this)
        val resultListStorage = (application as TypeGoApp).resultListStorage
        try {
            if (resultListStorage.isEmpty() && currentUser == null) {
                Log.i("MIGR", "checkMigrations: first if statement")
                resultListStorage.store(GameResultList())
            } else if (resultListStorage.isEmpty() && currentUser != null) {
                Log.i("MIGR", "checkMigrations: second if statement")
                val migration = TypingResultToGameResultMigration(currentUser.resultList)
                val results = migration.migrate()
                resultListStorage.store(results)
            }
        } catch (e: Exception) {
            Log.e(
                "MIGR",
                "checkMigrations failed to migrate to the new GameResultList: " + e.message
            )
        }
        try {
            val achievementStorage = (application as TypeGoApp).achievementsProgress
            if (achievementStorage.isEmpty() && currentUser == null) {
                val achievementMigration = AchievementMigration(AchievementList(this).get())
                achievementMigration.storeProgress(achievementStorage)
            } else if (achievementStorage.isEmpty() && currentUser != null) {
                val achievementMigration = AchievementMigration(currentUser.achievements)
                achievementMigration.storeProgress(achievementStorage)
            }
        } catch (e: Exception) {
            Log.e(
                "MIGR",
                "checkMigrations failed to migrate to the new achievements progress:" + e.message
            )
        }
    }

    private fun startBasicTest() {
        val intent = Intent(this, TypingTestActivity::class.java)
        val basicGameMode = GameOnTime(
            binding!!.spinnerBasicTestLanguageSelection.getSelectedLanguage(),
            DEFAULT_TIME_MODE,
            DEFAULT_DICTIONARY_TYPE,
            DEFAULT_SUGGESTIONS_IS_ON,
            DEFAULT_SCREEN_ORIENTATION
        )
        intent.putExtra(StringKeys.TEST_SETTINGS, basicGameMode)
        intent.putExtra(StringKeys.FROM_MAIN_MENU, true)
        startActivity(intent)
        finish()
    }

    private fun openTestCustomization() {
        startActivity(Intent(this, TestSetupActivity::class.java))
    }

    private fun openUserAccount() {
        startActivity(Intent(this, AccountActivity::class.java))
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            this,
            LanguageList().getTranslatableListInAlphabeticalOrder(this)
        )
        binding!!.spinnerBasicTestLanguageSelection.adapter = spinnerAdapter
        if (userPreferences!!.language != null) {
            binding!!.spinnerBasicTestLanguageSelection.setSelection(
                spinnerAdapter.getItemIndexByLanguage(userPreferences!!.language)
            )
        } else {
            binding!!.spinnerBasicTestLanguageSelection.setSelection(
                spinnerAdapter.getItemIndexBySystemLanguage()
            )
        }
    }

    companion object {
        val DEFAULT_TIME_MODE = TimeMode(60)
        val DEFAULT_DICTIONARY_TYPE = DictionaryType.BASIC
        const val DEFAULT_SUGGESTIONS_IS_ON = true
        val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
    }
}