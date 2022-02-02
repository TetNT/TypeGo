package com.tetsoft.typego.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.tetsoft.typego.data.account.UserPreferences
import android.os.Bundle
import com.tetsoft.typego.TypeGoApp
import android.content.Intent
import com.tetsoft.typego.testing.TestSettings
import com.tetsoft.typego.utils.StringKeys
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.LanguageList
import com.tetsoft.typego.data.TimeMode
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var userPreferences: UserPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = (application as TypeGoApp).preferences
        userInit()
        setupViews()
        setupLanguageSpinner()

    }

    private fun setupViews() {
        binding.cardUsualTest.setOnClickListener { startBasicTest() }

        binding.cardCustomizableTest.setOnClickListener {
            startActivity(Intent(this, TestSetupActivity::class.java))
        }

        binding.cardUserAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    fun startBasicTest() {
        val intent = Intent(this, TypingTestActivity::class.java)
        val DEFAULT_TEST_SETTINGS = TestSettings(
            binding.spinnerBasicTestLanguageSelection.getSelectedLanguage(),
            DEFAULT_TIME_MODE,
            DEFAULT_DICTIONARY_TYPE,
            DEFAULT_SUGGESTIONS_IS_ON,
            DEFAULT_SCREEN_ORIENTATION
        )
        intent.putExtra(StringKeys.TEST_SETTINGS, DEFAULT_TEST_SETTINGS)
        intent.putExtra(StringKeys.FROM_MAIN_MENU, true)
        startActivity(intent)
        finish()
    }

    private fun userInit() {
        var currentUser = User.getFromStoredData(this)
        if (currentUser == null) {
            currentUser = User()
        }
        currentUser.initAchievements(this)
        currentUser.storeData(this)
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            this,
            LanguageList().getLocalizedList(this)
        )
        binding.spinnerBasicTestLanguageSelection.adapter = spinnerAdapter
        if (userPreferences!!.language != null) {
            binding.spinnerBasicTestLanguageSelection.setSelection(
                spinnerAdapter.getItemIndexByLanguage(userPreferences!!.language)
            )
        } else {
            binding.spinnerBasicTestLanguageSelection.setSelection(
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