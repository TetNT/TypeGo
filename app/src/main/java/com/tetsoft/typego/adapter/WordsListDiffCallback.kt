package com.tetsoft.typego.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tetsoft.typego.data.Word

class WordsListDiffCallback(private val oldList: List<Word>, private val updatedList: List<Word>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return updatedList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == updatedList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].inputtedText == updatedList[newItemPosition].inputtedText &&
                oldList[oldItemPosition].originalText == updatedList[newItemPosition].originalText
    }

}
