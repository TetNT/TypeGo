package com.tetsoft.typego.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.achievement.Achievement
import com.tetsoft.typego.account.User
import com.tetsoft.typego.testing.*
import com.tetsoft.typego.utils.*
import java.util.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {
    private var currentUser: User? = null
    private var wpm = 0
    private var correctWords = 0
    private var correctWordsWeight = 0
    private var timeInSeconds = 0
    private var dictionaryType = 0
    private var screenOrientation = 0
    private var bestResultByLanguage = 0
    private var totalWords = 0
    private var calledFromResultsTab = false
    private var calledFromMainMenu = false
    private var textSuggestionsIsOn = false
    private var dictionaryLanguage: Language? = null
    private var result: TypingResult? = null
    private var typedWordsList: ArrayList<Word>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val emojiConfig: EmojiCompat.Config = BundledEmojiCompatConfig(this)
        EmojiCompat.init(emojiConfig)
        setContentView(R.layout.activity_result)
        currentUser = User.getFromStoredData(this)
        initIntentData()
        initPreviousResult()
        if (!calledFromResultsTab) temporarilyDisableButtons()
        initBestResult()
        initTypedWords()
        if (calledFromResultsTab) changeVisibilityFromResultsTab()
        val tvWPM = findViewById<EmojiTextView>(R.id.tvWPM)
        val textSuggestions = if (textSuggestionsIsOn) getString(R.string.yes) else getString(R.string.no)
        val dictionaryName = if (dictionaryType == 0) getString(R.string.basic) else getString(R.string.enhanced)
        val orientation = if (screenOrientation == 0) getString(R.string.vertical) else getString(R.string.horizontal)
        result = currentResult
        wpm = result!!.wpm.toInt()
        val wpmText = """$wpm ${getString(R.string.wordsPerMinute)}
            |${Emoji.getEmojiOfWpm(wpm)}""".trimMargin()
        tvWPM.text = wpmText
        tvIncorrectWords.text = getString(R.string.incorrect_words_pl, result!!.incorrectWords)
        tvCorrectWords.text = getString(R.string.correct_words_pl, correctWords)
        tvCorrectChars.text = getString(R.string.correct_chars_pl, correctWordsWeight)
        tvDictionary.text = getString(R.string.dictionary_pl, dictionaryName)
        tvAllottedTime.text = getString(R.string.selected_time_pl, TimeConvert.convertSeconds(this, timeInSeconds))
        tvLanguage.text = getString(R.string.selected_language_pl, dictionaryLanguage!!.getName(this))
        tvTextSuggestions.text = getString(R.string.text_suggestions_pl, textSuggestions)
        tvScreenOrientation.text = getString(R.string.screen_orientation_pl, orientation)
        if (wpm <= 0 && !calledFromResultsTab)
            Toast.makeText(this, getString(R.string.msg_results_with_zero_wpm), Toast.LENGTH_SHORT).show()
        else if (wpm > 0 && !calledFromResultsTab) saveResultData()
    }

    private fun initTypedWords() {
        if (calledFromResultsTab) {
            tvTypedWordsDescription.text = getString(R.string.typed_words_log_disabled)
            return
        }
        val rvWords = findViewById<RecyclerView>(R.id.rvTypedWords)
        val words: List<Word>? = typedWordsList
        rvWords.adapter = WordsAdapter(words!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvWords.layoutManager = layoutManager
    }

    private fun initPreviousResult() {
        val prevResultByLang = currentUser!!.getLastResultByLanguage(dictionaryLanguage)
        tvPreviousResult?.text = (getString(R.string.previous_result_pl, prevResultByLang))
        if (prevResultByLang == 0)
            tvPreviousResult?.visibility = View.GONE
        else tvPreviousResult?.visibility = View.VISIBLE
    }

    private fun initBestResult() {
        bestResultByLanguage = currentUser!!.getBestResultByLanguage(dictionaryLanguage)
        tvBestResult?.text = (getString(R.string.best_result_pl, bestResultByLanguage))
        if (bestResultByLanguage == 0) tvBestResult?.visibility = View.GONE
        else tvBestResult?.visibility = View.VISIBLE
    }

    private fun changeVisibilityFromResultsTab() {
        bStartOver?.visibility = View.GONE
        tvPreviousResult!!.visibility = View.GONE
        bFinish?.text = getString(R.string.close)
    }

    private fun initIntentData() {
        val arguments = intent.extras
        correctWords = arguments!!.getInt(StringKeys.TEST_CORRECT_WORDS)
        correctWordsWeight = arguments.getInt(StringKeys.TEST_CORRECT_WORDS_WEIGHT)
        timeInSeconds = arguments.getInt(StringKeys.TEST_AMOUNT_OF_SECONDS)
        totalWords = arguments.getInt(StringKeys.TOTAL_WORDS)
        dictionaryType = arguments.getInt(StringKeys.TEST_DICTIONARY_TYPE)
        screenOrientation = arguments.getInt(StringKeys.TEST_SCREEN_ORIENTATION)
        dictionaryLanguage = arguments.getSerializable(StringKeys.TEST_DICTIONARY_LANG) as Language?
        textSuggestionsIsOn = arguments.getBoolean(StringKeys.TEST_SUGGESTIONS_ON)
        calledFromResultsTab = arguments.getBoolean(StringKeys.CALLED_FROM_PASSED_RESULTS)
        calledFromMainMenu = arguments.getBoolean(StringKeys.FROM_MAIN_MENU)
        typedWordsList = arguments.getSerializable(StringKeys.TEST_TYPED_WORDS_LIST) as ArrayList<Word>?

    }

    private fun saveResultData() {
        if (currentUser == null) {
            Toast.makeText(this, getString(R.string.saving_results_error_occurred), Toast.LENGTH_SHORT).show()
            return
        }
        currentUser!!.lastResult = wpm
        if (currentUser!!.bestResult < currentUser!!.lastResult) {
            currentUser!!.bestResult = currentUser!!.lastResult
        }
        if (currentUser!!.lastResult > bestResultByLanguage) {
            tvNewBestResult.visibility = View.VISIBLE
        }
        currentUser!!.addResult(result!!)
        checkAchievements()
        currentUser!!.storeData(this)
    }

    private fun checkAchievements() {
        var newAchievements = 0
        var notificationAchievement: Achievement? = null
        for (achievement in currentUser!!.achievements) {
            if (achievement.completionDate == null && achievement.isCompleted(currentUser, result)) {
                achievement.completionDate = Calendar.getInstance().time
                newAchievements++
                notificationAchievement = achievement
            }
        }
        if (newAchievements == 1) {
            Toast.makeText(this, getString(R.string.achievement_unlocked, notificationAchievement!!.name), Toast.LENGTH_SHORT).show()
        } else if (newAchievements > 1) Toast.makeText(
                this@ResultActivity,
                getString(R.string.new_achievements_notification, newAchievements),
                Toast.LENGTH_LONG).show()
    }

    private val currentResult: TypingResult
        get() = TypingResult(
                correctWords,
                correctWordsWeight,
                totalWords,
                dictionaryType,
                screenOrientation,
                dictionaryLanguage,
                timeInSeconds,
                textSuggestionsIsOn,
                Calendar.getInstance().time)

    fun saveAndContinue(view: View?) {
        finish()
        var intent: Intent? = null
        if (calledFromMainMenu) intent = Intent(this, MainActivity::class.java)
        else if (!calledFromResultsTab) intent = Intent(this, TestSetupActivity::class.java)
        intent?.let { startActivity(it) }
    }


    private fun temporarilyDisableButtons() {
        bFinish?.isEnabled = false
        bStartOver?.isEnabled = false
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            bFinish?.isEnabled = true
            bStartOver?.isEnabled = true
        }
        handler.postDelayed(runnable, 2000)
    }

    fun startOver(v: View?) {
        val arguments = intent.extras
        val intent = Intent(this@ResultActivity, TypingTestActivity::class.java)
        intent.putExtra(StringKeys.TEST_SUGGESTIONS_ON, arguments!!.getBoolean(StringKeys.TEST_SUGGESTIONS_ON))
        intent.putExtra(StringKeys.TEST_AMOUNT_OF_SECONDS, arguments.getInt(StringKeys.TEST_AMOUNT_OF_SECONDS))
        intent.putExtra(StringKeys.TEST_DICTIONARY_TYPE, arguments.getInt(StringKeys.TEST_DICTIONARY_TYPE))
        intent.putExtra(StringKeys.TEST_DICTIONARY_LANG, arguments.getSerializable(StringKeys.TEST_DICTIONARY_LANG))
        intent.putExtra(StringKeys.TEST_SCREEN_ORIENTATION, screenOrientation)
        finish()
        startActivity(intent)
    }
}