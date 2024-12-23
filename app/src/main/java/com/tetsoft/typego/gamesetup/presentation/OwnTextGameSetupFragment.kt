package com.tetsoft.typego.gamesetup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.TimeModeList
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.core.utils.Translation
import com.tetsoft.typego.databinding.FragmentOwnTextGameSetupBinding
import com.tetsoft.typego.gamesetup.domain.GameSetupInformation

class OwnTextGameSetupFragment : BaseFragment<FragmentOwnTextGameSetupBinding>(),
    GameSetupInformation {

    companion object {
        private val SAMPLE_TEXT_RESOURCES = listOf(
            R.string.user_text_sample_1,
            R.string.user_text_sample_2,
            R.string.user_text_sample_3
        )
    }

    private fun getSampleTextList() : List<String> {
        val sampleTexts = arrayListOf<String>()
        SAMPLE_TEXT_RESOURCES.forEach {
            sampleTexts.add(getString(it))
        }
        return sampleTexts
    }

    private val viewModel: OwnTextGameSetupViewModel by hiltNavGraphViewModels(R.id.main_navigation)
    private lateinit var parentViewModel : GameSetupViewModel

    private lateinit var sliderListener: Slider.OnChangeListener

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOwnTextGameSetupBinding {
        return FragmentOwnTextGameSetupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel = ViewModelProvider(requireActivity())[GameSetupViewModel::class.java]
        setupButtons()
        setupTimeModeSlider()
        viewModel.initSampleTextIterator(getSampleTextList())
        binding.textSuggestions.isChecked = viewModel.areSuggestionsUsedInLastResultOrDefault()
        binding.screenOrientation.selectIndex(viewModel.getLastUsedOrientationOrDefault().id)
        binding.userText.setText(viewModel.getLastUsedUserText())
        if (binding.userText.text?.isBlank() == true) {
            binding.userText.setText(getString(R.string.user_text_sample_1))
        }
        setupListeners()
        parentViewModel.setOwnTextGameSettings(getGameSettings())
        binding.userTextValidation.text =
            getString(R.string.error_setup_user_text_too_small, parentViewModel.getMinimumWordsConstraint())
        binding.userTextValidation.isChecked =
            parentViewModel.userTextIsValid(viewModel.sanitizeUserTextInput(binding.userText.text.toString()))
    }

    private fun setupButtons() {
        binding.buttonClearText.setOnClickListener {
            Toast.makeText(requireContext(), R.string.user_text_clear_button_prompt, Toast.LENGTH_SHORT).show()
        }
        binding.buttonClearText.setOnLongClickListener {
            binding.userText.text?.clear()
            return@setOnLongClickListener true
        }
        binding.buttonRefreshText.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.user_text_refresh_button_prompt), Toast.LENGTH_SHORT).show()
        }
        binding.buttonRefreshText.setOnLongClickListener {
            binding.userText.setText(viewModel.getNextSampleText())
            return@setOnLongClickListener true
        }

    }

    override fun getGameSettings(): GameSettings.ForUserText {
        val userText = viewModel.sanitizeUserTextInput(binding.userText.text.toString())
        return GameSettings.ForUserText(
            userText,
            binding.timemodeSlider.getSelectedTimeMode().timeInSeconds,
            binding.textSuggestions.isChecked,
            binding.screenOrientation.getSelectedValue(),
            true
        )
    }

    private fun setupTimeModeSlider() {
        val translation = Translation(requireContext())
        with(binding) {
            sliderListener = Slider.OnChangeListener { _, value, _ ->
                val position = value.toInt()
                selectedTime.text = translation.get(TimeModeList().getTimeModeByIndex(position))
                selectedTime.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.pop_animation)
                )
                parentViewModel.setOwnTextGameSettings(getGameSettings())
            }
            timemodeSlider.setTimeModes(TimeModeList())
            timemodeSlider.selectTimeMode(viewModel.getLastUsedTimeModeOrDefault())
            timemodeSlider.addOnChangeListener(sliderListener)
            selectedTime.text = translation.get(TimeModeList().getTimeModeByIndex(binding.timemodeSlider.value.toInt()))
        }
    }

    private fun setupListeners() {
        binding.userText.doAfterTextChanged { text ->
            val areSettingsValid = parentViewModel.userTextIsValid(viewModel.sanitizeUserTextInput(text.toString()))
            binding.userTextValidation.isChecked = areSettingsValid
            parentViewModel.setOwnTextGameSettings(getGameSettings())
        }
        binding.textSuggestions.setOnClickListener { parentViewModel.setOwnTextGameSettings(getGameSettings()) }
        binding.screenOrientation.setOnCheckedChangeListener { _, _ ->
            parentViewModel.setOwnTextGameSettings(getGameSettings())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.timemodeSlider.removeOnChangeListener(sliderListener)
    }
}