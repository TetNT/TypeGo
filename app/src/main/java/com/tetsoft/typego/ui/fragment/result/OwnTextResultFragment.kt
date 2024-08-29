package com.tetsoft.typego.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.tetsoft.typego.R
import com.tetsoft.typego.databinding.FragmentOwnTextResultBinding
import com.tetsoft.typego.ui.VisibilityMapper
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.game.TimeGameViewModel
import com.tetsoft.typego.ui.fragment.typedwords.TypedWordsViewModel
import com.tetsoft.typego.utils.Animator
import com.tetsoft.typego.utils.TimeConvert
import com.tetsoft.typego.utils.Translation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs


class OwnTextResultFragment : BaseFragment<FragmentOwnTextResultBinding>() {

    private val viewModel: OwnTextResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val translation by lazy { Translation(requireContext()) }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOwnTextResultBinding {
        return FragmentOwnTextResultBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveResultIfValid()
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
        binding.currentCpm.text = viewModel.getCurrentCpm()
        binding.words.text = viewModel.getWordsWritten()
        binding.wordsDescription.text = requireContext().getString(R.string.correct_words_out_of_total, viewModel.getCorrectWordsCount())
    }

    private fun initAttributesData() {
        binding.resultAttributeText.text = viewModel.getUserText()
        binding.resultAttributeChosenTime.text = TimeConvert.convertSecondsToStamp(viewModel.getChosenTime())
        binding.resultAttributeTimeSpent.text = TimeConvert.convertSecondsToStamp(viewModel.getTimeSpent())
        binding.resultAttributeOrientation.text = translation.get(viewModel.getScreenOrientation())
        binding.resultAttributeSuggestions.text = translation.get(viewModel.suggestionsActivated())
    }

    private fun initButtonActions() {
        binding.buttonCheckWordsLog.visibility = VisibilityMapper.VisibleGone(viewModel.getTypedWordsList().isNotEmpty()).get()
        binding.buttonCheckWordsLog.setOnClickListener {
            val typedWordsViewModel: TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            typedWordsViewModel.selectTypedWordsList(viewModel.getTypedWordsList())
            findNavController().navigate(R.id.action_ownTextResultFragment_to_typedWordsFragment)
        }
        binding.buttonCheckAchievements.setOnClickListener {
            findNavController().navigate(R.id.action_ownTextResultFragment_to_achievementsFragment)
        }
        binding.buttonStartOver.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.ownTextResultFragment, true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
            val timeGameViewModel: TimeGameViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            timeGameViewModel.gameSettings = viewModel.getGameSettings()
            findNavController().navigate(R.id.action_ownTextResultFragment_to_gameOnTimeFragment, null, navOptions)
        }
        binding.buttonClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private companion object {
        const val COUNT_UP_ANIMATION_DURATION = 1000L
        const val BUTTONS_DISABLE_DURATION = 300L
    }

}