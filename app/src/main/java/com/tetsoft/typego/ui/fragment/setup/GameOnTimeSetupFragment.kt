package com.tetsoft.typego.ui.fragment.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.google.android.material.slider.Slider
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeModeList
import com.tetsoft.typego.databinding.FragmentGameOnTimeBinding
import com.tetsoft.typego.databinding.FragmentGameOnTimeSetupBinding
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.ui.custom.ValRadioButton
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.game.GameOnTimeViewModel
import com.tetsoft.typego.ui.fragment.game.GameViewModel
import com.tetsoft.typego.utils.Translation

class GameOnTimeSetupFragment : BaseFragment<FragmentGameOnTimeSetupBinding>() {

    private val viewModel : GameOnTimeSetupViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameOnTimeSetupBinding {
        return FragmentGameOnTimeSetupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupTimeModeSlider()
        setupLanguageSpinner()
        setupDictionaryTypeRadioButtons()
        setupSuggestionsCheckbox()
        setupScreenOrientationRadioButtons()
    }

    private fun setupButtons() {
        binding.tvTestSetup.setOnClickListener { binding.root.findNavController().navigateUp() }

        binding.buttonStartGame.setOnClickListener {

            val gameOnTime = com.tetsoft.typego.game.GameOnTime(
                0.0,
                0,
                0,
                binding.timemodeSlider.getSelectedTimeMode().timeInSeconds,
                binding.spinLanguageSelection.getSelectedLanguage().identifier,
                selectedDictionaryType.name,
                selectedScreenOrientation.name,
                binding.cbPredictiveText.isChecked,
                0,0,0L
            )
            val gameViewModel: GameOnTimeViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            gameViewModel.gameOnTime = gameOnTime
            binding.root.findNavController().navigate(R.id.action_gameOnTimeSetupFragment_to_gameOnTimeFragment)
        }
    }

    private val selectedDictionaryType: DictionaryType
        get() {
            val radioButton =
                binding.root.findViewById<ValRadioButton>(binding.rbDictionaryType.checkedRadioButtonId)
            return radioButton.assignedValue as DictionaryType
        }

    private val selectedScreenOrientation: ScreenOrientation
        get() {
            val screenOrientation =
                binding.root.findViewById<ValRadioButton>(binding.rbScreenOrientation.checkedRadioButtonId)
            return screenOrientation.assignedValue as ScreenOrientation
        }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            requireContext(),
            LanguageList().getTranslatableListInAlphabeticalOrder(requireContext())
        )
        binding.spinLanguageSelection.adapter = spinnerAdapter
        val lastUsedLanguage = viewModel.getLastUsedLanguageOrDefault()
        val languageIndex = spinnerAdapter.getItemIndexByLanguage(lastUsedLanguage)
        binding.spinLanguageSelection.setSelection(languageIndex)
    }

    private fun setupTimeModeSlider() {
        val translation = Translation(requireContext())
        with(binding) {
            timemodeSlider.setTimeModes(TimeModeList())
            timemodeSlider.selectTimeMode(viewModel.getLastUsedTimeModeOrDefault())
            timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
                val position = value.toInt()
                tvTimeStamp.text = translation.get(TimeModeList().getTimeModeByIndex(position))
                tvTimeStamp.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.pop_animation)
                )
            })
            tvTimeStamp.text = translation.get(TimeModeList().getTimeModeByIndex(binding.timemodeSlider.value.toInt()))
        }
    }

    private fun setupDictionaryTypeRadioButtons() {
        with(binding) {
            rbBasic.assignedValue = DictionaryType.BASIC
            rbEnhanced.assignedValue = DictionaryType.ENHANCED
            val lastSelectedRadioButton =
                rbDictionaryType.getChildAt(viewModel.getLastUsedDictionaryTypeOrDefault().id) as ValRadioButton
            lastSelectedRadioButton.isChecked = true
        }
    }

    private fun setupScreenOrientationRadioButtons() {
        with(binding) {
            rbPortrait.assignedValue = ScreenOrientation.PORTRAIT
            rbLandscape.assignedValue = ScreenOrientation.LANDSCAPE
            val lastSelectedRadioButton =
                rbScreenOrientation.getChildAt(viewModel.getLastUsedOrientationOrDefault().id) as ValRadioButton
            lastSelectedRadioButton.isChecked = true
        }
    }

    private fun setupSuggestionsCheckbox() {
        binding.cbPredictiveText.isChecked = viewModel.areSuggestionsUsedInLastResultOrDefault()
    }

}