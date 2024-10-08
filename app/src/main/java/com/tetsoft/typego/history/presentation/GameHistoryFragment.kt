package com.tetsoft.typego.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.databinding.FragmentGameHistoryBinding
import com.tetsoft.typego.result.presentation.OwnTextResultViewModel
import com.tetsoft.typego.result.presentation.RandomWordsResultViewModel

class GameHistoryFragment : BaseFragment<FragmentGameHistoryBinding>() {

    private val viewModel : GameHistoryViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bAchievements.setOnClickListener {
            navigateTo(R.id.action_gameHistoryFragment_to_achievementsFragment)
        }
        binding.bStatistics.setOnClickListener {
            navigateTo(R.id.action_gameHistoryFragment_to_statisticsFragment)
        }
        binding.averageWpmCounter.text = viewModel.getAverageWpmString()
        binding.bestResultCounter.text = viewModel.getBestWpmString()
        binding.testsPassedCounter.text = viewModel.getTotalResultsCountString()
        val listener = object : GameHistoryAdapter.RecyclerViewOnClickListener {
            override fun onClick(v: View?, position: Int) {
                val selectedItem = viewModel.getHistory()[position]
                if (selectedItem is RandomWords) {
                    val resultViewModel: RandomWordsResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                    resultViewModel.setRandomWordsResult(selectedItem)
                    resultViewModel.isGameCompleted = false
                    navigateTo(R.id.action_gameHistoryFragment_to_randomWordsResultFragment)
                } else if (selectedItem is OwnText) {
                    val resultViewModel : OwnTextResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                    resultViewModel.setOwnTextResult(selectedItem)
                    resultViewModel.setTypedWordsList(emptyList())
                    navigateTo(R.id.action_gameHistoryFragment_to_ownTextResultFragment)
                }
            }
        }
        binding.rvHistory.adapter = GameHistoryAdapter(requireContext(), viewModel.getHistory(), listener)
        binding.rvHistory.animation = viewModel.getGameHistoryEnteringAnimation()
        if (!viewModel.historyCanBeShown()) {
            binding.tvPassedTestsInfo.text = getString(R.string.msg_nothing_to_show)
            return
        }
        binding.tvPassedTestsInfo.text = getString(R.string.msg_passed_tests_information)
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameHistoryBinding {
        return FragmentGameHistoryBinding.inflate(inflater, container, false)
    }
}