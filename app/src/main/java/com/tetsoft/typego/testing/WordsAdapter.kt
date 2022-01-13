package com.tetsoft.typego.testing

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

class WordsAdapter(val wordsList:List<Word>) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
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
        var hasMistakes = false
        var spannableOrig = SpannableString(holder.tvOriginal.text)  // span of the original word
        spannableOrig.setSpan(getGreenSpan(), 0, holder.tvOriginal.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        var spannableInp = SpannableString(holder.tvInputted.text)  // span of the inputted word
        spannableInp.setSpan(getGreenSpan(), 0, holder.tvInputted.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.tvInputted.text = spannableInp
        for (incorrectCharInd in words[position].incorrectCharsIndices) {
            spannableInp = SpannableString(holder.tvInputted.text)
            spannableInp.setSpan(getRedSpan(), incorrectCharInd, incorrectCharInd+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tvInputted.text = spannableInp
            hasMistakes = true
        }
        if (words[position].inputtedText < words[position].originalText)
            hasMistakes = true
        if (hasMistakes) {
            var spannableIndex = SpannableString(holder.tvIndex.text)
            spannableIndex.setSpan(getRedSpan(), 0, spannableIndex.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tvIndex.text = spannableIndex
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