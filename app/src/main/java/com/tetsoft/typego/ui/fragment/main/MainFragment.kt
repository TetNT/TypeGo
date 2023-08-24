package com.tetsoft.typego.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.ui.custom.withColor
import com.tetsoft.typego.ui.fragment.game.GameViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private var _binding : FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClickListeners()
        setupLanguageSpinner()
        binding.tvAppVersion.text = BuildConfig.VERSION_NAME
        viewModel.removeVersioning()
    }

    private fun setupButtonsOnClickListeners() {
        binding.buttonBasicTestStart.setOnClickListener {
            startBasicTest()
        }
        binding.buttonCustomTestStart.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_main_to_gameSetup)
        }
        binding.buttonProfileOpen.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_main_to_account)
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
            binding.spinnerBasicTestLanguageSelection.getSelectedLanguage(),
            DEFAULT_TIME_MODE,
            DEFAULT_DICTIONARY_TYPE,
            DEFAULT_SUGGESTIONS_ACTIVATED,
            DEFAULT_SCREEN_ORIENTATION
        )
        val gameViewModel: GameViewModel by navGraphViewModels(R.id.main_navigation)
        gameViewModel.selectGameMode(basicGameMode)
        binding.root.findNavController().navigate(R.id.action_main_to_game)
    }

    private fun startPreviousTest() {
        if (viewModel.userHasPreviousGames()) {
            val gameViewModel: GameViewModel by navGraphViewModels(R.id.main_navigation)
            gameViewModel.selectGameMode(viewModel.getPreviousGameSettings())
            binding.root.findNavController().navigate(R.id.action_main_to_game)
        } else Snackbar.make(binding.root, R.string.msg_no_previous_games, Snackbar.LENGTH_LONG)
            .withColor(R.color.main_green, R.color.bg_dark_gray)
            .show()
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            requireContext(),
            LanguageList().getTranslatableListInAlphabeticalOrder(requireContext())
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