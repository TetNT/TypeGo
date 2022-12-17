package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.adapter.AchievementsAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetsoft.typego.databinding.FragmentAchievementsBinding
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.ui.custom.BaseFragment

class AchievementsFragment : BaseFragment<FragmentAchievementsBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAchievementsBinding {
        return FragmentAchievementsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameResultListStorage = GameResultListStorage(requireContext())
        val achievementsProgressStorage = AchievementsProgressStorage(requireContext())
        val staticAchievements = AchievementsList(requireContext())
        binding.rvAchievements.adapter = AchievementsAdapter(
            requireContext(),
            staticAchievements.get(),
            gameResultListStorage.get(),
            achievementsProgressStorage.getAll()
        )
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvAchievements.layoutManager = linearLayoutManager
    }
}