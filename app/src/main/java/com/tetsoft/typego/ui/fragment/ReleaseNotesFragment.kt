package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R
import com.tetsoft.typego.databinding.FragmentReleaseNotesBinding

class ReleaseNotesFragment : Fragment() {

    private var _binding : FragmentReleaseNotesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReleaseNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWhatsNew.text = getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME)
    }
}