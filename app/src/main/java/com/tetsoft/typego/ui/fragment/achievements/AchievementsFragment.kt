package com.tetsoft.typego.ui.fragment.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.AchievementsAdapter
import com.tetsoft.typego.databinding.FragmentAchievementsBinding
import com.tetsoft.typego.ui.custom.BaseFragment

class AchievementsFragment : BaseFragment<FragmentAchievementsBinding>() {

    val viewModel : AchievementsViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAchievementsBinding {
        return FragmentAchievementsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAchievements.adapter = AchievementsAdapter(
            requireContext(),
            viewModel.getAchievementsList(),
            viewModel.getResultList(),
            viewModel.getAchievementsProgress()
        )
    }
}