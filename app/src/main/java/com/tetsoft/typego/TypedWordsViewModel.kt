package com.tetsoft.typego

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tetsoft.typego.adapter.selector.DataSelector
import com.tetsoft.typego.adapter.word.WordsPagingSource
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.ui.fragment.result.ResultViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class TypedWordsViewModel @Inject constructor() : ViewModel() {

    private val selectedList = MutableLiveData<List<Word>>(ArrayList())

    val words = selectedList.switchMap { updateTypedWordsPagingData(it) }

    fun selectTypedWordsList(list: List<Word>) {
        selectedList.value = list
    }

    fun updateTypedWordsList() {
        selectedList.value = selectedList.value
    }


    private fun updateTypedWordsPagingData(words: List<Word>) : LiveData<PagingData<Word>> {
        val pagingSource = WordsPagingSource(words, DataSelector.FiniteSelector(words))
        return Pager(
            config = PagingConfig(pageSize = ResultViewModel.PAGE_SIZE),
            pagingSourceFactory = { pagingSource }).liveData
    }
}