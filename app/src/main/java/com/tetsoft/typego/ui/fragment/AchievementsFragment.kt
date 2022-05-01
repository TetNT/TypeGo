package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tetsoft.typego.data.achievement.AchievementList
import com.tetsoft.typego.adapter.AchievementsAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetsoft.typego.databinding.FragmentAchievementsBinding
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage

class AchievementsFragment : Fragment() {
    private var _binding: FragmentAchievementsBinding? = null
    private val binding: FragmentAchievementsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (context == null) return
        val gameResultListStorage = GameResultListStorage(requireContext())
        val achievementsProgressStorage = AchievementsProgressStorage(requireContext())
        val staticAchievements = AchievementList(requireContext())
        binding.rvAchievements.adapter = AchievementsAdapter(
            requireContext(),
            staticAchievements.get(),
            gameResultListStorage.get(),
            achievementsProgressStorage
        )
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvAchievements.layoutManager = linearLayoutManager
    }
}