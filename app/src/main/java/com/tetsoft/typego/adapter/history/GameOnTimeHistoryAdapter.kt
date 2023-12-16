package com.tetsoft.typego.adapter.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.extensions.setDrawableTint
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.utils.TimeConvert
import com.tetsoft.typego.utils.Translation
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class GameOnTimeHistoryAdapter(
    private val context: Context,
    private var results: GameHistoryList<GameOnTime>,
    private val listener: RecyclerViewOnClickListener
) : RecyclerView.Adapter<GameOnTimeHistoryAdapter.ViewHolder>() {

    interface RecyclerViewOnClickListener {
        fun onClick(v: View?, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemWpm: TextView
        val itemTime: TextView
        val itemLanguage: TextView
        val itemDictionary: TextView
        val itemSuggestions: TextView
        val itemOrientation: TextView
        val itemSeed: TextView
        val completionTime: TextView

        override fun onClick(v: View) {
            listener.onClick(v, bindingAdapterPosition)
        }

        init {
            itemWpm = itemView.findViewById(R.id.history_item_wpm)
            itemTime = itemView.findViewById(R.id.history_attribute_time)
            itemLanguage = itemView.findViewById(R.id.history_attribute_language)
            itemDictionary = itemView.findViewById(R.id.history_attribute_dictionary)
            itemSuggestions = itemView.findViewById(R.id.history_attribute_suggestions)
            itemOrientation = itemView.findViewById(R.id.history_attribute_orientation)
            itemSeed = itemView.findViewById(R.id.history_attribute_seed)
            completionTime = itemView.findViewById(R.id.completion_time)
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        val MAX_ITEM_COUNT = 20
        return Integer.min(results.size, MAX_ITEM_COUNT)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.history_on_time_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            itemWpm.text = results[position].getWpm().roundToInt().toString()
            itemLanguage.text = results[position].getLanguageCode()
            itemTime.text = TimeConvert.convertSecondsToStamp(results[position].getTimeSpent())
            itemDictionary.text = Translation(context).get(results[position].getDictionaryType())
            val completionTime = Date(results[position].getCompletionDateTime())
            val dateFormat: DateFormat = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.DEFAULT,
                SimpleDateFormat.SHORT
            )
            holder.completionTime.text = dateFormat.format(completionTime)
            // TODO: make stuff below a separate class and test it
            if (results[position].getScreenOrientation() == ScreenOrientation.PORTRAIT) {
                itemOrientation.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_portrait), null, null, null)
            } else {
                itemOrientation.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_landscape), null, null, null)
            }
            if (results[position].areSuggestionsActivated()) {
                itemSuggestions.setDrawableTint(R.color.white)
            }
            else {
                itemSuggestions.setDrawableTint(R.color.background_variant)
            }
            if(results[position].getSeed().isNotEmpty()) {
                itemSeed.setDrawableTint(R.color.white)
            } else {
                itemSeed.setDrawableTint(R.color.background_variant)
            }
        }
    }

}