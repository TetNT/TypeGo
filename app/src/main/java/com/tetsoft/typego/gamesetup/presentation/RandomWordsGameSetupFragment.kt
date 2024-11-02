package com.tetsoft.typego.gamesetup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.doOnTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.tetsoft.typego.R
import com.tetsoft.typego.core.ui.LanguageSpinnerAdapter
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.LanguageList
import com.tetsoft.typego.core.domain.TimeModeList
import com.tetsoft.typego.databinding.FragmentRandomWordsGameSetupBinding
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.core.utils.Translation
import com.tetsoft.typego.gamesetup.domain.GameSetupInformation

class RandomWordsGameSetupFragment : BaseFragment<FragmentRandomWordsGameSetupBinding>(), GameSetupInformation {

    private val viewModel : RandomWordsGameSetupViewModel by hiltNavGraphViewModels(R.id.main_navigation)
    private lateinit var parentViewModel : GameSetupViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomWordsGameSetupBinding {
        return FragmentRandomWordsGameSetupBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel = ViewModelProvider(requireActivity())[GameSetupViewModel::class.java]
        setupTimeModeSlider()
        setupLanguageSpinner()
        binding.etSeed.setText(viewModel.getLastUsedSeed())
        binding.dictionaryType.selectIndex(viewModel.getLastUsedDictionaryTypeOrDefault().id)
        binding.textSuggestions.isChecked = viewModel.areSuggestionsUsedInLastResultOrDefault()
        binding.screenOrientation.selectIndex(viewModel.getLastUsedOrientationOrDefault().id)
        setupListeners()
        parentViewModel.setRandomWordsGameSettings(getGameSettings())
    }

    override fun getGameSettings(): GameSettings.ForRandomlyGeneratedWords {
        return GameSettings.ForRandomlyGeneratedWords(
            binding.languageSelection.getSelectedLanguage().identifier,
            binding.timemodeSlider.getSelectedTimeMode().timeInSeconds,
            binding.dictionaryType.getSelectedValue(),
            binding.etSeed.text.toString(),
            binding.textSuggestions.isChecked,
            binding.screenOrientation.getSelectedValue(),
            true
        )
    }

    private fun setupTimeModeSlider() {
        val translation = Translation(requireContext())
        with(binding) {
            timemodeSlider.setTimeModes(TimeModeList())
            timemodeSlider.selectTimeMode(viewModel.getLastUsedTimeModeOrDefault())
            timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
                val position = value.toInt()
                selectedTime.text = translation.get(TimeModeList().getTimeModeByIndex(position))
                selectedTime.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.pop_animation)
                )
                parentViewModel.setRandomWordsGameSettings(getGameSettings())
            })
            selectedTime.text = translation.get(TimeModeList().getTimeModeByIndex(binding.timemodeSlider.value.toInt()))
        }
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            requireContext(),
            LanguageList().getLocalized(requireContext())
        )
        
        binding.languageSelection.adapter = spinnerAdapter
        val lastUsedLanguage = viewModel.getLastUsedLanguageOrDefault()
        val languageIndex = spinnerAdapter.getItemIndexByLanguage(lastUsedLanguage)
        binding.languageSelection.setSelection(languageIndex)
    }
    
    private fun setupListeners() {
        binding.languageSelection.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parentViewModel.setRandomWordsGameSettings(getGameSettings())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        })
        binding.dictionaryType.setOnCheckedChangeListener { _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
        binding.etSeed.doOnTextChanged { _, _, _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
        binding.textSuggestions.setOnClickListener { parentViewModel.setRandomWordsGameSettings(getGameSettings()) }
        binding.screenOrientation.setOnCheckedChangeListener { _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
    }
}