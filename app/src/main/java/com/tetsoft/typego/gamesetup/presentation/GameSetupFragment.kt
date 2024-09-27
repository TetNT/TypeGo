package com.tetsoft.typego.gamesetup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.tetsoft.typego.R
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.databinding.FragmentGameSetupBinding
import com.tetsoft.typego.game.presentation.TimeGameViewModel

class GameSetupFragment : BaseFragment<FragmentGameSetupBinding>() {

    private lateinit var viewModel: GameSetupViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameSetupBinding {
        return FragmentGameSetupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GameSetupViewModel::class.java]
        val tabFragments = mapOf(
            getString(R.string.own_text) to OwnTextGameSetupFragment(),
            getString(R.string.random_words) to RandomWordsGameSetupFragment()
        )
        val adapter = FragmentViewPagerAdapter(requireActivity(), tabFragments.values.toList())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabFragments.keys.toList()[position]
        }.attach()

        binding.buttonStartGame.setOnClickListener {
            val gs = if (binding.viewPager.currentItem == 0) {
                viewModel.getOwnTextGameSettings()

            } else viewModel.getRandomWordsGameSettings()
            if (!viewModel.settingsAreValid(gs)) return@setOnClickListener
            val timeGameViewModel : TimeGameViewModel by hiltNavGraphViewModels(R.id.main_navigation)
            timeGameViewModel.gameSettings = gs
            navigateTo(R.id.action_gameSetupFragment_to_timeGameFragment)
        }
    }

}