package com.tetsoft.typego.ui.fragment.game

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tetsoft.typego.Config
import com.tetsoft.typego.R
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.data.AdsCounter
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.databinding.FragmentGameBinding
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.ui.custom.BaseFragment
import com.tetsoft.typego.ui.custom.SpannableEditText.Companion.greenForeground
import com.tetsoft.typego.ui.custom.SpannableEditText.Companion.redForeground
import com.tetsoft.typego.ui.custom.addAfterTextChangedListener
import com.tetsoft.typego.ui.fragment.result.ResultViewModel
import com.tetsoft.typego.utils.TimeConvert.convertSecondsToStamp
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.math.max

class GameFragment : BaseFragment<FragmentGameBinding>() {

    var countdown: CountDownTimer? = null
    private var testInitiallyPaused = true
    var secondsRemaining = 0
    var timeTotalAmount = 0
    var adShown = false
    var ignoreCase = true
    var adsCounter: AdsCounter? = null
    var mInterstitialAd: InterstitialAd? = null

    private val gameViewModel: GameViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameBinding {
        return FragmentGameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adsCounter = (requireActivity().application as TypeGoApp).adsCounter
        adShown = false
        loadAd()
        initialize()
        setScreenOrientation()
        initWords()
        initializeBackButtonCallback()
        binding.inpWord.addAfterTextChangedListener { s ->
            // if a user pressed space in empty text field
            if (s.toString() == " ") {
                binding.inpWord.setText("")
                return@addAfterTextChangedListener
            }

            // if a test hasn't started yet and a user began to type
            if (testInitiallyPaused && binding.inpWord.text.isNotEmpty()) {
                startTimer(secondsRemaining)
                testInitiallyPaused = false
            }

            if (s!!.isEmpty()) {
                deselectLetters(
                    binding.words.selectedWordStartPosition,
                    binding.words.selectedWordEndPosition + 1
                )
                return@addAfterTextChangedListener
            }

            if (s.isNotEmpty() && !lastCharIsSpace(s)) {
                checkCorrectnessByLetter()
                return@addAfterTextChangedListener
            }

            // if user's input is not empty and it has space at the end
            if (binding.words.reachedTheEnd()) {
                // TODO: Handle this situation with interrupting the test (and maybe granting an achievement)
                binding.inpWord.text.clear()
                return@addAfterTextChangedListener
            }
            binding.words.deselectCurrentWord()
            addWordToTypedList(
                binding.inpWord.text.toString().trim { it <= ' ' },
                binding.words.selectedWord.trim { it <= ' ' })
            if (wordIsCorrect(ignoreCase)) {
                gameViewModel.addScore(binding.words.selectedWord.length)
                binding.words.selectCurrentWordAsCorrect()
            } else binding.words.selectCurrentWordAsIncorrect()
            binding.words.setNextWordCursors()
            binding.words.selectNextWord()
            binding.inpWord.setText("")
        }
        resetAll()
        binding.words.initializeWordCursor()
    }

    private fun lastCharIsSpace(s: Editable): Boolean {
        return s[s.length - 1] == ' '
    }

    private fun addWordToTypedList(inputted: String, original: String) {
        gameViewModel.addWordToTypedList(inputted, original)
    }

    private fun initialize() {
        // TODO: Test autoscroll
        val gameMode = gameViewModel.gameMode
        if (gameViewModel.gameMode is GameOnTime) {
            timeTotalAmount = (gameMode as GameOnTime).timeMode.timeInSeconds
            secondsRemaining = timeTotalAmount
        }
        gameViewModel.clearTypedWords()
        binding.inpWord.inputType = gameViewModel.getInputType()

        binding.restartButton.setOnClickListener { restartTest() }
    }

    private fun showContinueDialog(adShown: Boolean) {
        if (adShown) return
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(getString(R.string.continue_testing))
            .setTitle(getString(R.string.welcome_back))
        dialog.setNegativeButton(getString(R.string.no)) { _: DialogInterface?, _: Int ->
            pauseTimer()
            binding.root.findNavController().navigateUp()
        }
        dialog.setPositiveButton(getString(R.string.yes)) { dial: DialogInterface, _: Int ->
            resumeTimer()
            dial.cancel()
        }
        dialog.show()
        pauseTimer()
    }

    private fun startTimer(seconds: Int) {
        countdown = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimeLeft.text =
                    convertSecondsToStamp((millisUntilFinished / 1000).toInt())
                secondsRemaining = secondsRemaining.dec()
            }

            override fun onFinish() {
                binding.inpWord.isEnabled = false
                adsCounter?.addValue(timeTotalAmount / 60f)
                if (mInterstitialAd == null || !adsCounter?.enoughToShowAd()!!) {
                    showResultScreen()
                } else showAd()
            }
        }.start()
    }

    private fun pauseTimer() {
        if (countdown == null) return
        else countdown?.cancel()
    }

    private fun resumeTimer() {
        if (testInitiallyPaused) return
        startTimer(secondsRemaining)
    }

    private fun checkCorrectnessByLetter() {
        deselectLetters(
            binding.words.selectedWordStartPosition,
            binding.words.selectedWordEndPosition
        )
        for (i in binding.words.selectedWord.indices) {
            // if a user hasn't finished typing then deselect the rest of a word
            if (i >= binding.inpWord.length()) {
                deselectLetters(
                    i + binding.words.selectedWordStartPosition,
                    binding.words.selectedWordEndPosition
                )
                return
            }
            if (charactersMatch(
                    binding.inpWord.text[i],
                    binding.words.selectedWord[i],
                    ignoreCase
                )
            ) {
                selectCurrentLetterAsCorrect(i + binding.words.selectedWordStartPosition)
            } else {
                selectCurrentLetterAsIncorrect(i + binding.words.selectedWordStartPosition)
            }
        }
    }

    private fun charactersMatch(c1: Char, c2: Char, ignoreCase: Boolean): Boolean {
        var char1 = c1
        var char2 = c2
        if (ignoreCase) {
            char1 = c1.lowercaseChar()
            char2 = c2.lowercaseChar()
        }
        return char1 == char2
    }

    private fun showExitDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(getString(R.string.dialog_exit_test)).setTitle(R.string.exit)
        dialog.setNegativeButton(R.string.no) { dial, _ ->
            resumeTimer()
            dial.cancel()
        }
        dialog.setPositiveButton(R.string.yes) { _, _ ->
            pauseTimer()
            binding.root.findNavController().navigateUp()
        }
        dialog.show()
        pauseTimer()
    }

    private fun restartTest() {
        pauseTimer()
        initWords()
        resetAll()
    }

    private fun resetAll() {
        gameViewModel.clearScore()
        binding.words.selectedWordStartPosition = 0
        binding.words.selectedWordEndPosition = 0
        secondsRemaining = timeTotalAmount
        binding.tvTimeLeft.text = convertSecondsToStamp(secondsRemaining)
        testInitiallyPaused = true
        binding.words.initializeWordCursor()
        binding.words.selectNextWord()
        binding.inpWord.requestFocus()
        binding.inpWord.setText("")
    }

    private fun selectCurrentLetterAsCorrect(symbolIndex: Int) {
        binding.words.paintForeground(symbolIndex, greenForeground)
    }

    private fun selectCurrentLetterAsIncorrect(symbolIndex: Int) {
        binding.words.paintForeground(symbolIndex, redForeground)
    }

    private fun deselectLetters(startIndex: Int, endIndex: Int) {
        binding.words.clearForeground(startIndex, endIndex)
    }

    private fun wordIsCorrect(ignoreCase: Boolean): Boolean {
        val enteredWord: String =
            binding.inpWord.text.toString().trim { it <= ' ' } // removes a space at the end
        val comparingWord: String = binding.words.selectedWord
        return enteredWord.equals(comparingWord, ignoreCase)
    }

    // TODO: Rename the packages to lower case and use enum.name instead
    private fun getDictionaryFolderPath(dictionaryType: DictionaryType): String {
        return if (dictionaryType === DictionaryType.BASIC) "WordLists/Basic/" else "WordLists/Enhanced/"
    }

    // TODO: move this method to a class that will implement a TextSource interface
    private fun initWords() {

        val assets: AssetManager = requireActivity().assets
        val inputStream: InputStream
        var loadingWordList = ArrayList<String>()
        try {
            if (gameViewModel.gameMode is PrebuiltTextGameMode) {
                val prebuiltGameMode = gameViewModel.gameMode as PrebuiltTextGameMode
                val languageIdentifier: String = prebuiltGameMode.getLanguage().identifier
                inputStream = assets.open(
                    getDictionaryFolderPath(prebuiltGameMode.getDictionary()) + languageIdentifier + ".txt"
                )
                val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                loadingWordList = ArrayList(reader.readText().split("\r\n"))
                reader.close()
                inputStream.close()
            }

        } catch (e: IOException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.words_loading_error_occurred),
                Toast.LENGTH_SHORT
            ).show()
            binding.root.findNavController().navigateUp()
            return
        }
        val rnd = Random()
        val str = StringBuilder()
        val amountOfWords: Int = max((250.0 * (timeTotalAmount / 60.0)), 100.0).toInt()
        for (i in 0..amountOfWords) {
            // TODO: move this to the ViewModel and use coroutines to initialize this
            val word = loadingWordList[rnd.nextInt(loadingWordList.size)]
            str.append(word).append(" ")
        }
        binding.words.setText(str.toString())
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setScreenOrientation() {
        // TODO: Measure what exact SCROLL_POWER should be
        if (gameViewModel.gameMode.screenOrientation === ScreenOrientation.PORTRAIT) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            binding.words.autoScrollPredictPosition = 25
        } else {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.words.autoScrollPredictPosition = 0
        }
    }

    private fun loadAd() {
        Log.i("LoadAD", "Start loading")
        MobileAds.initialize(
            requireContext()
        ) { }
        InterstitialAd.load(requireContext(),
            Config.INTERSTITIAL_ID,
            (requireContext().applicationContext as TypeGoApp).adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    setAdCallback()
                    Log.i("LoadAD", "Ad loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("LoadAD", "Failed to load ad:" + loadAdError.message)
                    mInterstitialAd = null
                }
            })
    }

    // TODO: Move this method to the results page
    private fun setAdCallback() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.i("AD", "Failed to show ad:" + adError.message)
                showResultScreen()
            }

            override fun onAdDismissedFullScreenContent() {
                adShown = true
                adsCounter?.reset()
                showResultScreen()
            }
        }
    }

    private fun showAd() {
        if (mInterstitialAd == null) {
            Log.i("AD", "showAd(): Ad is null")
            return
        }
        adShown = true
        mInterstitialAd!!.show(requireActivity())
    }

    private fun showResultScreen() {
        binding.tvTimeLeft.text = getString(R.string.time_over)
        val resultViewModel: ResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
        resultViewModel.selectGameMode(gameViewModel.gameMode)
        resultViewModel.result = GameResult(
            gameViewModel.gameMode,
            gameViewModel.getScore(),
            timeTotalAmount,
            gameViewModel.getTypedWords().size,
            gameViewModel.calculateCorrectWords(),
            Calendar.getInstance().time.time
        )
        resultViewModel.selectTypedWordsList(gameViewModel.getTypedWords())
        resultViewModel.setGameCompleted(true)
        navigate(R.id.action_gameFragment_to_resultFragment)
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        if (testInitiallyPaused) return
        showContinueDialog(adShown)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun initializeBackButtonCallback() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}