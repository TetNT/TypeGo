package com.tetsoft.typego.adapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.Word

public class WordsAdapter(val wordsList:List<Word>) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
    private val words = wordsList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.word_item,
                        parent,
                        false))
    }

    override fun getItemCount() = wordsList.size

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.tvInputted.text = wordsList[position].inputtedText
        holder.tvOriginal.text = wordsList[position].originalText
        holder.tvIndex.text = "" + (position+1) + "."
        var spannableOrig = SpannableString(holder.tvOriginal.text)
        spannableOrig.setSpan(getGreenSpan(), 0, holder.tvOriginal.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        var spannableStr = SpannableString(holder.tvInputted.text)
        spannableStr.setSpan(getGreenSpan(), 0, holder.tvInputted.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.tvInputted.text = spannableStr
        for (incorrectCharInd in words[position].incorrectCharsIndices) {
            spannableStr = SpannableString(holder.tvInputted.text)
            spannableStr.setSpan(getRedSpan(), incorrectCharInd, incorrectCharInd+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tvInputted.text = spannableStr
        }
    }

    private fun getRedSpan() : ForegroundColorSpan = ForegroundColorSpan(Color.rgb(192, 0, 0))

    private fun getGreenSpan() : ForegroundColorSpan = ForegroundColorSpan(Color.rgb(0, 128, 0))

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInputted: TextView = itemView.findViewById(R.id.tvInputtedWord)
        val tvOriginal: TextView = itemView.findViewById(R.id.tvOriginalWord)
        val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)
    }

}