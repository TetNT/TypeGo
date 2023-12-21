package com.tetsoft.typego.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tetsoft.typego.R
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.databinding.FragmentGameOnTimeResultBinding
import com.tetsoft.typego.extensions.copyToClipboard
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.game.GameOnTimeViewModel
import com.tetsoft.typego.ui.fragment.typedwords.TypedWordsViewModel
import com.tetsoft.typego.ui.visibility.VisibilityMapper
import com.tetsoft.typego.utils.AnimationsPreset
import com.tetsoft.typego.utils.Animator
import com.tetsoft.typego.utils.Translation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameOnTimeResultFragment : BaseFragment<FragmentGameOnTimeResultBinding>() {

    private val viewModel: GameOnTimeResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val translation by lazy { Translation(requireContext()) }

    private var resultSaved = false

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameOnTimeResultBinding {
        return FragmentGameOnTimeResultBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWpm()
        initPreviousResultSection()
        initBestResultSection()
        initDetails()
        storeResult()
        setupButtons()
    }

    private fun setupButtons() {
        binding.tvCheckLog.setOnClickListener {
            if (viewModel.hasWordsLog() && viewModel.isGameCompleted) {
                val typedWordsViewModel: TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                typedWordsViewModel.selectTypedWordsList(viewModel.wordsList)
                findNavController().navigate(R.id.action_gameOnTimeResultFragment_to_typedWordsFragment)
            } else {
                showToast(R.string.typed_words_log_disabled)
            }
        }

        binding.bFinish.setOnClickListener { binding.root.findNavController().navigateUp() }
        if (resultCalledFromHistory()) {
            binding.bFinish.text = getString(R.string.close)
        } else {
            binding.bFinish.isEnabled = false
            binding.bStartOver.isEnabled = false
            lifecycleScope.launch {
                delay(COUNT_UP_ANIMATION_DURATION)
                binding.bFinish.isEnabled = true
                binding.bStartOver.isEnabled = true
            }
        }
        binding.bStartOver.setOnClickListener {
            val gameViewModel: GameOnTimeViewModel by navGraphViewModels(R.id.main_navigation)
            gameViewModel.gameOnTime = (viewModel.result)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.gameOnTimeResultFragment, true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
            binding.root.findNavController()
                .navigate(
                    R.id.action_gameOnTimeResultFragment_to_gameOnTimeFragment,
                    null,
                    navOptions
                )
        }
    }

    private fun initWpm() {
        val countAnimation = Animator.CountUp(COUNT_UP_ANIMATION_DURATION, viewModel.getWpm()).get()
        countAnimation.addUpdateListener { anim ->
            if (context == null) return@addUpdateListener
            binding.tvWPM.text = anim.animatedValue.toString()
        }
        countAnimation.start()
        binding.tvCpm.text = getString(R.string.cpm_pl, viewModel.getCpm())
        binding.tvCpm.animation = AnimationsPreset.FadeIn(COUNT_UP_ANIMATION_DURATION).get()
        binding.tvCpm.animation.start()
    }

    private fun initPreviousResultSection() {
        if (!viewModel.isGameCompleted) {
            binding.previousResultSection.visibility = View.GONE
            return
        }
        with(binding) {
            val previousResult = viewModel.getPreviousWpm()
            previousResultSection.visibility =
                viewModel.getVisibilityForResult(previousResult)
            // TODO: Optimize it so we don't need to call difference calc from the fragment
            tvPreviousResult.text = (getString(R.string.previous_result_pl, previousResult))
            val diff = viewModel.subtractCurrentWpmAndOtherResult(previousResult)
            tvDifferenceWithPreviousResult.visibility =
                viewModel.getVisibilityForDifference(diff)
            tvDifferenceWithPreviousResult.text = viewModel.getDifferenceString(diff)
            tvDifferenceWithPreviousResult.setTextColor(viewModel.getDifferenceColor(diff))
            tvDifferenceWithPreviousResult.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            tvDifferenceWithPreviousResult.animation.startOffset =
                PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET
            tvDifferenceWithPreviousResult.animation.start()
        }
    }

    private fun initBestResultSection() {
        with(binding) {
            val bestResult = viewModel.getBestWpmByCurrentLanguage()
            bestResultSection.visibility = viewModel.getVisibilityForResult(bestResult)
            tvBestResult.text = (getString(R.string.best_result_pl, bestResult))
            if (!viewModel.isGameCompleted) {
                tvNewBestResult.visibility = View.GONE
                return
            }
            val diff = viewModel.subtractCurrentWpmAndOtherResult(bestResult)
            tvDifferenceWithBestResult.visibility = viewModel.getVisibilityForDifference(diff)
            tvDifferenceWithBestResult.text = viewModel.getDifferenceString(diff)
            tvDifferenceWithBestResult.setTextColor(viewModel.getDifferenceColor(diff))
            tvNewBestResult.visibility = VisibilityMapper.FromBoolean(diff > 0).get()
            tvDifferenceWithBestResult.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            tvDifferenceWithBestResult.animation.startOffset =
                BEST_RESULT_FADE_IN_ANIMATION_OFFSET
            tvDifferenceWithBestResult.animation.start()
        }
    }

    private fun initDetails() {
        with(binding) {
            if (viewModel.result is GameOnTime.Empty) {
                findNavController().navigateUp()
                return
            }
            tvLanguage.text =
                getString(
                    R.string.selected_language_pl,
                    translation.get(viewModel.getLanguage())
                )
            tvSelectedTime.text =
                getString(
                    R.string.selected_time_pl,
                    translation.get(TimeMode(viewModel.getTimeInSeconds()))
                )
            tvDictionary.text =
                getString(
                    R.string.dictionary_pl,
                    translation.get(viewModel.getDictionary())
                )
            tvCorrectWords.text =
                getString(R.string.correct_words_pl, viewModel.getCorrectWords())
            tvIncorrectWords.text =
                getString(R.string.incorrect_words_pl, viewModel.getIncorrectWords())
            tvTextSuggestions.text =
                getString(
                    R.string.text_suggestions_pl,
                    translation.get(viewModel.areSuggestionsActivated())
                )
            tvScreenOrientation.text =
                getString(
                    R.string.screen_orientation_pl,
                    translation.get(viewModel.getScreenOrientation())
                )
            tvSeed.text = getString(R.string.seed_pl, viewModel.getSeedOrBlankSign())
            tvSeed.setOnClickListener {
                if (!viewModel.seedIsEmpty()) {
                    showToast(getString(R.string.seed_copy_hint, viewModel.result.getSeed()))
                } else {
                    showToast(R.string.seed_not_set)
                }
            }
            tvSeed.setOnLongClickListener {
                if (!viewModel.seedIsEmpty()) {
                    showToast(R.string.seed_copied)
                    requireContext().copyToClipboard("TypeGo seed", viewModel.result.getSeed())
                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }
        }
    }

    // TODO: Overhaul
    private fun storeResult() {
        if (resultSaved) {
            return
        }
        if (!viewModel.isGameCompleted) {
            return
        }
        if (viewModel.resultIsValid()) {
            viewModel.saveResult()
            resultSaved = true
            if (viewModel.getEarnedAchievementsCount(AchievementsList.get()) > 0) {
                makeSnackbar(getString(R.string.new_achievements_notification))
                    .setAction(R.string.check_profile) {
                        binding.root.findNavController()
                            .navigate(R.id.action_gameOnTimeResultFragment_to_achievementsFragment)
                    }.show()
            }
        } else showToast(getString(R.string.msg_results_with_zero_wpm))
    }

    private fun resultCalledFromHistory(): Boolean {
        val previousDestination =
            binding.root.findNavController().previousBackStackEntry?.destination?.id
        return previousDestination == R.id.accountFragment
    }

    private companion object {
        const val COUNT_UP_ANIMATION_DURATION = 1000L
        const val PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION - 200L
        const val BEST_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION
    }

}