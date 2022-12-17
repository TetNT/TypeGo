package com.tetsoft.typego

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.adapter.word.WordsPagingDataAdapter
import com.tetsoft.typego.databinding.FragmentTypedWordsBinding
import com.tetsoft.typego.ui.custom.BaseFragment

class TypedWordsFragment : BaseFragment<FragmentTypedWordsBinding>() {

    val viewModel : TypedWordsViewModel by hiltNavGraphViewModels(R.id.main_navigation)

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

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTypedWordsBinding {
        return FragmentTypedWordsBinding.inflate(inflater, container, false)
    }

}