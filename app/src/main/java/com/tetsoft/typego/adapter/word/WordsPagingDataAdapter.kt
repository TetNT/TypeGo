package com.tetsoft.typego.adapter.word

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.data.Word

class WordsPagingDataAdapter : PagingDataAdapter<Word, WordsPagingDataAdapter.ViewHolder>(
    diffCallback
) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInputted: TextView = itemView.findViewById(R.id.tvInputtedWord)
        val tvOriginal: TextView = itemView.findViewById(R.id.tvOriginalWord)
        val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position) ?: return
        holder.tvIndex.text = "${(position + 1)}."
        holder.tvInputted.text = currentItem.inputtedText
        holder.tvOriginal.text = currentItem.originalText
        var inputtedWordSpan = SpannableString(holder.tvInputted.text)
        inputtedWordSpan.setSpan(
            getGreenSpan(),
            0,
            holder.tvInputted.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.tvInputted.text = inputtedWordSpan
        inputtedWordSpan = SpannableString(holder.tvInputted.text)

        for (incorrectCharInd in currentItem.incorrectCharsIndices) {
            inputtedWordSpan.setSpan(
                getRedSpan(),
                incorrectCharInd,
                incorrectCharInd + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.tvInputted.text = inputtedWordSpan
        }
        if (!currentItem.isCorrect()) {
            val spannableIndex = SpannableString(holder.tvIndex.text)
            spannableIndex.setSpan(
                getRedSpan(),
                0,
                spannableIndex.length - 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.tvIndex.text = spannableIndex
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_item,
                parent,
                false
            )
        )
    }

    private fun getRedSpan(): ForegroundColorSpan = ForegroundColorSpan(Color.rgb(192, 0, 0))

    private fun getGreenSpan(): ForegroundColorSpan = ForegroundColorSpan(Color.rgb(0, 128, 0))

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.inputtedText == newItem.inputtedText
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.inputtedText == newItem.inputtedText &&
                        oldItem.originalText == newItem.originalText
            }

        }
    }
}