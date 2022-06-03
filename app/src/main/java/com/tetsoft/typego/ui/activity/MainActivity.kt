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
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.R
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.utils.StringKeys
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.databinding.ActivityMainBinding
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.ui.custom.withColor
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
        binding!!.buttonBasicTestStart.setOnClickListener { startBasicTest() }
        binding!!.buttonCustomTestStart.setOnClickListener {
            startActivity(Intent(this, TestSetupActivity::class.java))
        }
        binding!!.buttonProfileOpen.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
        binding!!.buttonPreviousTestStart.setOnClickListener {
            startPreviousTest()
        }
        binding!!.buttonReleaseNotesOpen.setOnClickListener {
            startActivity(Intent(this, ReleaseNotesActivity::class.java))
        }
    }

    private fun checkMigrations() {
        val currentUser = User.getFromStoredData(this)
        val resultListStorage = (application as TypeGoApp).resultListStorage
        try {
            if (resultListStorage.isEmpty() && currentUser == null) {
                resultListStorage.store(GameResultList())
            } else if (resultListStorage.isEmpty() && currentUser != null) {
                val migration = TypingResultToGameResultMigration(currentUser.resultList)
                val results = migration.migrate()
                resultListStorage.store(results)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_results_migration_failed),
                Toast.LENGTH_SHORT
            ).show()
            val sharedPreferences = getSharedPreferences(StringKeys.USER_STORAGE, MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
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
            Toast.makeText(
                this,
                getString(R.string.error_achievement_migration_failed),
                Toast.LENGTH_SHORT
            ).show()
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

    private fun startPreviousTest() {
        val gameResultList = GameResultListStorage(this).get()
        if (gameResultList.isNotEmpty()) {
            val intent = Intent(this, TypingTestActivity::class.java)
            val previousSettings = gameResultList[gameResultList.size - 1]?.gameMode
            intent.putExtra(StringKeys.TEST_SETTINGS, previousSettings)
            intent.putExtra(StringKeys.FROM_MAIN_MENU, true)
            startActivity(intent)
            finish()
        }
        else Snackbar.make(binding!!.root, R.string.msg_no_previous_games, Snackbar.LENGTH_LONG)
            .withColor(R.color.main_green, R.color.bg_dark_gray)
            .show()
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