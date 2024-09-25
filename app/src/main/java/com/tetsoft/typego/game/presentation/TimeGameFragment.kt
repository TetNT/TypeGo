package com.tetsoft.typego.game.presentation

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
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.ump.UserMessagingPlatform
import com.tetsoft.typego.R
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.core.data.adscounter.AdsCounter
import com.tetsoft.typego.core.domain.ScreenOrientation
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.databinding.FragmentTimeGameBinding
import com.tetsoft.typego.core.utils.extensions.addAfterTextChangedListener
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.result.presentation.OwnTextResultViewModel
import com.tetsoft.typego.result.presentation.RandomWordsResultViewModel
import com.tetsoft.typego.core.utils.TimeConvert
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class TimeGameFragment : BaseFragment<FragmentTimeGameBinding>() {

    private val viewModel: TimeGameViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private var countdown: CountDownTimer? = null
    private var gameNotStarted = true
    private var timeTotalAmount = 0
    var secondsPassed = 0
    var adShown = false
    var ignoreCase = true
    var adsCounter: AdsCounter? = null
    var mInterstitialAd: InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.gameSettings is GameSettings.TimeBased.Empty) {
            navigateUp()
            return
        }
        binding.progressLoadingResult.visibility = View.GONE
        adsCounter = (requireActivity().application as TypeGoApp).adsCounter
        adShown = false
        loadAd()
        initialize()
        setScreenOrientation()
        initWords()
        initializeBackButtonCallback()
        binding.inpWord.addAfterTextChangedListener { input ->
            if (!binding.inpWord.isEnabled) return@addAfterTextChangedListener
            // if user pressed space bar in empty text field
            if (input.toString() == " ") {
                binding.inpWord.setText("")
                return@addAfterTextChangedListener
            }

            // if test hasn't started yet and user began to type
            if (gameNotStarted && binding.inpWord.text.isNotEmpty()) {
                startTimer(timeTotalAmount)
                gameNotStarted = false
            }

            if (input!!.isEmpty()) {
                deselectLetters(binding.words.getStartPosition(), binding.words.getEndPosition() + 1)
                return@addAfterTextChangedListener
            }

            if (input.isNotEmpty() && !lastCharIsSpace(input)) {
                checkCorrectnessByLetter()
                return@addAfterTextChangedListener
            }

            // if user's input is not empty and it has space at the end
            binding.words.deselectCurrentWord()
            val sanitizedInput = binding.inpWord.text.toString().trim { it <= ' ' }
            viewModel.addWordToTypedList(sanitizedInput, binding.words.getSelectedWord())
            if (viewModel.wordIsCorrect(sanitizedInput, binding.words.getSelectedWord(), ignoreCase)) {
                viewModel.addScore(binding.words.getSelectedWord().length)
                binding.words.selectCurrentWordAsCorrect()
            } else {
                val charStatuses = viewModel.getTypedWords().last().characterStatuses
                binding.words.selectCurrentWordAsIncorrect(charStatuses)
            }
            if (binding.words.reachedTheEnd()) {
                finishGame()
                return@addAfterTextChangedListener
            }
            binding.words.setNextWordCursors()
            binding.words.selectCurrentWord()
            binding.inpWord.setText("")
        }
        resetAll()
    }

    private fun lastCharIsSpace(s: Editable): Boolean {
        return s[s.length - 1] == ' '
    }

    private fun initialize() {
        timeTotalAmount = viewModel.gameSettings.time
        viewModel.resetGame()
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
            navigateUp()
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
                if (view == null) {
                    return
                }
                binding.tvTimeLeft.text =
                    TimeConvert.convertSecondsToStamp((millisUntilFinished / 1000).toInt())
                secondsPassed = secondsPassed.inc()
            }

            override fun onFinish() {
                finishGame()
            }
        }.start()
    }

    private fun finishGame() {
        countdown?.cancel()
        binding.inpWord.isEnabled = false
        binding.words.isEnabled = false
        binding.progressLoadingResult.visibility = View.VISIBLE
        adsCounter?.addValue(timeTotalAmount / 60f)
        lifecycleScope.launch {
            delay(750)
            if (mInterstitialAd == null || !adsCounter?.enoughToShowAd()!!) {
                showResultScreen()
            } else showAd()
        }
    }

    private fun pauseTimer() {
        if (countdown == null) return
        else countdown?.cancel()
    }

    private fun resumeTimer() {
        if (gameNotStarted) return
        startTimer(timeTotalAmount - secondsPassed)
    }

    private fun checkCorrectnessByLetter() {
        deselectLetters(
            binding.words.getStartPosition(),
            binding.words.getEndPosition()
        )
        for (i in binding.words.getSelectedWord().indices) {
            // deselect the rest of a word if a user hasn't finished typing
            if (i >= binding.inpWord.length()) {
                deselectLetters(
                    i + binding.words.getStartPosition(),
                    binding.words.getEndPosition()
                )
                return
            }
            if (charactersMatch(
                    binding.inpWord.text[i],
                    binding.words.getSelectedWord()[i],
                    ignoreCase
                )
            ) {
                selectCurrentLetterAsCorrect(i + binding.words.getStartPosition())
            } else {
                selectCurrentLetterAsIncorrect(i + binding.words.getStartPosition())
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
        viewModel.resetGame()
        secondsPassed = 0
        binding.tvTimeLeft.text = TimeConvert.convertSecondsToStamp(timeTotalAmount)
        gameNotStarted = true
        binding.words.initializeSelection(binding.words.text.toString())
        binding.words.selectCurrentWord()
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

    private fun initWords() {
        binding.words.setText(viewModel.generateText(requireActivity().assets))
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setScreenOrientation() {
        requireActivity().requestedOrientation = viewModel.gameSettings.screenOrientation.get()
        // TODO: Measure what exact SCROLL_POWER should be
        if (viewModel.gameSettings.screenOrientation === ScreenOrientation.PORTRAIT) {
            binding.words.autoScrollPredictPosition = 25
        } else {
            binding.words.autoScrollPredictPosition = 0
        }
    }


    private fun loadAd() {
        val canRequestAds = UserMessagingPlatform.getConsentInformation(requireContext()).canRequestAds()
        if (!canRequestAds) return
        MobileAds.initialize(requireContext()) {}
        InterstitialAd.load(requireContext(),
            viewModel.getInterstitialAdsId(),
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
            return
        }
        adShown = true
        mInterstitialAd!!.show(requireActivity())
    }

    private fun setAdCallback() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.e("AD", "Failed to show ad: ${adError.message} (${adError.code})")
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
        if (viewModel.gameSettings is GameSettings.ForRandomlyGeneratedWords) {
            val randomWordsResultViewModel: RandomWordsResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            randomWordsResultViewModel.wordsList = viewModel.getTypedWords()
            randomWordsResultViewModel.isGameCompleted = true
            randomWordsResultViewModel.setRandomWordsResult(viewModel.generateRandomWordsResult(Calendar.getInstance().time.time))
            findNavController().navigate(R.id.action_timeGameFragment_to_randomWordsResultFragment)
        } else if (viewModel.gameSettings is GameSettings.ForUserText) {
            val ownTextResultViewModel: OwnTextResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            ownTextResultViewModel.setTypedWordsList(viewModel.getTypedWords())
            ownTextResultViewModel.setOwnTextResult(viewModel.generateOwnTextResult(secondsPassed, Calendar.getInstance().time.time))
            findNavController().navigate(R.id.action_timeGameFragment_to_ownTextResultFragment)
        }
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
        if (gameNotStarted) return
        if (countdown == null) {
            navigateUp()
        }
        showContinueDialog(adShown)
    }

    override fun onDetach() {
        super.onDetach()
        pauseTimer()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeGameBinding {
        return FragmentTimeGameBinding.inflate(inflater, container, false)
    }
}