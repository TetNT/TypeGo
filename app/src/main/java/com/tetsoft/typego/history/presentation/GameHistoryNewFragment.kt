package com.tetsoft.typego.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.tetsoft.typego.core.domain.LanguageList
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.core.ui.LanguageSpinnerAdapter
import com.tetsoft.typego.core.ui.LocalizedSpinnerAdapter
import com.tetsoft.typego.databinding.FragmentGameHistoryNewBinding
import com.tetsoft.typego.history.data.GameMode
import com.tetsoft.typego.history.data.Order


class GameHistoryNewFragment : BaseFragment<FragmentGameHistoryNewBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameHistoryNewBinding {
        return FragmentGameHistoryNewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectedSortOrder.adapter =
            LocalizedSpinnerAdapter(requireContext(), listOf(Order.ASCENDING, Order.DESCENDING))
        binding.selectedGameMode.adapter =
            LocalizedSpinnerAdapter(requireContext(), listOf(GameMode.RANDOM_WORDS, GameMode.OWN_TEXT))
        binding.selectedLanguage.adapter = LanguageSpinnerAdapter(requireContext(), LanguageList().getLocalized(requireContext()))
        binding.selectedGameMode.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.selectedLanguage.isEnabled =
                    (binding.selectedGameMode.selectedItem as GameMode) == GameMode.RANDOM_WORDS
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

    }
}