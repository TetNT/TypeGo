package com.tetsoft.typego.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R
import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.LanguageList
import com.tetsoft.typego.core.domain.TimeMode
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.core.ui.LanguageSpinnerAdapter
import com.tetsoft.typego.core.utils.extensions.withAppColors
import com.tetsoft.typego.databinding.FragmentMainBinding
import com.tetsoft.typego.game.presentation.TimeGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private var initialCheckCompleted = false

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClickListeners()
        setupLanguageSpinner()
        binding.tvAppVersion.text = BuildConfig.VERSION_NAME
        if (!initialCheckCompleted && viewModel.hasUncheckedNotes()) {
            navigateTo(
                R.id.action_main_to_keyNotes,
                Bundle().apply { putBoolean("show_all", false) })
        }
        initialCheckCompleted = true
    }

    private fun setupButtonsOnClickListeners() {
        binding.buttonBasicTestStart.setOnClickListener { startBasicTest() }
        binding.buttonCustomTestStart.setOnClickListener { navigateTo(R.id.action_mainFragment_to_gameSetupFragment) }
        binding.buttonProfileOpen.setOnClickListener { navigateTo(R.id.action_mainFragment_to_gameHistoryFragment) }
        binding.buttonPreviousTestStart.setOnClickListener { startPreviousTest() }
        binding.buttonReleaseNotesOpen.setOnClickListener { navigateTo(R.id.action_main_to_releaseNotes) }
    }

    private fun startBasicTest() {
        val basicGameMode = GameSettings.ForRandomlyGeneratedWords(
            binding.spinnerBasicTestLanguageSelection.getSelectedLanguage().identifier,
            DEFAULT_TIME_MODE.timeInSeconds,
            DEFAULT_DICTIONARY_TYPE,
            "",
            DEFAULT_SUGGESTIONS_ACTIVATED,
            DEFAULT_SCREEN_ORIENTATION,
            true
        )
        val gameViewModel: TimeGameViewModel by navGraphViewModels(R.id.main_navigation)
        gameViewModel.gameSettings = basicGameMode
        navigateTo(R.id.action_mainFragment_to_timeGameFragment)
    }

    private fun startPreviousTest() {
        if (viewModel.userHasPreviousGames()) {
            val gameViewModel: TimeGameViewModel by navGraphViewModels(R.id.main_navigation)
            gameViewModel.gameSettings = viewModel.getMostRecentGameSettings() as GameSettings.TimeBased
            navigateTo(R.id.action_mainFragment_to_timeGameFragment)
        } else Snackbar.make(binding.root, R.string.msg_no_previous_games, Snackbar.LENGTH_LONG)
            .withAppColors()
            .show()
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            requireContext(),
            LanguageList().getLocalized(requireContext())
        )
        binding.spinnerBasicTestLanguageSelection.adapter = spinnerAdapter
        val lastLanguage = viewModel.getLastUsedLanguageOrDefault()
        val languageIndex = spinnerAdapter.getItemIndexByLanguage(lastLanguage)
        binding.spinnerBasicTestLanguageSelection.setSelection(
            languageIndex
        )
    }

    companion object {
        private val DEFAULT_TIME_MODE = TimeMode(60)
        private val DEFAULT_DICTIONARY_TYPE = DictionaryType.BASIC
        private const val DEFAULT_SUGGESTIONS_ACTIVATED = true
        private val DEFAULT_SCREEN_ORIENTATION = ScreenOrientation.PORTRAIT
    }

}