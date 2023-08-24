package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetsoft.typego.adapter.AchievementsAdapter
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.databinding.FragmentAchievementsBinding
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage

class AchievementsFragment : Fragment() {

    private var _binding : FragmentAchievementsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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