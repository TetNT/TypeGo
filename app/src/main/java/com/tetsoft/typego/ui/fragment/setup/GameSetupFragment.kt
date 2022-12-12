package com.tetsoft.typego.ui.fragment.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.google.android.material.slider.Slider
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeModeList
import com.tetsoft.typego.databinding.FragmentGameSetupBinding
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.ui.custom.BaseFragment
import com.tetsoft.typego.ui.custom.ValRadioButton
import com.tetsoft.typego.ui.fragment.game.GameViewModel
import com.tetsoft.typego.utils.TimeConvert
import com.tetsoft.typego.utils.Translation

class GameSetupFragment : BaseFragment<FragmentGameSetupBinding>() {

    private val availableTimeModes = TimeModeList()

    private val viewModel: GameOnTimeSetupViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameSetupBinding {
        return FragmentGameSetupBinding.inflate(inflater, container, false)
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

            val gameOnTime = GameOnTime(
                binding.spinLanguageSelection.getSelectedLanguage(),
                binding.timemodeSlider.getSelectedTimeMode(),
                selectedDictionaryType,
                binding.cbPredictiveText.isChecked,
                selectedScreenOrientation
            )
            val gameViewModel: GameViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            gameViewModel.selectGameMode(gameOnTime)
            binding.root.findNavController().navigate(R.id.action_gameSetup_to_game)
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
        with(binding) {
            timemodeSlider.setTimeModes(availableTimeModes)
            timemodeSlider.selectTimeMode(viewModel.getLastUsedTimeModeOrDefault())
            timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
                val position = value.toInt()
                tvTimeStamp.text = TimeConvert.convertSeconds(
                    requireContext(),
                    availableTimeModes.getTimeModeByIndex(position).timeInSeconds
                )
                tvTimeStamp.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.pop_animation)
                )
            })
            val progressInSeconds =
                availableTimeModes.getTimeModeByIndex(binding.timemodeSlider.value.toInt()).timeInSeconds
            tvTimeStamp.text = TimeConvert.convertSeconds(requireContext(), progressInSeconds)
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