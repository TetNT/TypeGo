package com.tetsoft.typego.ui.activity

import com.tetsoft.typego.utils.TimeConvert.convertSeconds
import androidx.appcompat.app.AppCompatActivity
import com.tetsoft.typego.data.account.UserPreferences
import android.os.Bundle
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import com.google.android.material.slider.Slider
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.utils.StringKeys
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeModeList
import com.tetsoft.typego.databinding.ActivityTestSetupBinding

class TestSetupActivity : AppCompatActivity() {

    var userPreferences: UserPreferences? = null

    var binding: ActivityTestSetupBinding? = null

    private val availableTimeModes = TimeModeList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = (application as TypeGoApp).preferences
        binding = ActivityTestSetupBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        var dictionaryChildIndex = 0
        if (userPreferences!!.dictionaryType === DictionaryType.ENHANCED) dictionaryChildIndex = 1
        var screenChildIndex = 0
        if (userPreferences!!.screenOrientation === ScreenOrientation.LANDSCAPE) screenChildIndex =
            1
        (binding!!.rbDictionaryType.getChildAt(dictionaryChildIndex) as RadioButton).isChecked =
            true
        (binding!!.rbScreenOrientation.getChildAt(screenChildIndex) as RadioButton).isChecked = true
        setupButtons()
        setupTimeModeSlider()
        selectCurrentLanguageOption()
        binding!!.cbPredictiveText.isChecked = userPreferences!!.isSuggestionsActivated
    }

    private fun setupButtons() {
        binding!!.tvTestSetup.setOnClickListener { finish() }

        binding!!.buttonStartGame.setOnClickListener {
            val intent = Intent(this, TypingTestActivity::class.java)
            val gameOnTime = GameOnTime(
                binding!!.spinLanguageSelection.getSelectedLanguage(),
                binding!!.timemodeSlider.getSelectedTimeMode(),
                selectedDictionaryType,
                binding!!.cbPredictiveText.isChecked,
                selectedScreenOrientation
            )
            intent.putExtra(StringKeys.TEST_SETTINGS, gameOnTime)
            userPreferences!!.update(gameOnTime)
            finish()
            startActivity(intent)
        }
    }

    private val selectedDictionaryType: DictionaryType
        get() {
            val radioButton =
                findViewById<RadioButton>(binding!!.rbDictionaryType.checkedRadioButtonId)
            val selectedDictionaryIndex = binding!!.rbDictionaryType.indexOfChild(radioButton)
            return if (selectedDictionaryIndex == 0) DictionaryType.BASIC else DictionaryType.ENHANCED
        }

    private val selectedScreenOrientation: ScreenOrientation
        get() {
            val screenOrientation =
                findViewById<RadioButton>(binding!!.rbScreenOrientation.checkedRadioButtonId)
            val selectedScreenOrientation =
                binding!!.rbScreenOrientation.indexOfChild(screenOrientation)
            return if (selectedScreenOrientation == 0) ScreenOrientation.PORTRAIT else ScreenOrientation.LANDSCAPE
        }

    private fun setupTimeModeSlider() {
        binding!!.timemodeSlider.setTimeModes(availableTimeModes)
        binding!!.timemodeSlider.selectTimeMode(userPreferences!!.timeMode)
        binding!!.timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            val position = value.toInt()
            binding!!.tvTimeStamp.text = convertSeconds(
                this@TestSetupActivity,
                availableTimeModes.getTimeModeByIndex(position).timeInSeconds)
            binding!!.tvTimeStamp.startAnimation(
                AnimationUtils.loadAnimation(applicationContext, R.anim.pop_animation)
            )
        })
        val progressInSeconds =
            availableTimeModes.getTimeModeByIndex(binding!!.timemodeSlider.value.toInt()).timeInSeconds
        binding!!.tvTimeStamp.text = convertSeconds(this@TestSetupActivity, progressInSeconds)
    }

    // Selects either user preferred language or system language
    private fun selectCurrentLanguageOption() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            this,
            LanguageList().getTranslatableListInAlphabeticalOrder(this)
        )
        binding!!.spinLanguageSelection.adapter = spinnerAdapter
        if (userPreferences!!.language != null) {
            binding!!.spinLanguageSelection.setSelection(
                spinnerAdapter.getItemIndexByLanguage(userPreferences!!.language)
            )
        } else {
            binding!!.spinLanguageSelection.setSelection(
                spinnerAdapter.getItemIndexBySystemLanguage()
            )
        }
    }
}