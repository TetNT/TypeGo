package com.tetsoft.typego.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tetsoft.typego.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tetsoft.typego.BuildConfig

class ReleaseNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_release_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvNotesHeader = view.findViewById<TextView>(R.id.tv_whats_new)
        val context = context
        if (context != null) tvNotesHeader.text =
            context.getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME)
    }
}