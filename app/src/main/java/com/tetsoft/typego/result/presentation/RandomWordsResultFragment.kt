package com.tetsoft.typego.result.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.VisibilityMapper
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.core.utils.Animator
import com.tetsoft.typego.core.utils.TimeConvert
import com.tetsoft.typego.core.utils.Translation
import com.tetsoft.typego.core.utils.extensions.copyToClipboard
import com.tetsoft.typego.databinding.FragmentRandomWordsResultBinding
import com.tetsoft.typego.game.presentation.TimeGameViewModel
import com.tetsoft.typego.wordslog.presentation.TypedWordsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class RandomWordsResultFragment : BaseFragment<FragmentRandomWordsResultBinding>() {

    private val viewModel: RandomWordsResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val translation by lazy { Translation(requireContext()) }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomWordsResultBinding {
        return FragmentRandomWordsResultBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.resultIsValid()) {
            navigateUp()
            return
        }
        viewModel.saveResult()
        initWpm()
        initCurrentResultData()
        initAttributesData()
        initButtonActions()
        val newAchievements = viewModel.getEarnedAchievementsCount()
        if (newAchievements > 0) {
            binding.buttonCheckAchievements.text = getString(R.string.check_achievements_with_count, + newAchievements)
        }
        binding.buttonClose.isEnabled = false
        binding.buttonStartOver.isEnabled = false
        lifecycleScope.launch {
            delay(BUTTONS_DISABLE_DURATION)
            binding.buttonClose.isEnabled = true
            binding.buttonStartOver.isEnabled = true
        }
    }

    private fun initWpm() {
        val countAnimation = Animator.CountUp(COUNT_UP_ANIMATION_DURATION, viewModel.getWpm()).get()
        countAnimation.addUpdateListener { anim ->
            if (context == null) return@addUpdateListener
            binding.wpm.text = anim.animatedValue.toString()
        }
        countAnimation.start()
    }

    private fun initCurrentResultData() {
        binding.lastWpm.text = viewModel.getLastResultOrBlank()
        binding.lastWpmDescription.text = requireContext().getString(
            viewModel.getLastResultDifferenceStringResId(), abs(viewModel.getLastResultDifference()?: 0)
        )
        binding.bestWpm.text = viewModel.getBestResultOrBlank()
        binding.bestWpmDescription.text = requireContext().getString(
            viewModel.getBestResultDifferenceStringResId(), abs(viewModel.getBestResultDifference() ?: 0)
        )
        binding.currentCpm.text = viewModel.getCpm()
        binding.words.text = viewModel.getWordsWritten()
        binding.wordsDescription.text = requireContext().getString(R.string.correct_words_out_of_total, viewModel.getCorrectWordsCount())
    }

    private fun initAttributesData() {
        binding.resultAttributeLanguage.text = translation.get(viewModel.getLanguage())
        binding.resultAttributeLanguage.setOnClickListener {
            showToast(getString(R.string.selected_language_pl, translation.get(viewModel.getLanguage())))
        }

        binding.resultAttributeDictionary.text = translation.get(viewModel.getDictionary())
        binding.resultAttributeDictionary.setOnClickListener {
            showToast(getString(R.string.dictionary_pl, translation.get(viewModel.getDictionary())))
        }

        binding.resultAttributeTime.text = TimeConvert.convertSecondsToStamp(viewModel.getTimeInSeconds())
        binding.resultAttributeTime.setOnClickListener {
            showToast(getString(R.string.chosen_time_pl, TimeConvert.convertSecondsToStamp(viewModel.getTimeInSeconds())))
        }

        binding.resultAttributeSeed.text = viewModel.getSeedOrBlankSign()
        binding.resultAttributeSeed.setOnClickListener {
            if (!viewModel.seedIsEmpty()) {
                showToast(getString(R.string.seed_copy_hint, viewModel.getSeedOrBlankSign()))
            } else {
                showToast(R.string.seed_not_set)
            }
        }
        binding.resultAttributeSeed.setOnLongClickListener {
            if (!viewModel.seedIsEmpty()) {
                showToast(R.string.seed_copied)
                requireContext().copyToClipboard("TypeGo seed", viewModel.getSeedOrBlankSign())
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }

        binding.resultAttributeSuggestions.text = translation.getAsEnabledDisabled(viewModel.areSuggestionsActivated())
        binding.resultAttributeSuggestions.setOnClickListener {
            showToast(getString(R.string.text_suggestions_pl, translation.getAsEnabledDisabled(viewModel.areSuggestionsActivated())))
        }

        binding.resultAttributeOrientation.text = translation.get(viewModel.getScreenOrientation())
        binding.resultAttributeOrientation.setOnClickListener {
            showToast(getString(R.string.screen_orientation_pl, translation.get(viewModel.getScreenOrientation())))
        }
    }

    private fun initButtonActions() {
        binding.buttonCheckWordsLog.visibility = VisibilityMapper.VisibleGone(viewModel.getTypedWordsList().isNotEmpty()).get()
        binding.buttonCheckWordsLog.setOnClickListener {
            val typedWordsViewModel: TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            typedWordsViewModel.selectTypedWordsList(viewModel.getTypedWordsList())
            navigateTo(R.id.action_randomWordsResultFragment_to_typedWordsFragment)
        }
        binding.buttonCheckAchievements.setOnClickListener {
            navigateTo(R.id.action_randomWordsResultFragment_to_achievementsFragment)
        }
        binding.buttonStartOver.setOnClickListener {
            val timeGameViewModel: TimeGameViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            timeGameViewModel.gameSettings = viewModel.getGameSettings()
            navigateTo(R.id.action_randomWordsResultFragment_to_timeGameFragment)
        }
        binding.buttonClose.setOnClickListener {
            navigateUp()
        }
    }

    private companion object {
        const val COUNT_UP_ANIMATION_DURATION = 1000L
        const val BUTTONS_DISABLE_DURATION = 300L
    }

}