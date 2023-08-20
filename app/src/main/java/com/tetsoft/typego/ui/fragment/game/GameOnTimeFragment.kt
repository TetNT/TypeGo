package com.tetsoft.typego.ui.fragment.game

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.ActivityInfo
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
import androidx.navigation.fragment.findNavController
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
import com.tetsoft.typego.data.calculation.CpmCalculation
import com.tetsoft.typego.data.calculation.WpmCalculation
import com.tetsoft.typego.data.textsource.AssetStringReader
import com.tetsoft.typego.data.textsource.ShuffledTextFromAsset
import com.tetsoft.typego.databinding.FragmentGameOnTimeBinding
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.ui.custom.SpannableEditText
import com.tetsoft.typego.ui.custom.addAfterTextChangedListener
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.result.GameOnTimeResultViewModel
import com.tetsoft.typego.utils.TimeConvert
import java.util.*
import kotlin.math.max

class GameOnTimeFragment : BaseFragment<FragmentGameOnTimeBinding>() {

    private val viewModel: GameOnTimeViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private var countdown: CountDownTimer? = null
    private var testInitiallyPaused = true
    var secondsRemaining = 0
    var timeTotalAmount = 0
    var adShown = false
    var ignoreCase = true
    var adsCounter: AdsCounter? = null
    var mInterstitialAd: InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.gameOnTime is GameOnTime.Empty) {
            findNavController().navigateUp()
            return
        }
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
                binding.inpWord.text.clear()
                return@addAfterTextChangedListener
            }
            binding.words.deselectCurrentWord()
            viewModel.addWordToTypedList(
                binding.inpWord.text.toString().trim { it <= ' ' },
                binding.words.selectedWord.trim { it <= ' ' })
            if (viewModel.wordIsCorrect(binding.inpWord.text.toString(), binding.words.selectedWord, ignoreCase)) {
                viewModel.addScore(binding.words.selectedWord.length)
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

    private fun initialize() {
        timeTotalAmount = viewModel.gameOnTime.getTimeSpent()
        secondsRemaining = timeTotalAmount
        viewModel.clearTypedWords()
        binding.inpWord.inputType = viewModel.getInputType()
        binding.restartButton.setOnClickListener { restartTest() }
    }

    private fun showContinueDialog(adShown: Boolean) {
        if (adShown) return
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(getString(R.string.continue_testing))
            .setTitle(getString(R.string.welcome_back))
        dialog.setNegativeButton(getString(R.string.no)) { _: DialogInterface?, _: Int ->
            pauseTimer()
            findNavController().navigateUp()
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
                    TimeConvert.convertSecondsToStamp((millisUntilFinished / 1000).toInt())
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
            findNavController().navigateUp()
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
        viewModel.clearScore()
        binding.words.selectedWordStartPosition = 0
        binding.words.selectedWordEndPosition = 0
        secondsRemaining = timeTotalAmount
        binding.tvTimeLeft.text = TimeConvert.convertSecondsToStamp(secondsRemaining)
        testInitiallyPaused = true
        binding.words.initializeWordCursor()
        binding.words.selectNextWord()
        binding.inpWord.requestFocus()
        binding.inpWord.setText("")
    }

    private fun selectCurrentLetterAsCorrect(symbolIndex: Int) {
        binding.words.paintForeground(symbolIndex, SpannableEditText.greenForeground)
    }

    private fun selectCurrentLetterAsIncorrect(symbolIndex: Int) {
        binding.words.paintForeground(symbolIndex, SpannableEditText.redForeground)
    }

    private fun deselectLetters(startIndex: Int, endIndex: Int) {
        binding.words.clearForeground(startIndex, endIndex)
    }

    private fun getDictionaryFolderPath(dictionaryType: DictionaryType): String {
        return if (dictionaryType === DictionaryType.BASIC) "words/basic/" else "words/enhanced/"
    }

    // TODO: move this method to a class that will implement a TextSource interface
    private fun initWords() {
        val amountOfWords: Int = max((250.0 * (timeTotalAmount / 60.0)), 100.0).toInt()
        val path = getDictionaryFolderPath(viewModel.gameOnTime.getDictionaryType()) + viewModel.gameOnTime.getLanguageCode()+ ".txt"
        val textSource = ShuffledTextFromAsset(AssetStringReader(requireActivity().assets), path, amountOfWords).getString()

        if (textSource.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.words_loading_error_occurred),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
            return
        }
        binding.words.setText(textSource)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setScreenOrientation() {
        requireActivity().requestedOrientation = viewModel.gameOnTime.getScreenOrientation().get()
        // TODO: Measure what exact SCROLL_POWER should be
        if (viewModel.gameOnTime.getScreenOrientation() === ScreenOrientation.PORTRAIT) {
            binding.words.autoScrollPredictPosition = 25
        } else {
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

    private fun showAd() {
        if (mInterstitialAd == null) {
            Log.i("AD", "showAd(): Ad is null")
            return
        }
        adShown = true
        mInterstitialAd!!.show(requireActivity())
    }

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

    private fun showResultScreen() {
        binding.tvTimeLeft.text = getString(R.string.time_over)
        val gameOnTimeResultViewModel: GameOnTimeResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
        gameOnTimeResultViewModel.result = GameOnTime(
            WpmCalculation.Standard(viewModel.gameOnTime.getTimeSpent(), viewModel.getScore()).calculate(),
            CpmCalculation.Standard(viewModel.gameOnTime.getTimeSpent(), viewModel.getScore()).calculate(),
            viewModel.getScore(),
            viewModel.gameOnTime.getTimeSpent(),
            viewModel.gameOnTime.getLanguageCode(),
            viewModel.gameOnTime.getDictionaryType().name,
            viewModel.gameOnTime.getScreenOrientation().name,
            viewModel.gameOnTime.areSuggestionsActivated(),
            viewModel.getTypedWords().size,
            viewModel.calculateCorrectWords(),
            Calendar.getInstance().time.time
        )
        gameOnTimeResultViewModel.wordsList = viewModel.getTypedWords()
        gameOnTimeResultViewModel.isGameCompleted = true
        findNavController().navigate(R.id.action_gameOnTimeFragment_to_gameOnTimeResultFragment)
    }

    private fun initializeBackButtonCallback() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameOnTimeBinding {
        return FragmentGameOnTimeBinding.inflate(inflater, container, false)
    }
}