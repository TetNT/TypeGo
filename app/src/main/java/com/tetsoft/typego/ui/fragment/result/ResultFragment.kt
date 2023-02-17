package com.tetsoft.typego.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.R
import com.tetsoft.typego.TypedWordsViewModel
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.databinding.FragmentResultBinding
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.ui.fragment.game.GameViewModel
import com.tetsoft.typego.utils.AnimationManager
import com.tetsoft.typego.utils.Translation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResultFragment : Fragment() {

    private val resultViewModel: ResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val translation by lazy { Translation(requireContext()) }

    private var resultSaved = false

    private var _binding : FragmentResultBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWpm()
        initPreviousResultSection()
        initBestResultSection()
        initDetailsSectionForPrebuiltMode()
        storeResult()
        setupButtons()
    }

    private fun initWpm() {
        val animationManager = AnimationManager()
        val countAnimation = animationManager.getCountAnimation(
            0, resultViewModel.getWpm(),
            COUNT_UP_ANIMATION_DURATION
        )
        animationManager.applyCountAnimation(countAnimation, binding.tvWPM)
        countAnimation.start()
        binding.tvCpm.text = getString(R.string.cpm_pl, resultViewModel.getCpm())
        binding.tvCpm.animation = animationManager.getFadeInAnimation(COUNT_UP_ANIMATION_DURATION)
        binding.tvCpm.animation.start()
    }

    private fun setupButtons() {
        binding.tvCheckLog.setOnClickListener {
            if (resultViewModel.hasWordsLog() && resultViewModel.isGameCompleted) {
                val typedWordsViewModel : TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                typedWordsViewModel.selectTypedWordsList(resultViewModel.selectedList.value ?: emptyList())
                binding.root.findNavController().navigate(R.id.action_result_to_typedWords)
            } else {
                Toast.makeText(requireContext(), getString(R.string.typed_words_log_disabled), Toast.LENGTH_SHORT).show()
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
            val gameViewModel: GameViewModel by navGraphViewModels(R.id.main_navigation)
            gameViewModel.selectGameMode(resultViewModel.gameMode)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.resultFragment, true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
            binding.root.findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment, null, navOptions)
        }
    }

    private fun initPreviousResultSection() {
        if (!resultViewModel.isGameCompleted) {
            binding.previousResultSection.visibility = View.GONE
            return
        }
        with(binding) {
            val previousResult = resultViewModel.getPreviousWpm()
            previousResultSection.visibility =
                resultViewModel.getVisibilityForResult(previousResult)
            // TODO: Optimize it so we don't need to call difference calc from the fragment
            tvPreviousResult.text = (getString(R.string.previous_result_pl, previousResult))
            val diff = resultViewModel.subtractCurrentWpmAndOtherResult(previousResult)
            tvDifferenceWithPreviousResult.visibility =
                resultViewModel.getVisibilityForDifference(diff)
            tvDifferenceWithPreviousResult.text = resultViewModel.getDifferenceString(diff)
            tvDifferenceWithPreviousResult.setTextColor(resultViewModel.getDifferenceColor(diff))
            tvDifferenceWithPreviousResult.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            tvDifferenceWithPreviousResult.animation.startOffset =
                PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET
            tvDifferenceWithPreviousResult.animation.start()
        }

    }

    private fun initBestResultSection() {
        with(binding) {
            val bestResult = resultViewModel.getBestWpmByCurrentLanguage()
            bestResultSection.visibility = resultViewModel.getVisibilityForResult(bestResult)
            tvBestResult.text = (getString(R.string.best_result_pl, bestResult))
            if (!resultViewModel.isGameCompleted) return
            val diff = resultViewModel.subtractCurrentWpmAndOtherResult(bestResult)
            tvDifferenceWithBestResult.visibility = resultViewModel.getVisibilityForDifference(diff)
            tvDifferenceWithBestResult.text = resultViewModel.getDifferenceString(diff)
            tvDifferenceWithBestResult.setTextColor(resultViewModel.getDifferenceColor(diff))
            if (diff > 0) tvNewBestResult.visibility = View.VISIBLE
            tvDifferenceWithBestResult.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            tvDifferenceWithBestResult.animation.startOffset = BEST_RESULT_FADE_IN_ANIMATION_OFFSET
            tvDifferenceWithBestResult.animation.start()
        }

    }

    private fun initDetailsSectionForPrebuiltMode() {
        with(binding) {
            if (resultViewModel.gameMode is GameMode.Empty) {
                findNavController().navigateUp()
                return
            }
            if (resultViewModel.isPrebuiltGameMode) {
                tvLanguage.text =
                    getString(
                        R.string.selected_language_pl,
                        translation.get(resultViewModel.getLanguage())
                    )
                if (resultViewModel.isGameOnTime) {
                    tvSelectedTime.text =
                        getString(
                            R.string.selected_time_pl,
                            translation.get(TimeMode(resultViewModel.getTimeInSeconds()))
                        )
                }
                tvDictionary.text =
                    getString(
                        R.string.dictionary_pl,
                        translation.get(resultViewModel.getDictionary())
                    )
                tvCorrectWords.text =
                    getString(R.string.correct_words_pl, resultViewModel.getCorrectWords())
                tvIncorrectWords.text =
                    getString(R.string.incorrect_words_pl, resultViewModel.getIncorrectWords())
            }
            tvTextSuggestions.text =
                getString(
                    R.string.text_suggestions_pl,
                    translation.get(resultViewModel.gameMode.suggestionsActivated)
                )
            tvScreenOrientation.text =
                getString(
                    R.string.screen_orientation_pl,
                    translation.get(resultViewModel.getScreenOrientation())
                )
        }

    }

    private fun resultCalledFromHistory(): Boolean {
        val previousDestination =
            binding.root.findNavController().previousBackStackEntry?.destination?.id
        return previousDestination == R.id.accountFragment
    }

    private fun storeResult() {
        if (resultSaved) {
            return
        }
        if (!resultViewModel.isGameCompleted/**resultCalledFromHistory()**/) {
            return
        }
        if (resultViewModel.resultIsValid()) {
            resultViewModel.saveResult()
            resultSaved = true
            if (resultViewModel.getEarnedAchievementsCount(AchievementsList(requireContext())) > 0) {
                Snackbar.make(
                    binding.resultConstraintLayout,
                    getString(R.string.new_achievements_notification),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.check_profile) {
                        binding.root.findNavController()
                            .navigate(R.id.action_result_to_achievements)
                    }.show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_results_with_zero_wpm),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    companion object {
        private const val COUNT_UP_ANIMATION_DURATION = 1000L
        const val PREVIOUS_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION - 200L
        const val BEST_RESULT_FADE_IN_ANIMATION_OFFSET = COUNT_UP_ANIMATION_DURATION
    }
}