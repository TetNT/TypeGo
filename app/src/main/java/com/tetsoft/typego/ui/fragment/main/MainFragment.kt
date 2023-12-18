package com.tetsoft.typego.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.databinding.FragmentMainBinding
import com.tetsoft.typego.extensions.withColor
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.game.GameOnTimeViewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClickListeners()
        setupLanguageSpinner()
        binding.tvAppVersion.text = BuildConfig.VERSION_NAME
    }

    private fun setupButtonsOnClickListeners() {
        binding.buttonBasicTestStart.setOnClickListener {
            startBasicTest()
        }
        binding.buttonCustomTestStart.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_mainFragment_to_gameOnTimeSetupFragment)
        }
        binding.buttonProfileOpen.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_mainFragment_to_gameHistoryFragment)
        }
        binding.buttonPreviousTestStart.setOnClickListener {
            startPreviousTest()
        }
        binding.buttonReleaseNotesOpen.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_main_to_releaseNotes)
        }
    }

    private fun startBasicTest() {
        val basicGameMode = GameOnTime(
            0.0,
            0,
            0,
            DEFAULT_TIME_MODE.timeInSeconds,
            binding.spinnerBasicTestLanguageSelection.getSelectedLanguage().identifier,
            DEFAULT_DICTIONARY_TYPE.name,
            DEFAULT_SCREEN_ORIENTATION.name,
            DEFAULT_SUGGESTIONS_ACTIVATED,
            ""
        )
        val gameViewModel: GameOnTimeViewModel by navGraphViewModels(R.id.main_navigation)
        gameViewModel.gameOnTime = basicGameMode
        binding.root.findNavController().navigate(R.id.action_mainFragment_to_gameOnTimeFragment)
    }

    private fun startPreviousTest() {
        if (viewModel.userHasPreviousGames()) {
            val gameViewModel: GameOnTimeViewModel by navGraphViewModels(R.id.main_navigation)
            gameViewModel.gameOnTime = viewModel.getMostRecentGameSettings()
            binding.root.findNavController().navigate(R.id.action_mainFragment_to_gameOnTimeFragment)
        } else Snackbar.make(binding.root, R.string.msg_no_previous_games, Snackbar.LENGTH_LONG)
            .withColor(R.color.main_green, R.color.cardview_main_color)
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