package com.tetsoft.typego.wordslog.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tetsoft.typego.wordslog.domain.DataSelector
import com.tetsoft.typego.core.data.Word

class WordsPagingSource(
    private val wordsList: List<Word>,
    private val selector: DataSelector<Word>
) : PagingSource<Int, Word>() {
    override fun getRefreshKey(state: PagingState<Int, Word>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Word> {
        if (wordsList.isEmpty()) return LoadResult.Page(emptyList(), null, null)
        val page = params.key ?: STARTING_KEY
        val selection = selector.getNextData(params.loadSize)
        val nextKey = if (selection.isEmpty()) null else page + 1
        val prevKey = if (page == STARTING_KEY) null else page - 1
        return LoadResult.Page(
            data = selection,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    companion object {
        const val STARTING_KEY = 1
    }

}