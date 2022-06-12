package com.tetsoft.typego.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.R
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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
    private var completionDateLong : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        achievementsStorage = AchievementsProgressStorage(this)
        resultsStorage = GameResultListStorage(this)
        gameMode = intent.extras?.getSerializable(StringKeys.TEST_SETTINGS) as GameOnTime
        initIntentData()
        binding.bFinish.setOnClickListener { saveAndContinue() }
        binding.bStartOver.setOnClickListener { startOver() }
        binding.tvLeaveFeedback.setOnClickListener { leaveFeedbackClick() }
        if (completionDateLong == 0L) {
            completionDateLong = Calendar.getInstance().time.time
        }
        result = GameResult(
            gameMode,
            correctWordsWeight,
            gameMode.timeMode.timeInSeconds,
            totalWords,
            correctWords,
            completionDateLong
        )
        wpm = result.wpm.toInt()
        initPreviousResultSection()
        if (!calledFromResultsTab) temporarilyDisableButtons()
        initBestResultSection()
        initTypedWords()
        if (calledFromResultsTab) binding.bFinish.text = getString(R.string.close)
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

        val animationManager = AnimationManager()
        val countAnimation = animationManager.getCountAnimation(0, wpm, COUNT_UP_ANIMATION_DURATION)
        animationManager.applyCountAnimation(countAnimation, binding.tvWPM)
        countAnimation.start()
        binding.tvWPM.text = getString(R.string.results_wpm_pl, wpm)
        binding.tvIncorrectWords.text =
            getString(R.string.incorrect_words_pl, result.wordsWritten - result.correctWords)
        binding.tvCorrectWords.text = getString(R.string.correct_words_pl, correctWords)
        binding.tvDictionary.text = getString(R.string.dictionary_pl, dictionaryName)
        binding.tvAllottedTime.text = getString(
            R.string.selected_time_pl,
            TimeConvert.convertSeconds(this, gameMode.timeMode.timeInSeconds)
        )
        binding.tvLanguage.text =
            getString(R.string.selected_language_pl, gameMode.getLanguage().getName(this))
        binding.tvTextSuggestions.text = getString(R.string.text_suggestions_pl, textSuggestions)
        binding.tvScreenOrientation.text = getString(R.string.screen_orientation_pl, orientation)
        try {
            val dateFormat = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.DEFAULT,
                SimpleDateFormat.SHORT
            )
            binding.tvCompletionDate.text = getString(
                R.string.date_pl,
                dateFormat.format(Date(result.completionDateTime))
            )
        } catch (e: Exception) {
            binding.tvCompletionDate.visibility = View.GONE
        }

        if (wpm <= 0 && !calledFromResultsTab)
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT)
                .show()
        else if (wpm > 0 && !calledFromResultsTab) {
            resultsStorage.addResult(result)
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

    private fun initPreviousResultSection() {
        if (calledFromResultsTab) {
            binding.previousResultSection.visibility = View.GONE
            return
        }
        val resultsList = resultsStorage.get()
        val previousResult = resultsList.getPreviousResultByLanguage(gameMode.getLanguage())
        if (previousResult == 0) {
            binding.previousResultSection.visibility = View.GONE
        }
        else {
            binding.tvPreviousResult.text = (getString(R.string.previous_result_pl, previousResult))
            binding.tvPreviousResult.visibility = View.VISIBLE
            val resultDifference = (result.wpm - previousResult).toInt()
            if (resultDifference == 0) {
                binding.tvDifferenceWithPreviousResult.visibility = View.GONE
                return
            }
            if (resultDifference > 0) {
                binding.tvDifferenceWithPreviousResult.text = "(+${abs(resultDifference)})"
                binding.tvDifferenceWithPreviousResult.setTextColor(Color.GREEN)
            } else if (resultDifference < 0) {
                binding.tvDifferenceWithPreviousResult.text = "(-${abs(resultDifference)})"
                binding.tvDifferenceWithPreviousResult.setTextColor(Color.GRAY)
            }

            binding.tvDifferenceWithPreviousResult.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            binding.tvDifferenceWithPreviousResult.animation.startOffset = PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET
            binding.tvDifferenceWithPreviousResult.animation.start()
        }
    }

    private fun initBestResultSection() {
        val resultsList = resultsStorage.get()
        bestResultByLanguage = resultsList.getBestResultByLanguage(gameMode.getLanguage())
        if (bestResultByLanguage == 0) {
            binding.bestResultSection.visibility = View.GONE
            return
        }
        binding.tvBestResult.text = (getString(R.string.best_result_pl, bestResultByLanguage))
        binding.tvBestResult.visibility = View.VISIBLE
        if (calledFromResultsTab) return
        val resultDifference = (result.wpm - bestResultByLanguage).toInt()
        if (resultDifference == 0) {
            binding.tvDifferenceWithBestResult.visibility = View.GONE
        } else if (resultDifference > 0) {
            binding.tvDifferenceWithBestResult.text = "(+${abs(resultDifference)})"
            binding.tvDifferenceWithBestResult.setTextColor(Color.GREEN)
            binding.tvNewBestResult.visibility = View.VISIBLE
        } else if (resultDifference < 0) {

            binding.tvDifferenceWithBestResult.text = "(-${abs(resultDifference)})"
            binding.tvDifferenceWithBestResult.setTextColor(Color.GRAY)
        }
        binding.tvDifferenceWithBestResult.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.tvDifferenceWithBestResult.animation.startOffset = BEST_RESULT_FADE_IN_ANIMATION_OFFSET
        binding.tvDifferenceWithBestResult.animation.start()
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
        completionDateLong = arguments.getLong(StringKeys.TEST_COMPLETION_DATE, 0L)
    }

    private fun checkAchievements() {
        var newAchievements = 0
        for (achievement in AchievementList(this).get()) {
            val completionTime = achievementsStorage.getCompletionDateTimeLong(achievement.id.toString())
            if (completionTime == 0L && achievement.requirementsAreComplete(resultsStorage.get())) {
                achievementsStorage.store(achievement.id.toString(), Calendar.getInstance().time.time)
                newAchievements++
            }
        }
        val intent = Intent(
            this, AccountActivity::class.java
        )
            .putExtra(StringKeys.TO_ACHIEVEMENT_SECTION, true)
        if (newAchievements > 0) {
            Snackbar.make(
                findViewById(R.id.result_constraint_layout),
                getString(R.string.new_achievements_notification),
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.check_profile) {
                    startActivity(intent)
                    finish()
                }.show()
        }
    }

    private fun saveAndContinue() {
        finish()
        var intent = Intent(this, TestSetupActivity::class.java)
        if (calledFromMainMenu) intent = Intent(this, MainActivity::class.java)
        else if (calledFromResultsTab) return
        startActivity(intent)
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

    private fun startOver() {
        val intent = Intent(this@ResultActivity, TypingTestActivity::class.java)
        intent.putExtra(StringKeys.TEST_SETTINGS, gameMode)
        finish()
        startActivity(intent)
    }

    private fun leaveFeedbackClick() {
        binding.tvLeaveFeedback.setOnClickListener {
            val appPackageName: String = this.packageName
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }

    companion object {
        const val COUNT_UP_ANIMATION_DURATION = 1000L
        const val PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION - 200L
        const val BEST_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION
    }
}