package com.tetsoft.typego.ui.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.history.GameOnTimeHistoryAdapter
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.databinding.FragmentGameHistoryBinding
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.result.GameOnTimeResultViewModel

class GameHistoryFragment : BaseFragment<FragmentGameHistoryBinding>() {

    private val viewModel : GameHistoryViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLanguageSpinner()
        binding.bAchievements.setOnClickListener {
            findNavController().navigate(R.id.action_gameHistoryFragment_to_achievementsFragment)
        }
        binding.bStatistics.setOnClickListener {
            findNavController().navigate(R.id.action_gameHistoryFragment_to_statisticsFragment)
        }
        viewModel.selectedLanguage.observe(viewLifecycleOwner) {
            binding.averageWpmCounter.text = "-"
            binding.bestResultCounter.text = "-"
            binding.testsPassedCounter.text = "-"
            val selectedLanguage = binding.spinnerResultsLanguageSelection.getSelectedLanguage()
            val resultsByLanguage = viewModel.getOnTimeHistory(selectedLanguage)
            val listener = object : GameOnTimeHistoryAdapter.RecyclerViewOnClickListener {
                override fun onClick(v: View?, position: Int) {
                    val resultViewModel: GameOnTimeResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                    resultViewModel.result = resultsByLanguage[position]
                    resultViewModel.isGameCompleted = false
                    binding.root.findNavController().navigate(R.id.action_gameHistoryFragment_to_gameOnTimeResultFragment)
                }
            }
            binding.rvHistoryGameOnTime.adapter = GameOnTimeHistoryAdapter(requireContext(), resultsByLanguage, listener)
            binding.rvHistoryGameOnTime.animation = viewModel.getGameHistoryEnteringAnimation()
            if (viewModel.averageWpmCanBeShown(resultsByLanguage)) {
                binding.averageWpmCardview.setOnClickListener{ }
                binding.averageWpmCounter.text = viewModel.getAverageWpm(resultsByLanguage).toString()
            } else {
                binding.averageWpmCardview.setOnClickListener {
                    Toast.makeText(requireContext(), getString(R.string.msg_average_wpm_unavailable), Toast.LENGTH_SHORT).show()
                }
                binding.averageWpmCounter.text = "-"
            }
            if (!viewModel.historyCanBeShown(resultsByLanguage)) {
                binding.tvPassedTestsInfo.text = getString(R.string.msg_nothing_to_show)
                return@observe
            }
            binding.tvPassedTestsInfo.text = getString(R.string.msg_passed_tests_information)
            binding.testsPassedCounter.text = resultsByLanguage.size.toString()
            binding.bestResultCounter.text = viewModel.getBestWpm(resultsByLanguage).toString()
        }
    }

    private fun initLanguageSpinner() {
        val languages = LanguageList().getTranslatableListInAlphabeticalOrder(requireContext())
        // an option to show the whole history
        val spinnerItem = LanguageSpinnerItem(
            Language(Language.ALL),
            requireContext().getString(R.string.ALL),
            R.drawable.ic_language
        )
        languages.add(0, spinnerItem)
        val spinnerAdapter = LanguageSpinnerAdapter(requireContext(), languages)
        binding.spinnerResultsLanguageSelection.adapter = spinnerAdapter
        binding.spinnerResultsLanguageSelection.setSelection(0)
        binding.spinnerResultsLanguageSelection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectLanguage(binding.spinnerResultsLanguageSelection.getSelectedLanguage())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameHistoryBinding {
        return FragmentGameHistoryBinding.inflate(inflater, container, false)
    }
}