package com.tetsoft.typego

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.adapter.word.WordsPagingDataAdapter
import com.tetsoft.typego.databinding.FragmentTypedWordsBinding

class TypedWordsFragment : Fragment() {

    val viewModel : TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private var _binding : FragmentTypedWordsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypedWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WordsPagingDataAdapter()
        binding.rvTypedWords.adapter = adapter
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.updateTypedWordsList()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvTypedWords.layoutManager = layoutManager
    }
}