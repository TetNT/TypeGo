package com.tetsoft.typego.adapter.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.GamesHistoryAdapter
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.utils.TimeConvert
import com.tetsoft.typego.utils.Translation
import kotlin.math.roundToInt

class GameOnTimeHistoryAdapter(
    private val context: Context,
    private var results: GameHistoryList<GameOnTime>,
    private val listener: GamesHistoryAdapter.RecyclerViewOnClickListener
) : RecyclerView.Adapter<GameOnTimeHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemWpm: TextView
        var itemTime: TextView
        var itemLanguage: TextView
        var itemDictionary: TextView
        var itemSuggestions: TextView
        var itemOrientation: TextView
        override fun onClick(v: View) {
            listener.onClick(v, adapterPosition)
        }

        init {
            itemWpm = itemView.findViewById(R.id.history_item_wpm)
            itemTime = itemView.findViewById(R.id.history_attribute_time)
            itemLanguage = itemView.findViewById(R.id.history_attribute_language)
            itemDictionary = itemView.findViewById(R.id.history_attribute_dictionary)
            itemSuggestions = itemView.findViewById(R.id.history_attribute_suggestions)
            itemOrientation = itemView.findViewById(R.id.history_attribute_orientation)
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
            if (results[position].getScreenOrientation() == ScreenOrientation.PORTRAIT) {
                itemOrientation.setCompoundDrawablesRelative(AppCompatResources.getDrawable(context, R.drawable.ic_portrait), null, null, null)
            } else {
                itemOrientation.setCompoundDrawablesRelative(AppCompatResources.getDrawable(context, R.drawable.ic_landscape), null, null, null)
            }
            setDrawableTint(itemSuggestions)
            // FIXME
            //if (results[position].areSuggestionsActivated()) {
            //    DrawableCompat.setTint(itemSuggestions.compoundDrawables[0], ContextCompat.getColor(context, R.color.main_green))
            //} else {
            //    DrawableCompat.setTint(itemSuggestions.compoundDrawables[0], ContextCompat.getColor(context, R.color.bg_lighter_gray))
            //}
        }
    }

    private fun setDrawableTint(textView: TextView) {
        for (drawable in textView.compoundDrawables) {
            if (drawable !== null) {
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.main_green))
            }
        }
    }
}