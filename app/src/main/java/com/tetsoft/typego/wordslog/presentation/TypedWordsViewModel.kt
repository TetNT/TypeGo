package com.tetsoft.typego.wordslog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tetsoft.typego.core.data.Word
import com.tetsoft.typego.wordslog.data.FiniteSelector
import dagger.hilt.android.lifecycle.HiltViewModel
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
        val pagingSource = WordsPagingSource(words, FiniteSelector(words))
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { pagingSource }).liveData
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}