package com.tetsoft.typego.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.AnimationManager
import com.tetsoft.typego.R
import com.tetsoft.typego.achievement.Achievement
import com.tetsoft.typego.account.User
import com.tetsoft.typego.databinding.ActivityResultBinding
import com.tetsoft.typego.testing.*
import com.tetsoft.typego.ui.main.MainActivity
import com.tetsoft.typego.utils.*
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private var currentUser: User? = null
    private var wpm = 0
    private var correctWords = 0
    private var correctWordsWeight = 0
    private var bestResultByLanguage = 0
    private var result: TypingResult? = null
    private var totalWords = 0
    private var calledFromResultsTab = false
    private var calledFromMainMenu = false
    private var typedWordsList: ArrayList<Word>? = null
    private lateinit var testSettings: TestSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        currentUser = User.getFromStoredData(this)
        testSettings = intent.extras?.getSerializable(StringKeys.TEST_SETTINGS) as TestSettings
        initIntentData()
        initPreviousResult()
        if (!calledFromResultsTab) temporarilyDisableButtons()
        initBestResult()
        initTypedWords()
        if (calledFromResultsTab) changeVisibilityFromResultsTab()
        val textSuggestions = if (testSettings.isSuggestionsActivated) getString(R.string.yes) else getString(R.string.no)
        val dictionaryName = if (testSettings.dictionaryType == DictionaryType.BASIC) getString(R.string.basic) else getString(R.string.enhanced)
        val orientation = if (testSettings.screenOrientation == ScreenOrientation.PORTRAIT) getString(R.string.vertical) else getString(R.string.horizontal)
        result = currentResult
        wpm = result!!.wpm.toInt()
        val animationManager = AnimationManager()
        val countAnimation = animationManager.getCountAnimation(0, wpm, 1000)
        animationManager.applyCountAnimation(
                countAnimation,
                binding.tvWPM)
        countAnimation.start()
        binding.tvWPM.text = getString(R.string.results_wpm_pl, wpm)
        binding.tvIncorrectWords.text = getString(R.string.incorrect_words_pl, result!!.incorrectWords)
        binding.tvCorrectWords.text = getString(R.string.correct_words_pl, correctWords)
        binding.tvCorrectChars.text = getString(R.string.correct_chars_pl, correctWordsWeight)
        binding.tvDictionary.text = getString(R.string.dictionary_pl, dictionaryName)
        binding.tvAllottedTime.text = getString(R.string.selected_time_pl, TimeConvert.convertSeconds(this, testSettings.timeMode.timeInSeconds))
        binding.tvLanguage.text = getString(R.string.selected_language_pl, testSettings.language.getName(this))
        binding.tvTextSuggestions.text = getString(R.string.text_suggestions_pl, textSuggestions)
        binding.tvScreenOrientation.text = getString(R.string.screen_orientation_pl, orientation)
        if (wpm <= 0 && !calledFromResultsTab)
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT).show()
        else if (wpm > 0 && !calledFromResultsTab) saveResultData()
    }

    private fun initTypedWords() {
        if (calledFromResultsTab) {
            binding.tvTypedWordsDescription.text = getString(R.string.typed_words_log_disabled)
            return
        }
        val rvWords = findViewById<RecyclerView>(R.id.rvTypedWords)
        val words: List<Word>? = typedWordsList
        rvWords.adapter = WordsAdapter(words!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvWords.layoutManager = layoutManager
    }

    private fun initPreviousResult() {
        val prevResultByLang = currentUser!!.getLastResultByLanguage(testSettings.language)
        binding.tvPreviousResult.text = (getString(R.string.previous_result_pl, prevResultByLang))
        if (prevResultByLang == 0)
            binding.tvPreviousResult.visibility = View.GONE
        else binding.tvPreviousResult.visibility = View.VISIBLE
    }

    private fun initBestResult() {
        bestResultByLanguage = currentUser!!.getBestResultByLanguage(testSettings.language)
        binding.tvBestResult.text = (getString(R.string.best_result_pl, bestResultByLanguage))
        if (bestResultByLanguage == 0) binding.tvBestResult.visibility = View.GONE
        else binding.tvBestResult.visibility = View.VISIBLE
    }

    private fun changeVisibilityFromResultsTab() {
        binding.bStartOver.visibility = View.GONE
        binding.tvPreviousResult.visibility = View.GONE
        binding.bFinish.text = getString(R.string.close)
    }

    private fun initIntentData() {
        val arguments = intent.extras
        correctWords = arguments!!.getInt(StringKeys.TEST_CORRECT_WORDS)
        correctWordsWeight = arguments.getInt(StringKeys.TEST_CORRECT_WORDS_WEIGHT)
        totalWords = arguments.getInt(StringKeys.TOTAL_WORDS)
        calledFromResultsTab = arguments.getBoolean(StringKeys.CALLED_FROM_PASSED_RESULTS)
        calledFromMainMenu = arguments.getBoolean(StringKeys.FROM_MAIN_MENU)
        typedWordsList = arguments.getSerializable(StringKeys.TEST_TYPED_WORDS_LIST) as ArrayList<Word>?
    }

    private fun saveResultData() {
        if (currentUser == null) {
            Toast.makeText(this, getString(R.string.saving_results_error_occurred), Toast.LENGTH_SHORT).show()
            return
        }
        if (currentUser!!.bestResult < wpm) {
            currentUser!!.bestResult = wpm
        }
        if (wpm > bestResultByLanguage) {
            binding.tvNewBestResult.visibility = View.VISIBLE
        }
        currentUser!!.addResult(result!!)
        checkAchievements()
        currentUser!!.storeData(this)
    }

    private fun checkAchievements() {
        var newAchievements = 0
        var notificationAchievement: Achievement? = null
        for (achievement in currentUser!!.achievements) {
            if (achievement.completionDate == null && achievement.requirementsAreComplete(currentUser, result)) {
                achievement.completionDate = Calendar.getInstance().time
                newAchievements++
                notificationAchievement = achievement
            }
        }
        if (newAchievements == 1) {
            Snackbar.make(findViewById(
                    R.id.result_constraint_layout),
                    getString(R.string.achievement_unlocked, notificationAchievement!!.name),
                    Snackbar.LENGTH_LONG).show()
        } else if (newAchievements > 1)
            Snackbar.make(findViewById(
                R.id.result_constraint_layout),
                getString(R.string.new_achievements_notification, newAchievements),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.check_profile) {
                    startActivity(Intent(this, AccountActivity::class.java))
                    finish()
            }.show()
    }

    private val currentResult: TypingResult
        get() = TypingResult(
                correctWords,
                correctWordsWeight,
                totalWords,
                testSettings.dictionaryType,
                testSettings.screenOrientation,
                testSettings.language,
                testSettings.timeMode,
                testSettings.isSuggestionsActivated,
                Calendar.getInstance().time)

    fun saveAndContinue(view: View?) {
        finish()
        var intent: Intent? = null
        if (calledFromMainMenu) intent = Intent(this, MainActivity::class.java)
        else if (!calledFromResultsTab) intent = Intent(this, TestSetupActivity::class.java)
        intent?.let { startActivity(it) }
    }


    private fun temporarilyDisableButtons() {
        binding.bFinish.isEnabled = false
        binding.bStartOver.isEnabled = false
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            binding.bFinish.isEnabled = true
            binding.bStartOver.isEnabled = true
        }
        handler.postDelayed(runnable, 2000)
    }

    fun startOver(v: View?) {
        val intent = Intent(this@ResultActivity, TypingTestActivity::class.java)
        intent.putExtra(StringKeys.TEST_SETTINGS, testSettings)
        finish()
        startActivity(intent)
    }
}