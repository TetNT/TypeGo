package com.tetsoft.typego.ui.activity

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
import com.tetsoft.typego.utils.AnimationManager
import com.tetsoft.typego.R
import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.adapter.WordsAdapter
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.data.achievement.AchievementList
import com.tetsoft.typego.databinding.ActivityResultBinding
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.utils.*
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var wpm = 0
    private var correctWords = 0
    private var correctWordsWeight = 0
    private var bestResultByLanguage = 0
    private lateinit var achievementsStorage : AchievementsProgressStorage
    private lateinit var result: GameResult
    private lateinit var resultsStorage : GameResultListStorage
    private var totalWords = 0
    private var calledFromResultsTab = false
    private var calledFromMainMenu = false
    private var typedWordsList: ArrayList<Word>? = null
    private lateinit var gameMode: GameOnTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        achievementsStorage = AchievementsProgressStorage(this)
        resultsStorage = GameResultListStorage(this)
        gameMode = intent.extras?.getSerializable(StringKeys.TEST_SETTINGS) as GameOnTime
        initIntentData()
        initPreviousResult()
        if (!calledFromResultsTab) temporarilyDisableButtons()
        initBestResult()
        initTypedWords()
        if (calledFromResultsTab) changeVisibilityFromResultsTab()

        result = GameResult(
            gameMode,
            correctWordsWeight,
            gameMode.timeMode.timeInSeconds,
            totalWords,
            correctWords,
            Calendar.getInstance().time.time
        )

        val textSuggestions =
            if (gameMode.suggestionsActivated) getString(R.string.yes) else getString(R.string.no)
        val dictionaryName =
            if (gameMode.dictionaryType == DictionaryType.BASIC) getString(R.string.basic) else getString(
                R.string.enhanced
            )
        val orientation =
            if (gameMode.screenOrientation == ScreenOrientation.PORTRAIT) getString(R.string.vertical) else getString(
                R.string.horizontal
            )
        wpm = result.wpm.toInt()

        val animationManager = AnimationManager()
        val countAnimation = animationManager.getCountAnimation(0, wpm, 1000)
        animationManager.applyCountAnimation(
            countAnimation,
            binding.tvWPM
        )
        countAnimation.start()
        binding.tvWPM.text = getString(R.string.results_wpm_pl, wpm)
        binding.tvIncorrectWords.text =
            getString(R.string.incorrect_words_pl, result.wordsWritten - result.correctWords)
        binding.tvCorrectWords.text = getString(R.string.correct_words_pl, correctWords)
        binding.tvCorrectChars.text = getString(R.string.correct_chars_pl, correctWordsWeight)
        binding.tvDictionary.text = getString(R.string.dictionary_pl, dictionaryName)
        binding.tvAllottedTime.text = getString(
            R.string.selected_time_pl,
            TimeConvert.convertSeconds(this, gameMode.timeMode.timeInSeconds)
        )
        binding.tvLanguage.text =
            getString(R.string.selected_language_pl, gameMode.getLanguage().getName(this))
        binding.tvTextSuggestions.text = getString(R.string.text_suggestions_pl, textSuggestions)
        binding.tvScreenOrientation.text = getString(R.string.screen_orientation_pl, orientation)
        if (wpm <= 0 && !calledFromResultsTab)
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT)
                .show()
        else if (wpm > 0 && !calledFromResultsTab) {
            saveResultData()
            checkAchievements()
        }
    }

    private fun initTypedWords() {
        if (calledFromResultsTab) {
            binding.tvTypedWordsDescription.text = getString(R.string.typed_words_log_disabled)
            return
        }

        val words: List<Word>? = typedWordsList
        binding.rvTypedWords.adapter = WordsAdapter(words!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvTypedWords.layoutManager = layoutManager
    }

    private fun initPreviousResult() {
        val resultsList = resultsStorage.get()
        val previousResult = resultsList?.getPreviousResultByLanguage(gameMode.getLanguage())
        binding.tvPreviousResult.text = (getString(R.string.previous_result_pl, previousResult))
        if (previousResult == 0)
            binding.tvPreviousResult.visibility = View.GONE
        else binding.tvPreviousResult.visibility = View.VISIBLE
    }

    private fun initBestResult() {
        val resultList = resultsStorage.get()
        bestResultByLanguage = resultList!!.getBestResultByLanguage(gameMode.getLanguage())
        binding.tvBestResult.text = (getString(R.string.best_result_pl, bestResultByLanguage))
        if (bestResultByLanguage == 0) binding.tvBestResult.visibility = View.GONE
        else binding.tvBestResult.visibility = View.VISIBLE
    }

    private fun changeVisibilityFromResultsTab() {
        //binding.bStartOver.visibility = View.GONE
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
        typedWordsList =
            arguments.getSerializable(StringKeys.TEST_TYPED_WORDS_LIST) as ArrayList<Word>?
    }

    private fun saveResultData() {
        // TODO: move this logic to a separate method
        if (wpm > bestResultByLanguage) {
            binding.tvNewBestResult.visibility = View.VISIBLE
        }

        resultsStorage.addResult(result)
    }

    private fun checkAchievements() {
        var newAchievements = 0
        var notificationAchievement: Achievement? = null
        for (achievement in AchievementList(this).get()) {
            val completionTime = achievementsStorage.getCompletionDateTimeLong(achievement.id.toString())
            if (completionTime == 0L && achievement.requirementsAreComplete(resultsStorage.get()!!)) {
                achievementsStorage.store(achievement.id.toString(), Calendar.getInstance().time.time)
                newAchievements++
                notificationAchievement = achievement
            }
        }
        val intent = Intent(
            this, AccountActivity::class.java
        )
            .putExtra(StringKeys.TO_ACHIEVEMENT_SECTION, true)
        if (newAchievements == 1) {
            Snackbar.make(
                findViewById(R.id.result_constraint_layout),
                getString(R.string.achievement_unlocked, notificationAchievement!!.name),
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.check_profile) {
                    startActivity(intent)
                    finish()
                }.show()
        } else if (newAchievements > 1)
            Snackbar.make(
                findViewById(R.id.result_constraint_layout),
                getString(R.string.new_achievements_notification, newAchievements),
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.check_profile) {
                    startActivity(intent)
                    finish()
                }.show()
    }

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
        intent.putExtra(StringKeys.TEST_SETTINGS, gameMode)
        finish()
        startActivity(intent)
    }
}