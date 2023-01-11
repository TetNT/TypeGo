package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.GamesHistoryAdapter
import com.tetsoft.typego.adapter.GamesHistoryAdapter.RecyclerViewOnClickListener
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.databinding.FragmentAccountBinding
import com.tetsoft.typego.ui.custom.BaseFragment
import com.tetsoft.typego.ui.fragment.result.ResultViewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private var inDescendingOrder = true

    private val viewModel : AccountViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLanguageSpinner()
        binding.bAchievements.setOnClickListener {
            navigate(R.id.action_account_to_achievements)
        }
        binding.bStatistics.setOnClickListener {
            navigate(R.id.action_account_to_statistics)
        }
        viewModel.selectedLanguage.observe(viewLifecycleOwner) {
            binding.averageWpmCounter.text = "-"
            binding.bestResultCounter.text = "-"
            binding.testsPassedCounter.text = "-"
            val selectedLanguage = binding.spinnerResultsLanguageSelection.getSelectedLanguage()
            val resultsByLanguage = viewModel.getResults(selectedLanguage, inDescendingOrder)
            val listener = RecyclerViewOnClickListener { _: View?, position: Int ->
                val resultViewModel : ResultViewModel by hiltNavGraphViewModels(R.id.main_navigation)
                resultViewModel.result = resultsByLanguage[position]
                resultViewModel.selectGameMode(resultViewModel.result!!.gameMode)
                resultViewModel.setGameCompleted(false)
                navigate(R.id.action_account_to_result)
            }
            binding.rvPassedTests.adapter = GamesHistoryAdapter(context, resultsByLanguage, listener)
            binding.rvPassedTests.animation = viewModel.getGameHistoryEnteringAnimation()
            if (viewModel.averageWpmCanBeShown(resultsByLanguage)) {
                binding.averageWpmCounter.text = viewModel.getAverageWpm(resultsByLanguage).toString()
            } else {
                binding.averageWpmCounter.text = "-"
            }
            if (!viewModel.historyCanBeShown(resultsByLanguage)) {
                binding.tvPassedTestsInfo.text = getString(R.string.msg_nothing_to_show)
                return@observe
            }
            binding.tvPassedTestsInfo.text = getString(R.string.msg_passed_tests_information)
            binding.testsPassedCounter.text = resultsByLanguage.size.toString()
            binding.bestResultCounter.text = viewModel.getBestResultWpm(resultsByLanguage).toString()

        }
    }

    private fun initLanguageSpinner() {
        val languages = LanguageList().getTranslatableListInAlphabeticalOrder(requireContext())
        // an option to show the whole stats
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

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater, container, false)
    }
}