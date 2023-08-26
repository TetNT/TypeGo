package com.tetsoft.typego.ui.fragment.releasenotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.releasenotes.ReleaseNotesAdapter
import com.tetsoft.typego.data.ReleaseNotesList
import com.tetsoft.typego.databinding.FragmentReleaseNotesBinding
import com.tetsoft.typego.ui.fragment.BaseFragment

class ReleaseNotesFragment : BaseFragment<FragmentReleaseNotesBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWhatsNew.text = getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME)
        binding.tvWhatsNewDescription.text = getText(R.string.release_notes_description_current_version)
        binding.rvReleaseNotes.adapter =
            ReleaseNotesAdapter(
                requireContext(),
                ReleaseNotesList.Standard(requireContext()).getReversed()
            )
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReleaseNotesBinding {
        return FragmentReleaseNotesBinding.inflate(inflater, container, false)
    }
}