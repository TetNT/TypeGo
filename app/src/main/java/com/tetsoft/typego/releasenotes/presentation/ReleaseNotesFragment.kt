package com.tetsoft.typego.releasenotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R
import com.tetsoft.typego.databinding.FragmentReleaseNotesBinding
import com.tetsoft.typego.core.ui.BaseFragment
import com.tetsoft.typego.releasenotes.data.ReleaseNotesListImpl

class ReleaseNotesFragment : BaseFragment<FragmentReleaseNotesBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWhatsNew.text = getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME)
        binding.tvWhatsNewDescription.text = getText(R.string.release_notes_description_current_version)
        binding.rvReleaseNotes.adapter =
            ReleaseNotesAdapter(
                requireContext(),
                ReleaseNotesListImpl(requireContext()).getReversed()
            )
        binding.buttonOpenCarousel.setOnClickListener {
            findNavController().navigate(R.id.action_releaseNotes_to_keyNotes)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReleaseNotesBinding {
        return FragmentReleaseNotesBinding.inflate(inflater, container, false)
    }
}