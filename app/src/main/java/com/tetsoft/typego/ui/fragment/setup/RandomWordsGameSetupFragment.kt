package com.tetsoft.typego.ui.fragment.setup

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
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.data.game.GameSettings
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.timemode.TimeModeList
import com.tetsoft.typego.databinding.FragmentRandomWordsGameSetupBinding
import com.tetsoft.typego.ui.fragment.BaseFragment
import com.tetsoft.typego.ui.fragment.GameSetupInformation
import com.tetsoft.typego.utils.Translation

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
        binding.rbDictionaryType.selectIndex(viewModel.getLastUsedDictionaryTypeOrDefault().id)
        binding.cbPredictiveText.isChecked = viewModel.areSuggestionsUsedInLastResultOrDefault()
        binding.rbScreenOrientation.selectIndex(viewModel.getLastUsedOrientationOrDefault().id)
        setupListeners()
        parentViewModel.setRandomWordsGameSettings(getGameSettings())
    }

    override fun getGameSettings(): GameSettings.ForRandomlyGeneratedWords {
        return GameSettings.ForRandomlyGeneratedWords(
            binding.spinLanguageSelection.getSelectedLanguage().identifier,
            binding.timemodeSlider.getSelectedTimeMode().timeInSeconds,
            binding.rbDictionaryType.getSelectedValue(),
            binding.etSeed.text.toString(),
            binding.cbPredictiveText.isChecked,
            binding.rbScreenOrientation.getSelectedValue(),
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
                tvTimeStamp.text = translation.get(TimeModeList().getTimeModeByIndex(position))
                tvTimeStamp.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.pop_animation)
                )
                parentViewModel.setRandomWordsGameSettings(getGameSettings())
            })
            tvTimeStamp.text = translation.get(TimeModeList().getTimeModeByIndex(binding.timemodeSlider.value.toInt()))
        }
    }

    private fun setupLanguageSpinner() {
        val spinnerAdapter = LanguageSpinnerAdapter(
            requireContext(),
            LanguageList().getLocalized(requireContext())
        )
        
        binding.spinLanguageSelection.adapter = spinnerAdapter
        val lastUsedLanguage = viewModel.getLastUsedLanguageOrDefault()
        val languageIndex = spinnerAdapter.getItemIndexByLanguage(lastUsedLanguage)
        binding.spinLanguageSelection.setSelection(languageIndex)
    }
    
    private fun setupListeners() {
        binding.spinLanguageSelection.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parentViewModel.setRandomWordsGameSettings(getGameSettings())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.timemodeSlider.addOnChangeListener(Slider.OnChangeListener { _, _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        })
        binding.rbDictionaryType.setOnCheckedChangeListener { _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
        binding.etSeed.doOnTextChanged { _, _, _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
        binding.cbPredictiveText.setOnClickListener { parentViewModel.setRandomWordsGameSettings(getGameSettings()) }
        binding.rbScreenOrientation.setOnCheckedChangeListener { _, _ ->
            parentViewModel.setRandomWordsGameSettings(getGameSettings())
        }
    }
}